package com.example.zillowsearch;


import java.io.InputStream;

import java.net.URLEncoder;

import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends ActionBarActivity {
		EditText address;
	    EditText city;
	    Spinner state;
	    TextView addressError;
	    TextView cityError;
	    TextView stateError;
	    TextView jsonError;
	    static InputStream is = null;
	    public final static String JSON_STRING = "json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        address=(EditText) findViewById(R.id.addressZillow);
 	   city=(EditText) findViewById(R.id.cityZillow);
 	   state=(Spinner) findViewById(R.id.stateZillow);
 		addressError=(TextView)findViewById(R.id.addressError);
 	    cityError=(TextView)findViewById(R.id.cityError);
 	    stateError=(TextView)findViewById(R.id.stateError);
 	   
         
       //  Validation for input starts
 	    address.addTextChangedListener(new TextWatcher() {

 			@Override
 			public void beforeTextChanged(CharSequence s, int start, int count,
 					int after) {
 				// TODO Auto-generated method stub
 				
 			}

 			@Override
 			public void onTextChanged(CharSequence s, int start, int before,
 					int count) {
 				// TODO Auto-generated method stub
 				Log.v("address", s.toString());
 				if(s.toString().equals("")){
 		        	addressError.setVisibility(View.VISIBLE);
 		        }else{
 		        	addressError.setVisibility(View.INVISIBLE);
 		        }
 			
 				
 			}

 			@Override
 			public void afterTextChanged(Editable s) {
 				
 				
 			}

 		
 		});    

 	    city.addTextChangedListener(new TextWatcher() {

 			@Override
 			public void beforeTextChanged(CharSequence s, int start, int count,
 					int after) {
 				// TODO Auto-generated method stub
 				
 			}

 			@Override
 			public void onTextChanged(CharSequence s, int start, int before,
 					int count) {
 				// TODO Auto-generated method stub
 				Log.v("city", s.toString());
 				 if(s.toString().equals("")){
 			        	cityError.setVisibility(View.VISIBLE);
 			        }else{
 			        	cityError.setVisibility(View.INVISIBLE);
 			        }
 				
 			}

 			@Override
 			public void afterTextChanged(Editable s) {
 				
 				
 			}

 		
 		});

 	  
 	   
    }
 	    
 	    
 	  


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
    

    
    
    
    /** Called when the user clicks on image */
    public void zillowPage(View view) {
    	Intent browserIntent = new Intent("android.intent.action.VIEW",
Uri.parse("http://www.zillow.com"));

    			startActivity(browserIntent);
    	
    	
    	
    }
    /** Called when the user touches the button */
    public void zillowPhp(View view) {
	   address=(EditText) findViewById(R.id.addressZillow);
	   city=(EditText) findViewById(R.id.cityZillow);
	   state=(Spinner) findViewById(R.id.stateZillow);
		addressError=(TextView)findViewById(R.id.addressError);
	    cityError=(TextView)findViewById(R.id.cityError);
	    stateError=(TextView)findViewById(R.id.stateError);
        
        
	    state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()    {
 	        @Override
 	        public void onItemSelected(AdapterView<?> arg0, View arg1,
 	        int arg2, long arg3){
 	        if(state.getSelectedItem().toString().equals("Choose State")){
 	        	stateError.setVisibility(View.VISIBLE);
 	        }else{
 	        	stateError.setVisibility(View.INVISIBLE);
 	        }
 	    }

 			@Override
 			public void onNothingSelected(AdapterView<?> parent) {
 				// TODO Auto-generated method stub
 				stateError.setVisibility(View.INVISIBLE);
 			}
 	    });

    
	    
        if(address.getText().toString().equals("")){
        	addressError.setVisibility(View.VISIBLE);
        }else{
        	addressError.setVisibility(View.INVISIBLE);
        }
        if(city.getText().toString().equals("")){
        	cityError.setVisibility(View.VISIBLE);
        }else{
        	cityError.setVisibility(View.INVISIBLE);
        }
        if(state.getSelectedItem().toString().equals("Choose State")){
        	stateError.setVisibility(View.VISIBLE);
        }else{
        	stateError.setVisibility(View.INVISIBLE);
        }
    //  Validation for input ends
       if(!address.getText().toString().equals("") && !city.getText().toString().equals("") && !state.getSelectedItem().toString().equals("Choose State")){
       try{
    	   new JSONParse().execute();
       }catch(Exception e){
    	   e.printStackTrace();
       }
      
       }
    }
    
   private class JSONParse extends AsyncTask<String, String, JSONObject> {
        
 	   @Override
 	     protected void onPreExecute() {
 	    super.onPreExecute();
 	   address=(EditText) findViewById(R.id.addressZillow);
 	    city=(EditText) findViewById(R.id.cityZillow);
 	    state=(Spinner) findViewById(R.id.stateZillow);
 	        
 	   }
 	   @Override
 	     protected JSONObject doInBackground(String... args) {
 	     JSONParser jParser = new JSONParser();
 	     // Getting JSON from URL
 	    address=(EditText) findViewById(R.id.addressZillow);
 	    city=(EditText) findViewById(R.id.cityZillow);
 	   state=(Spinner) findViewById(R.id.stateZillow);
 	    
 	   	   // System.out.print(url);
 	    StringBuilder sb =new StringBuilder();
	    JSONObject json=null;
	    String url=new String();
		try {
			url="http://zillowhw8-env.elasticbeanstalk.com/zillowphp.php?street="+URLEncoder.encode(address.getText().toString(), "UTF-8")+"&city="+URLEncoder.encode(city.getText().toString(), "UTF-8")+"&state="+URLEncoder.encode(state.getSelectedItem().toString(), "UTF-8")+"";			 
			json = jParser.getJSONFromUrl(url);

		}catch(Exception e){
			e.printStackTrace();
		}
 	     return json;
 	   }
 	  @Override
      protected void onPostExecute(JSONObject json) {
     // pDialog.dismiss();
     
         // Getting JSON Array
 		 //EditText address = null;
         //Set JSON Data in TextView
 		 try {
 		 jsonError=(TextView)findViewById(R.id.jsonerror);
 	
 	JSONObject resultJSON=	json.getJSONObject("result");
 	String message=resultJSON.getString("message");	
 		if(message=="0" || message.equals("0")){
    	Intent intent = new Intent(MainActivity.this, ResultActivity.class); 	    
   	    intent.putExtra(MainActivity.JSON_STRING, json.toString());
   	    startActivity(intent);
 		}else{
 			jsonError.setVisibility(View.VISIBLE);
 		}
 		
 		} catch (Exception e) {
 			Toast.makeText(MainActivity.this, "Internet Issue: JSON not retrieved", Toast.LENGTH_LONG).show();
		e.printStackTrace();
		}
   }
 	    
 	 }
 
}
