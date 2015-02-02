package com.parash.expensechecker;

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
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

// List of Claims
public class MainActivity extends Activity {
	private static final String FILENAME = "file.sav";
	
	private Button addClaimButton;
	private ArrayList<Claim> listOfClaims;
	private ArrayAdapter<Claim> adapter;
	private ListView lv_claims;
	
	private static int[] hideForIP = { R.id.mark_approved_claim, R.id.mark_returned_claim };
	private static int[] hideForA = { R.id.edit_claim, R.id.submit_claim, R.id.mark_approved_claim, R.id.mark_returned_claim };
	private static int[] hideForS = { R.id.edit_claim, R.id.submit_claim };
	private static int[] hideForR = { R.id.mark_approved_claim, R.id.mark_returned_claim };

	protected int list_view_item_position;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        
        lv_claims = ( ListView ) findViewById(R.id.expenseList);
        addClaimButton = ( Button ) findViewById(R.id.b_add_claim);
        
        listOfClaims = loadFromFile();
		
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
		{
		    public void onItemClick(AdapterView<?> parentView, View childView, final int position, long id) 
		    {
		    	PopupMenu popup = new PopupMenu(MainActivity.this, childView);
                popup.getMenuInflater().inflate(R.menu.claim_popup, popup.getMenu());
                
                list_view_item_position = position;
        	
            	onPrepareOptionsMenu( popup.getMenu() );

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                	public boolean onMenuItemClick(MenuItem item) {
                    	

                    	switch (item.getItemId()) {
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
                        	Intent send = new Intent(Intent.ACTION_SENDTO);
                        	String uriText = "mailto:" + Uri.encode("email@gmail.com") + 
                        	          "?subject=" + Uri.encode("the subject") + 
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
    
    private void resetAdapter(){
    	adapter = new ArrayAdapter<Claim>(MainActivity.this, R.layout.claims_list_line, R.id.tv_claimsList, listOfClaims );
		lv_claims.setAdapter(adapter);
    }
        
    @Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		
		finish();
		return;
	}

	@Override
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
