package net.sporix.scurrencyconverter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivitySCurrency extends Activity {

private static final int SPLASH_DISPLAY_TIME = 4500; /* 3 seconds */

public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.splash);

    new Handler().postDelayed(new Runnable() {

        public void run() {

            Intent mainIntent = new Intent(MainActivitySCurrency.this,
                    HomeScreen.class);
            MainActivitySCurrency.this.finish();
            MainActivitySCurrency.this.startActivity(mainIntent);

           
          //  overridePendingTransition(R.anim.mainfadein,
	                    //R.anim.splashfadeout);
	        }
	    }, SPLASH_DISPLAY_TIME);
	}
}