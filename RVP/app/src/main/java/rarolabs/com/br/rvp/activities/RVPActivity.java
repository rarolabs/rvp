package rarolabs.com.br.rvp.activities;

import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.AccountPicker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.utils.ImageUtil;

/**
 * Created by rodrigosol on 2/9/15.
 */
public class RVPActivity extends ActionBarActivity {
    protected SharedPreferences settings;
    private static final int REQUEST_CODE_PICK_ACCOUNT = 1000;
    private static final int SELECT_PHOTO = 100;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = getSharedPreferences("RVP",0);
    }
    public void trocarFoto() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);

    }


    protected void pickUserAccount() {
        String[] accountTypes = new String[]{"com.google"};
        Intent intent = AccountPicker.newChooseAccountIntent(null, null,
                accountTypes, false, null, null, null, null);
        startActivityForResult(intent, REQUEST_CODE_PICK_ACCOUNT);
    }


    protected void loadAccount() {
        String account = settings.getString("account", null);
        if(account == null){
            pickUserAccount();
        }
    }

    private void setSelectedAccountName(String accountName) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("account", accountName);
        editor.commit();
    }

    protected void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    protected String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append(", ");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("My Current loction address", "" + strReturnedAddress.toString());
            } else {
                Log.w("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current loction address", "Canont get Address!");
        }
        return strAdd;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case  REQUEST_CODE_PICK_ACCOUNT:
                // Receiving a result from the AccountPicker
                if (resultCode == RESULT_OK) {
                    setRequestCodePickAccountOK(data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME));
                } else if (resultCode == RESULT_CANCELED) {
                    setRequestCodePickAccountCanceled();
                }
                break;

            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    setRequestCodeSelectPhotoOK(selectedImage);

                }
        }
    }

    protected void setRequestCodeSelectPhotoOK(Uri selectedImage) {
    }

    protected void setRequestCodePickAccountCanceled() {
        Toast.makeText(this, "Você precisa selecionar uma conta antes de continuar", Toast.LENGTH_SHORT).show();
        finish();
    }

    protected void setRequestCodePickAccountOK(String email) {
        setSelectedAccountName(email);
    }

    protected void disableWelcomeActivity(){
        settings.edit().putBoolean(Constants.WELCOME,true).apply();
    }
}
