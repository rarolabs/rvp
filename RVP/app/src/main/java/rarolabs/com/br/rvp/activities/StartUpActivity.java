package rarolabs.com.br.rvp.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import rarolabs.com.br.rvp.config.Constants;

/**
 * Created by rodrigosol on 2/5/15.
 */
public class StartUpActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        SharedPreferences settings = getSharedPreferences("RVP", 0);
        Intent i = null;
        Log.w("StartUp","Welcome:" +  settings.getBoolean(Constants.WELCOME,true));
        if(settings.getBoolean(Constants.WELCOME,true)){
            i = new Intent(this,WelcomeActivity.class);
        }else{
            i = new Intent(this,MainActivity.class);
        }
        finish();
        startActivity(i);

    }
}
