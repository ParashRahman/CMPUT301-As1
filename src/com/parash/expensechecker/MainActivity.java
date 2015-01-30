package com.parash.expensechecker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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
				
				Intent i = new Intent(getApplicationContext(), AddClaimActivity.class);
				
				Claim newClaim = new Claim();
				listOfClaims.add(newClaim);
				
				i.putExtra("respectiveClaim", newClaim);
				
				startActivity(i);
			}
		});
		
    }
    
    private ArrayList<Claim> loadFromFile() {
		// TODO Auto-generated method stub
    	Gson gson = new Gson();
    	ArrayList<Claim> claims = null;
    	
    	try {
			FileInputStream fis = openFileInput(FILENAME);
			//https://sites.google.com/site/gson/gson-user-guide
			Type dataType = new TypeToken<ArrayList<String>>() {}.getType();
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


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		
	}    
    
}
