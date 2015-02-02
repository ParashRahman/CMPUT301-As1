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
import android.os.Bundle;
import android.util.Log;
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
	private ImageButton editClaimButton;
	private ImageButton viewClaimButton;
	private ImageButton deleteClaimButton;
	private ArrayList<Claim> listOfClaims;
	private ArrayAdapter<Claim> adapter;
	private ListView lv_claims;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        
        lv_claims = ( ListView ) findViewById(R.id.expenseList);
        addClaimButton = ( Button ) findViewById(R.id.b_add_claim);
        //editClaimButton = (ImageButton) findViewById(R.id.b_editClaim);
        //viewClaimButton = (ImageButton) findViewById(R.id.b_viewClaim);
        //deleteClaimButton = (ImageButton) findViewById(R.id.b_deleteClaim);
        
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

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                	public boolean onMenuItemClick(MenuItem item) {
                		
                		if ( listOfClaims.get(position).getFromDate() != null ){
                			Log.i("meMessage", listOfClaims.get(position).getFromDate().toString());
                		} else {
                			Log.i("meMessage", "Date is NULL");
                		}
                		
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
                        	adapter = new ArrayAdapter<Claim>(MainActivity.this, R.layout.claims_list_line, R.id.tv_claimsList, listOfClaims );
                    		lv_claims.setAdapter(adapter);
                            return true;
                        
                        case R.id.view_claim:
                        	Intent intent = new Intent(getApplicationContext(), ViewClaimActivity.class);
            				
            				Claim newClaim = listOfClaims.get(position);
            				
            				intent.putExtra("respectiveClaim", newClaim);
            				intent.putExtra("indexOfClaim", position );
            				
            				startActivity(intent);    
                        	
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
		
		Log.i("meMessage", Boolean.toString(extrasIn));
		
		if ( extrasIn ) { // If there exists an extra
			int dex = getIntent().getIntExtra("indexOfClaim", 0);	
			Claim replacement = (Claim) getIntent().getSerializableExtra("respectiveClaim");
			///////////////
			if (replacement.getFromDate() != null ){
				Log.i("meMessage",replacement.getFromDate().toString());
			}
			//////////////
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
		
		adapter = new ArrayAdapter<Claim>(this, R.layout.claims_list_line, R.id.tv_claimsList, listOfClaims );
		lv_claims.setAdapter(adapter);
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
