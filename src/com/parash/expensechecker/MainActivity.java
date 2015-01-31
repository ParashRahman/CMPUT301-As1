package com.parash.expensechecker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

// List of Claims
public class MainActivity extends Activity {
	private static final String FILENAME = "file.sav";
	private Button addClaimButton;
	private ArrayList<Claim> listOfClaims;
	private ArrayAdapter<Claim> adapter;
	private ListView lv_claims;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        
        lv_claims = ( ListView ) findViewById(R.id.expenseList);
        addClaimButton = ( Button ) findViewById(R.id.b_add_claim);
        
        listOfClaims = loadFromFile();
		adapter = new ArrayAdapter<Claim>(this, R.layout.claims_list_line, R.id.tv_claimsList, listOfClaims );
		lv_claims.setAdapter(adapter);
		
		
		addClaimButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Intent i = new Intent(getApplicationContext(), EditClaimActivity.class);
				
				Claim newClaim = new Claim();
				listOfClaims.add(newClaim);
				
				Log.i( "meMessage", Integer.toString(listOfClaims.size()) );

				
				i.putExtra( "respectiveClaim", newClaim );
				i.putExtra( "indexOfClaim", listOfClaims.size() - 1 );
				
				startActivity(i);
			}
		});
		
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
		
		Log.i("meMessage", Boolean.toString(extrasIn) );
		
		if ( extrasIn ) { // If there exists an extra
			Log.i( "meMessage", Integer.toString(listOfClaims.size()) );
			
			int dex = getIntent().getIntExtra("indexOfClaim", 0);
			Claim replacement = (Claim) getIntent().getSerializableExtra("respectiveClaim");
			listOfClaims.set(dex, replacement);
			
			Log.i("meMessage",replacement.toString());
			Log.i("meMessage",Integer.toString(dex));
		}
		
		adapter.notifyDataSetChanged();
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

	private void saveToFile() {
		Gson gson = new Gson();
		
		Type dataType = new TypeToken<ArrayList<Claim>>(){}.getType();
		try {
			Writer osw = new OutputStreamWriter ( new FileOutputStream(FILENAME) );
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		
	}    
    
}
