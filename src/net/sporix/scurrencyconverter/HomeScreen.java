package net.sporix.scurrencyconverter;

import java.io.ByteArrayOutputStream;
import java.io.File;

import net.sporix.scurrencyconverter.R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class HomeScreen extends Activity {
	
	private String [] arrayCurrency = {"AED", "ANG", "ARS", "AUD", "BDT", "BGN", "BHD", "BND", "BOB", "BRL",  "BWP", "CAD",
			"CHF", "CLP", "CNY", "COP", "CRC", "CZK", "DKK", "DOP", "DZD", "EEK", "EGP",
			"EUR", "FJD", "GBP", "HKD",
			"HNL", "HRK", "HUF", "IDR", "ILS", "INR", "JMD", "JOD", "JPY",
			"KES", "KRW", "KWD", "KYD", "KZT", "LBP", "LKR", "LTL",
			"LVL", "MAD", "MDL", "MKD", "MUR", "MVR", "MXN", "MYR",
			"NAD", "NGN", "NIO", "NOK", "NPR", "NZD", "OMR", "PEN", "PGK", "PHP", "PKR", "PLN", "PYG",
			"QAR", "RON", "RSD", "RUB", "SAR", "SCR", "SEK", "SGD", "SKK", "SLL", "SVC", "THB", "TND", "TRY", "TTD", "TWD", "TZS",
			"UAH", "UGX", "USD", "UYU", "UZS", "VEF", "VND", "XOF", "YER",
			"ZAR", "ZMK"};
	
	//private String[] arrayFavourite = {};
	private List<String> listFavourite = new ArrayList<String>(); 
	private List<String> listFavouritePost = new ArrayList<String>(); 
	
	private ProgressDialog dialog = null;
	private String output = "";
	private String fromName = "";
	private Spinner spinnerFrom, spinnerTo;
	private ListView listView;
	private ArrayAdapter<String> dataAdapter = null;
	private ArrayAdapter<String> dataAdapterFavourite = null;
	private ArrayAdapter<String> dataAdapterSetting = null;
	private ImageButton btnSetting;
	private RelativeLayout mainlayout;
	private Spinner spin1, spin2, spin3, spin4, spin5;
	private Button btnOkSetting;
	private RelativeLayout myLayout;
	private View hiddenInfo;
	
	protected int _splashTime = 5000; 
	private Thread splashTread;
	
	private LinearLayout linText;
	private ImageButton btnConvert;

	private String toName = "";
	private EditText txtFrom;
	private TextView txtResult;
	private Button btnClosePop;
	private PopupWindow pw;
	
	private String val = "";
	private String secondaryConversion;
	private boolean isFinished;
	private int iterarion = -1;
	
	ProgressDialog progressBar;
	private int progressBarStatus = 0;
	private Handler progressBarHandler = new Handler();
	
	private TextView txtInfo;
	private int backCount = 0;
	
	private boolean isOnSettingPage = false;
	private Button btnCancelSetting;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_scurrency);
        
        mainlayout = (RelativeLayout)this.findViewById(R.layout.activity_main_activity_scurrency);
        
        dataAdapter = new ArrayAdapter<String>(this,
        		android.R.layout.simple_spinner_item, arrayCurrency);
        
        spinnerFrom = (Spinner)this.findViewById(R.id.mainFrom);
        spinnerFrom.setAdapter(dataAdapter);
        spinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
           
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}

			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Log.v("clicked", "item name: "+arrayCurrency[position]);
				
				setFromName(arrayCurrency[position]);
			}

        });
        
      
        
        spinnerTo = (Spinner)this.findViewById(R.id.mainTo);
        spinnerTo.setAdapter(dataAdapter);
        spinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
           
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}

			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Log.v("clicked", "item name: "+arrayCurrency[position]);
				
				setToName(arrayCurrency[position]);
			}

        });
      
        
        
        /*progressBar = new ProgressDialog(this);
		//progressBar.setCancelable(true);
		progressBar.setMessage("File downloading ...");
		progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressBar.setProgress(0);
		progressBar.setMax(100);
		progressBar.show();*/
        
        
        if(getPreferencesDate("CUR1").length() <=0)
        {
        	 SavePreferences("CUR1", "GBP");
        	 listFavourite.add("GBP"+" = "+convertToUsd(getPreferencesDate("CUR1")));
        	 
        	// progressBarStatus+=20;
        	 
        	 //progressBar.setProgress(progressBarStatus);
        	 
        }
        else
        {
        	//progressBarStatus+=20;
       	 
       	 	//progressBar.setProgress(progressBarStatus);
        	
        	listFavourite.add(getPreferencesDate("CUR1")+" = "+convertToUsd(getPreferencesDate("CUR1")));
        	 
        }
       
        if(getPreferencesDate("CUR2").length() <=0)
        {
	        SavePreferences("CUR2", "EUR");
	       
	       // progressBarStatus+=20;
       	 
       	 	//progressBar.setProgress(progressBarStatus);
       	 	
	        listFavourite.add("EUR"+" = "+convertToUsd(getPreferencesDate("CUR2")));
	        
        }
        else
        {
        	//progressBarStatus+=20;
       	 
        	//progressBar.setProgress(progressBarStatus);
        	
        	listFavourite.add(getPreferencesDate("CUR2")+" = "+convertToUsd(getPreferencesDate("CUR2")));
        	
        }
        
        if(getPreferencesDate("CUR3").length() <=0)
        {
	        SavePreferences("CUR3", "JPY");
	        
	        //progressBarStatus+=20;
       	 
       	 	//progressBar.setProgress(progressBarStatus);
	        
	     
	        listFavourite.add("JPY"+" = "+convertToUsd(getPreferencesDate("CUR3")));
	       
        }
        else
        {
        	//progressBarStatus+=20;
       	 
        	//progressBar.setProgress(progressBarStatus);
        
        	listFavourite.add(getPreferencesDate("CUR3")+" = "+convertToUsd(getPreferencesDate("CUR3")));
        	
        }
        
        if(getPreferencesDate("CUR4").length() <=0)
        {
	        SavePreferences("CUR4", "AUD");
	        
	        //progressBarStatus+=20;
       	 
       	 	//progressBar.setProgress(progressBarStatus);
	        
	       
	        listFavourite.add("AUD"+" = "+convertToUsd(getPreferencesDate("CUR4")));
	       
        }
        else
        {
        	//progressBarStatus+=20;
       	 
       	 	//progressBar.setProgress(progressBarStatus);
        	
        	listFavourite.add(getPreferencesDate("CUR4")+" = "+convertToUsd(getPreferencesDate("CUR4")));
        	
        }
        
        if(getPreferencesDate("CUR5").length() <=0)
        {
	        SavePreferences("CUR5", "SGD");
	        
	        //progressBarStatus+=20;
       	 
	       // progressBar.setProgress(progressBarStatus);
	        
	       
	        listFavourite.add("SGD"+" = "+convertToUsd(getPreferencesDate("CUR5")));
	      
        }
        else
        {
        	//progressBarStatus+=20;
       	 
        	//progressBar.setProgress(progressBarStatus);
        	
        	listFavourite.add(getPreferencesDate("CUR5")+" = "+convertToUsd(getPreferencesDate("CUR5")));
        }
        
        
       // if(progressBarStatus >= 100)
        	//progressBar.dismiss();
        
        
        Log.v("list oncreate", "size and data: "+listFavourite);
        
       /* dataAdapterFavourite = new ArrayAdapter<String>(this,
        		R.layout.spinner_item, listFavourite);*/
        String[] stockArr = new String[listFavourite.size()];
       // Log.v("arr", ""+stockArr);
        stockArr = listFavourite.toArray(stockArr);
        
        
        dataAdapterFavourite = new MobileArrayAdapter(this, stockArr);
        
        
        listView = (ListView)this.findViewById(R.id.listView);
        //dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listFavourite);
        listView.setAdapter(dataAdapterFavourite);
        
        
        txtInfo = (TextView)this.findViewById(R.id.txtInfo);
        
        btnSetting = (ImageButton)this.findViewById(R.id.btnSetting);
        
        btnSetting.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				LinearLayout settingLayout = (LinearLayout)findViewById(R.id.linSetting);
		        if(settingLayout == null){
		            //Inflate the Hidden Layout Information View
		            myLayout = (RelativeLayout)findViewById(R.id.linMain);
		            hiddenInfo = getLayoutInflater().inflate(R.layout.setting_layout, myLayout, false);
		            myLayout.addView(hiddenInfo);
		            initSettingPortion();
		            isOnSettingPage = true;
		            
		            if(backCount == 1)
		            {
		            	backCount = 0;
		            }
		            
		            if(getPreferencesDateFirstTime("FIRST") == false)
		            {
		            	txtInfo = (TextView)HomeScreen.this.findViewById(R.id.txtInfo);
		            	
		            	txtInfo.setVisibility(View.GONE);
		            }
		        }
			}
		});
        
        linText = (LinearLayout)this.findViewById(R.id.linText);
        
       
        txtFrom = (EditText)this.findViewById(R.id.txtFrom);
        
        txtResult = (TextView)this.findViewById(R.id.txtResult);
        
       LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT ,LayoutParams.FILL_PARENT); 
        int xx = params.width; //Your X coordinate
        int yy = params.height; //Your Y coordinate
     /*
        txtResult.setLayoutParams(params);
        txtResult.setTextColor(Color.WHITE);
        txtResult.setTextSize(20);
        txtResult.setText("Result: 0.0");*/
        
        
        btnConvert = (ImageButton)this.findViewById(R.id.btnSwitch);
        btnConvert.setTag("Convert");
        btnConvert.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				if(isNetworkAvailable())
				{
				
				
						//if(btnConvert.getTag().toString().equals("Convert") && txtFrom.getText().toString().length() > 0)
						//{
							btnConvert.setTag("Try Again");
							
						//}
						//else 
							//btnConvert.setTag("Convert");
						
						//if(btnConvert.getTag().equals("Try Again"))
						//{
							
							//linText.addView(txtResult);
							val = txtFrom.getText().toString();
							
							String str = new RequestTask().execute("http://sporix.net/mobileapp/currency/index.php?amount="+(txtFrom.getText().toString())+"&from="+getFromName()+"&to="+getToName()).toString();
							//txtResult.setText(str);
							//Log.v("clicked", "val: "+str);
							setOutput(str);
							//txtFrom.setVisibility(View.GONE);
							
						
							//btnConvert.setTag("Try Again");
							
						//}
						//else
						//{
							//txtFrom.setVisibility(View.VISIBLE);
							//txtFrom.setText("");
							//txtResult.setText("");
							//linText.removeView(txtResult);
						//}
				
				}
			}
		});
        
       
       
        if(getPreferencesDateFirstTime("FIRST"))
        {
        	SavePreferencesFirstTime("FIRST", false);
        	
        	LinearLayout settingLayout = (LinearLayout)findViewById(R.id.linSetting);
	        if(settingLayout == null){
	            //Inflate the Hidden Layout Information View
	            myLayout = (RelativeLayout)findViewById(R.id.linMain);
	            hiddenInfo = getLayoutInflater().inflate(R.layout.setting_layout, myLayout, false);
	            myLayout.addView(hiddenInfo);
	            initSettingPortion();
	        }
        }
        
       
        
        
      //  Log.v("getfirsttime", "val: "+getPreferencesDateFirstTime("FIRST"));
        
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_activity_scurrency, menu);
        return true;
    }

    private class RequestTask extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... uri) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = null;
            
            
           
            
            try {
                response = httpclient.execute(new HttpGet(uri[0]));
                StatusLine statusLine = response.getStatusLine();
                if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    out.close();
                    responseString = out.toString();
                    
                    Log.v("output", "output: "+responseString);
                    
                    setOutput(responseString);
                } 
                
               else{
                    //Closes the connection.
            	  
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                    
                   
                }
            } catch (ClientProtocolException e) {
                //TODO Handle problems..
            } catch (IOException e) {
                //TODO Handle problems..
            }
            return responseString;
        }
        
        @Override    
        protected void onPreExecute() 
        {       
            super.onPreExecute();
            setDialog();
        }
        
        
        
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            
            txtResult.setText("Result: "+getOutput());
            
            
            if(dialog != null)
            {
            	dialog.dismiss();
            	dialog = null;
            }
            
            //Do anything with response..
        }
    }
    
    
    private class RequestTask2 extends AsyncTask<String, String, String>{

    	
    	
        @Override
        protected String doInBackground(String... uri) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = null;
            
          
       
            
            try {
                response = httpclient.execute(new HttpGet(uri[0]));
                StatusLine statusLine = response.getStatusLine();
                if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    out.close();
                    responseString = out.toString();
                    
                    Log.v("output", "output: "+responseString);
                    
                   // setSecondaryCon(responseString);
                } 
                
               else{
                    //Closes the connection.
            	  
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                    
                   
                }
            } catch (ClientProtocolException e) {
                //TODO Handle problems..
            } catch (IOException e) {
                //TODO Handle problems..
            }
            return responseString;
        }
        
        @Override    
        protected void onPreExecute() 
        {       
            super.onPreExecute();
            setDialog();
            isFinished = false;
        }
        
        
        
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
           
          Log.v("res", "res is: "+result);
          
            setSecondaryCon(result);
            if(dialog != null)
            {
            	dialog.dismiss();
            	dialog = null;
            }
            
            //Do anything with response..
        }
    }
    
    public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
	
	private void setDialog()
	{
		if(dialog == null)
		{
			dialog = new ProgressDialog(this);
	        dialog.setMessage("Loading, please wait");
	        dialog.show();
	    }
	}
	
	private void initSettingPortion()
	{
		 dataAdapterSetting = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayCurrency);
		
		 spin1 = (Spinner)this.findViewById(R.id.spin1);
		 spin1.setAdapter(dataAdapterSetting);
		 spin1.setSelection(dataAdapterSetting.getPosition(getPreferencesDate("CUR1")));
		 
		 spin2 = (Spinner)this.findViewById(R.id.spin2);
		 spin2.setAdapter(dataAdapterSetting);
		 spin2.setSelection(dataAdapterSetting.getPosition(getPreferencesDate("CUR2")));
		 
		 spin3 = (Spinner)this.findViewById(R.id.spin3);
		 spin3.setAdapter(dataAdapterSetting);
		 spin3.setSelection(dataAdapterSetting.getPosition(getPreferencesDate("CUR3")));
		 
		 spin4 = (Spinner)this.findViewById(R.id.spin4);
		 spin4.setAdapter(dataAdapterSetting);
		 spin4.setSelection(dataAdapterSetting.getPosition(getPreferencesDate("CUR4")));
		 
		 spin5 = (Spinner)this.findViewById(R.id.spin5);
		 spin5.setAdapter(dataAdapterSetting);
		 spin5.setSelection(dataAdapterSetting.getPosition(getPreferencesDate("CUR5")));
		 
		 btnOkSetting = (Button)this.findViewById(R.id.btnOkSetting);
		 
		 
		 btnOkSetting.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 myLayout.removeView(hiddenInfo);
				 hiddenInfo = null;
				 listFavourite.clear();
				 backCount++;
		         
				 isOnSettingPage = true;
				 
				 SavePreferences("CUR1", spin1.getSelectedItem().toString());
				 listFavourite.add(spin1.getSelectedItem().toString()+" = "+convertToUsd(getPreferencesDate("CUR1")));
				 SavePreferences("CUR2", spin2.getSelectedItem().toString());
				 listFavourite.add(spin2.getSelectedItem().toString()+" = "+convertToUsd(getPreferencesDate("CUR2")));
				 SavePreferences("CUR3", spin3.getSelectedItem().toString());
				 listFavourite.add(spin3.getSelectedItem().toString()+" = "+convertToUsd(getPreferencesDate("CUR3")));
				 SavePreferences("CUR4", spin4.getSelectedItem().toString());
				 listFavourite.add(spin4.getSelectedItem().toString()+" = "+convertToUsd(getPreferencesDate("CUR4")));
				 SavePreferences("CUR5", spin5.getSelectedItem().toString());
				 listFavourite.add(spin5.getSelectedItem().toString()+" = "+convertToUsd(getPreferencesDate("CUR5")));
				 
				 listView.setAdapter(dataAdapterFavourite);
			}
		});
		 
		 
		 btnCancelSetting = (Button) this.findViewById(R.id.btnCancelSetting);
		 btnCancelSetting.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				backCount++;
				
				 myLayout.removeView(hiddenInfo);
				 hiddenInfo = null;
				 /*listFavourite.clear();
		         
				 isOnSettingPage = true;
				 
				 SavePreferences("CUR1", spin1.getSelectedItem().toString());
				 listFavourite.add(spin1.getSelectedItem().toString()+" = "+convertToUsd(getPreferencesDate("CUR1")));
				 SavePreferences("CUR2", spin2.getSelectedItem().toString());
				 listFavourite.add(spin2.getSelectedItem().toString()+" = "+convertToUsd(getPreferencesDate("CUR2")));
				 SavePreferences("CUR3", spin3.getSelectedItem().toString());
				 listFavourite.add(spin3.getSelectedItem().toString()+" = "+convertToUsd(getPreferencesDate("CUR3")));
				 SavePreferences("CUR4", spin4.getSelectedItem().toString());
				 listFavourite.add(spin4.getSelectedItem().toString()+" = "+convertToUsd(getPreferencesDate("CUR4")));
				 SavePreferences("CUR5", spin5.getSelectedItem().toString());
				 listFavourite.add(spin5.getSelectedItem().toString()+" = "+convertToUsd(getPreferencesDate("CUR5")));
				 
				 listView.setAdapter(dataAdapterFavourite);*/
			}
		});
		 
		 
		
	}
	
	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	
	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}
	
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	 private void SavePreferences(String key, String value)
	 {
         SharedPreferences sharedPreferences = this.getSharedPreferences("net.sporix.scurrencyconverter", Context.MODE_PRIVATE);
         SharedPreferences.Editor editor = sharedPreferences.edit();
         //editor.putString(key, value);
         editor.putString(key, value);
         editor.commit();
     }
	 
	 
	 private void SavePreferencesFirstTime(String key, boolean value)
	 {
         SharedPreferences sharedPreferences = this.getSharedPreferences("net.sporix.scurrencyconverter", Context.MODE_PRIVATE);
         SharedPreferences.Editor editor = sharedPreferences.edit();
         //editor.putString(key, value);
         editor.putBoolean(key, value);
         editor.commit();
     }
	 
	 private boolean getPreferencesDateFirstTime(String key)
	 {
         SharedPreferences sharedPreferences = this.getSharedPreferences("net.sporix.scurrencyconverter", Context.MODE_PRIVATE);
         boolean strSavedMem1 = sharedPreferences.getBoolean(key, true);
        
         return strSavedMem1;
         
     }
	 
	 
	 
	 private String getPreferencesDate(String key)
	 {
         SharedPreferences sharedPreferences = this.getSharedPreferences("net.sporix.scurrencyconverter", Context.MODE_PRIVATE);
         String strSavedMem1 = sharedPreferences.getString(key, "");
        
         return strSavedMem1;
         
     }
     
	
	 private String convertToUsd(String value)
	 {
		 //http://sporix.net/mobileapp/currency/index.php?amount=5&from=USD&to=BDT
		 
		 
		 
		 AsyncTask<String, String, String> str = new RequestTask2().execute("http://sporix.net/mobileapp/currency/index.php?amount=1&from=USD&to="+value);
		 String str2 = "";
		
		// setSecondaryCon(str);
		 try {
			Log.v("inside", "convertToUsd: "+ str.get());
			str2 = str.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
		 return str2;
	 }
	 
	 
	 private void setSecondaryCon(String val)
	 {
		 this.secondaryConversion = val;
	 }
	 private String getSecondaryCon()
	 {
		 return this.secondaryConversion;
	 }
	 
	 @Override
	    public boolean onKeyDown(int keyCode, KeyEvent event) {
	    super.onKeyDown(keyCode, event);
	    
	    if(keyCode == KeyEvent.KEYCODE_BACK)
	    {
	    	backCount++;
	    	
	    	if(isOnSettingPage)
	    	{
	    		 myLayout.removeView(hiddenInfo);
				 hiddenInfo = null;
				
	    		/* myLayout.removeView(hiddenInfo);
				 hiddenInfo = null;
				 listFavourite.clear();
				 
				 SavePreferences("CUR1", spin1.getSelectedItem().toString());
				 listFavourite.add(spin1.getSelectedItem().toString()+" = "+convertToUsd(getPreferencesDate("CUR1")));
				 SavePreferences("CUR2", spin2.getSelectedItem().toString());
				 listFavourite.add(spin2.getSelectedItem().toString()+" = "+convertToUsd(getPreferencesDate("CUR2")));
				 SavePreferences("CUR3", spin3.getSelectedItem().toString());
				 listFavourite.add(spin3.getSelectedItem().toString()+" = "+convertToUsd(getPreferencesDate("CUR3")));
				 SavePreferences("CUR4", spin4.getSelectedItem().toString());
				 listFavourite.add(spin4.getSelectedItem().toString()+" = "+convertToUsd(getPreferencesDate("CUR4")));
				 SavePreferences("CUR5", spin5.getSelectedItem().toString());
				 listFavourite.add(spin5.getSelectedItem().toString()+" = "+convertToUsd(getPreferencesDate("CUR5")));
				 
				 listView.setAdapter(dataAdapterFavourite);*/
	    	}
	    	else
	    		backCount++;
	    		
	    }
	    
	    if(backCount >= 2)
	    {
	    	exitPopUp();
	    	//HomeScreen.this.finish();
	    }
	    
	    Log.v("back", "count: "+backCount);
	    
	    return false;
	 }
	 
	 private void exitPopUp()
		{
			AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);     
			alt_bld.setMessage("Would you like to exit SCUrrency Converter?");
			alt_bld.setTitle("SCUrrency Converter");
			alt_bld.setCancelable(false);
//			/alt_bld.setIcon(R.drawable.icon);
			
			alt_bld.setNegativeButton("No", new DialogInterface.OnClickListener() {
			      public void onClick(DialogInterface dialog, int which) {

			                        // TODO Auto-generated method stub
			    	  dialog.dismiss();
			          

			    } });
			
			alt_bld.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			      public void onClick(DialogInterface dialog, int which) {

			                        // TODO Auto-generated method stub
			    	  
			             HomeScreen.this.finish();          

			    } });
			
			
			
			
			
			alt_bld.show();
			
			
			
			//AlertDialog alert = alt_bld.create();

		}
	
	
}
