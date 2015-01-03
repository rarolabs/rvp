package rarolabs.com.br.rvp;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import static android.accounts.AccountManager.newChooseAccountIntent;


public class HomeActivity extends Activity {

    private static final int REQUEST_ACCOUNT_PICKER = 2;
    private SharedPreferences settings;
    private String accountName;
    private GoogleAccountCredential credential;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

}
