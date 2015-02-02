package com.parash.expensechecker;

/** 
 * 
 * The MIT License (MIT)

Copyright (c) 2015 ParashRahman

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.


 * 
 * **/

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

// List of Claims
public class MainActivity extends Activity {
	// file name of saved list of claims data
	private static final String FILENAME = "file.sav";
	
	private Button addClaimButton;
	private ArrayList<Claim> listOfClaims;
	private ArrayAdapter<Claim> adapter;
	private ListView lv_claims;
	
	// following four matrices adjust the menu for each claim depending on what the claim's status is
	private static int[] hideForIP = { R.id.mark_approved_claim, R.id.mark_returned_claim };
	private static int[] hideForA = { R.id.edit_claim, R.id.submit_claim, R.id.mark_approved_claim, R.id.mark_returned_claim };
	private static int[] hideForS = { R.id.edit_claim, R.id.submit_claim };
	private static int[] hideForR = { R.id.mark_approved_claim, R.id.mark_returned_claim };

	protected int list_view_item_position;
		
    @Override
    // A monstrous looking class only because of the Listeners.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        
        lv_claims = ( ListView ) findViewById(R.id.expenseList);
        addClaimButton = ( Button ) findViewById(R.id.b_add_claim);
        
        listOfClaims = loadFromFile();
		
        // If user clicks the add claim button create an empty claim and go to the edit claim activity
		addClaimButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Intent i = new Intent(getApplicationContext(), EditClaimActivity.class);
				
				Claim newClaim = new Claim();
				listOfClaims.add(newClaim);
				
				i.putExtra("respectiveClaim", newClaim);
				i.putExtra("indexOfClaim", listOfClaims.size() - 1 );
				
				startActivity(i);
			}
		});
		
		//http://stackoverflow.com/questions/21329132/android-custom-dropdown-popup-menu
		//http://stackoverflow.com/questions/7201159/is-using-menuitem-getitemid-valid-in-finding-which-menuitem-is-selected-by-use
		//http://stackoverflow.com/questions/4554435/how-to-get-the-index-and-string-of-the-selected-item-in-listview-in-android
		lv_claims.setOnItemClickListener(new AdapterView.OnItemClickListener() 
		// this is the list view click listener for the list of Claims
		{
		    public void onItemClick(AdapterView<?> parentView, View childView, final int position, long id) 
		    {
		    	// A popup menu pops up to give the user options on what to do with the claim
		    	PopupMenu popup = new PopupMenu(MainActivity.this, childView);
                popup.getMenuInflater().inflate(R.menu.claim_popup, popup.getMenu());
                
                list_view_item_position = position;
        	
            	onPrepareOptionsMenu( popup.getMenu() );

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                	// onclicklistener for the menu
                	public boolean onMenuItemClick(MenuItem item) {
                    	
                    	switch (item.getItemId()) {
                    	// each case is for a different menu option
                        case R.id.edit_claim:
                        	Intent i = new Intent(getApplicationContext(), EditClaimActivity.class);
            				
            				Claim claim = listOfClaims.get(position);
            				
            				i.putExtra("respectiveClaim", claim);
            				i.putExtra("indexOfClaim", position );
            				
            				startActivity(i);                      	
                        	
                            return true;
                        
                        case R.id.delete_claim:
                        	listOfClaims.remove(position);
                        	resetAdapter();
                            return true;
                        
                        case R.id.view_claim:
                        	Intent intent = new Intent(getApplicationContext(), ViewClaimActivity.class);
            				
            				Claim claim2 = listOfClaims.get(position);
            				
            				intent.putExtra("respectiveClaim", claim2);
            				intent.putExtra("indexOfClaim", position );
            				
            				startActivity(intent);    
                        	
                        	return true;
                        
                        case R.id.submit_claim:
                        	listOfClaims.get(position).setStatus(Claim.SUBMITTED);
                        	resetAdapter();
                        	return true;
                        	
                        case R.id.mark_approved_claim:
                        	listOfClaims.get(position).setStatus(Claim.APPROVED);
                        	resetAdapter();
                        	return true;
                        	
                        case R.id.mark_returned_claim:
                        	listOfClaims.get(position).setStatus(Claim.RETURNED);
                        	resetAdapter();
                        	return true;
                        case R.id.email_claim:
                            //https://stackoverflow.com/questions/8284706/send-email-via-gmail
                        	// email option puts claim info in body and lets the user fill out the rest
                        	Intent send = new Intent(Intent.ACTION_SENDTO);
                        	
                        	String uriText = "mailto:" + Uri.encode("") + "?subject=" + Uri.encode("My Expense Claim") + 
                        	          "&body=" + Uri.encode(listOfClaims.get(position).claimSummary());
                        	Uri uri = Uri.parse(uriText);

                        	send.setData(uri);
                        	startActivity(Intent.createChooser(send, "Send mail..."));
                        	return true;
                        default:;
                        }
                        return true;
                    }

                });
                
                popup.show(); 

		    }
		    public void onNothingSelected(AdapterView<?> parentView) 
		    {
		    }
		});
		
    }
    
    
    @Override
    // used in the popup menu in onCreate. It hides the menu options based on the claim's respective status
    public boolean onPrepareOptionsMenu(Menu menu)
	{
		String status = listOfClaims.get(list_view_item_position).getStatus();

		if ( status.equals(Claim.IN_PROGRESS) ){
			for ( int id : hideForIP ){
				menu.findItem(id).setVisible(false);
			}
		} else if ( status.equals(Claim.APPROVED) ){
			for ( int id : hideForA ){
				menu.findItem(id).setVisible(false);
			}
		} else if ( status.equals(Claim.SUBMITTED) ){
			for ( int id : hideForS ){
				menu.findItem(id).setVisible(false);
			}
  		} else if ( status.equals(Claim.RETURNED) ){
  			for ( int id : hideForR ){
				menu.findItem(id).setVisible(false);
  			}
  		}
	    return true;
	}
    
    // redisplays what is on the list view. used when status of claim is changed.
    private void resetAdapter(){
    	adapter = new ArrayAdapter<Claim>(MainActivity.this, R.layout.claims_list_line, R.id.tv_claimsList, listOfClaims );
		lv_claims.setAdapter(adapter);
    }

	@Override
	// reloads all the claim from memory and checks if there was an edited claim attached that should be updated.
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		// checking for extras from: http://stackoverflow.com/questions/13408419/how-do-i-tell-if-intent-extras-exist-in-android	
        listOfClaims = loadFromFile();
        
		boolean extrasIn = false;
		
		if ( getIntent().getExtras() != null ){
			extrasIn = getIntent().getExtras().containsKey("indexOfClaim") && getIntent().getExtras().containsKey("respectiveClaim");
		}		
		
		
		if ( extrasIn ) { // If there exists an extra
			int dex = getIntent().getIntExtra("indexOfClaim", 0);	
			Claim replacement = (Claim) getIntent().getSerializableExtra("respectiveClaim");
			listOfClaims.set(dex, replacement);
		}
		
		Collections.sort(listOfClaims, new Comparator<Claim>() {
	        @Override
	        public int compare(Claim c1, Claim c2)
	        {
	        	if ( c1.getFromDate() == null ){
	        		return 1;
	        	}
	        	if ( c2.getFromDate() == null ){
	        		return -1;
	        	}
	            return  (c1.getFromDate()).compareTo(c2.getFromDate());
	        }
	    });
		
		resetAdapter();
	}

	// loads all the claims from memory.
	private ArrayList<Claim> loadFromFile() {
		// TODO Auto-generated method stub
    	Gson gson = new Gson();
    	ArrayList<Claim> claims = null;
    	
    	try {
			FileInputStream fis = openFileInput(FILENAME);
			//https://sites.google.com/site/gson/gson-user-guide
			Type dataType = new TypeToken<ArrayList<Claim>>() {}.getType();
			InputStreamReader isr = new InputStreamReader(fis);
			claims = gson.fromJson(isr, dataType);
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} if(claims == null){
			claims = new ArrayList<Claim>();
		}
		return claims;
   	}

	// saves all the claims to memory
	private void saveToFile() throws IOException {
		Gson gson = new Gson();
		
		try {
			FileOutputStream fos = openFileOutput(FILENAME,
					0);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			gson.toJson(listOfClaims, osw);
			osw.flush();
			fos.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		try {
			saveToFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}    
    
}
