package rarolabs.com.br.rvp.activities;

import android.accounts.AccountManager;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.AccountPicker;

import java.util.List;
import java.util.Locale;

import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.activities.alertas.AlertaActivity;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.models.EsquemaAlerta;
import rarolabs.com.br.rvp.models.Notificacao;

/**
 * Created by rodrigosol on 2/9/15.
 */
public class RVPActivity extends ActionBarActivity {
    protected SharedPreferences settings;
    private static final int REQUEST_CODE_PICK_ACCOUNT = 1000;
    private static final int SELECT_PHOTO = 100;
    protected AnimatorSet animations;
    protected TextSwitcher alertaTexto;
    protected Boolean alertaSendoExibido = false;
    protected BroadcastReceiver mReceiverNotificacao;
    protected IntentFilter intentFilter;
    protected Vibrator vibe;
    private RelativeLayout barraNotificacao;
    private ImageView barraNotificacaoIcon;
    private TextView barraNotificacaoText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = getSharedPreferences("RVP",0);

            mReceiverNotificacao = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Log.d("Main", "Alerta rececido");

                    String tipoNotificacao = "";
                    if(barraNotificacao != null){
                        if(intent.getExtras().getString(Constants.EXTRA_NOTIFICACAO_TIPO,"").equals("ALERTA")) {
                            tipoNotificacao = intent.getExtras().getString(Constants.EXTRA_NOTIFICACAO_TIPO_ALERTA, "");
                            mostraBarraNotificacao(tipoNotificacao);
                        }else if(intent.getExtras().getString(Constants.EXTRA_NOTIFICACAO_TIPO,"").equals("MENSAGEM")){
                            Notificacao notificacao = Notificacao.findById(Notificacao.class,intent.getExtras().getLong(Constants.EXTRA_NOTIFICACAO_ID));
                            mostraMensagem(notificacao);
                        }
                    }


                }
            };

            intentFilter = new IntentFilter("rarolabs.com.br.rvp.broadcast.MOSTRA_ALERTA");
            onNewIntent(getIntent());

            vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


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
        settings.edit().putBoolean(Constants.WELCOME,false).apply();
    }

    private void setupAnimation() {
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                42,
                getResources().getDisplayMetrics()
        );

        ObjectAnimator anim1 = ObjectAnimator.ofFloat(barraNotificacao, "TranslationY", px);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(barraNotificacao, "TranslationY", -px);
        anim2.setStartDelay(2000);
        animations = new AnimatorSet();
        animations.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mostrandoAlerta(true);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mostrandoAlerta(false);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mostrandoAlerta(false);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                mostrandoAlerta(false);
            }
        });
        animations.play(anim1).before(anim2);
    }

    private synchronized void mostrandoAlerta(Boolean status) {
        alertaSendoExibido = status;
    }


    public void mostraBarraNotificacao(String tipoAlerta) {
        if(tipoAlerta.equals("")){
            mostrarNotificacao();
        }else{
            mostrarAlerta(tipoAlerta);
        }
    }

    private void mostrarNotificacao() {
        barraNotificacao.setBackgroundColor(getResources().getColor(R.color.material_lime_A400));
        barraNotificacaoIcon.setImageResource(R.drawable.ic_drawer_notificacoes_selected);
        barraNotificacaoText.setTextColor(getResources().getColor(R.color.material_green_700));
        barraNotificacaoText.setText(getString(R.string.notificacao_recebida));
        //

        showNotificationBar();
    }

    public void mostraMensagem(Notificacao notificacao) {
        barraNotificacao.setBackgroundColor(getResources().getColor(R.color.material_lime_A400));
        barraNotificacaoIcon.setImageResource(R.drawable.ic_drawer_notificacoes_selected);
        barraNotificacaoText.setTextColor(getResources().getColor(R.color.material_green_700));
        barraNotificacaoText.setText(notificacao.getUltimoComentario().getTexto());
        showNotificationBar();
    }

    private void mostrarAlerta(String tipo) {
        EsquemaAlerta esquema = EsquemaAlerta.get(tipo);
        barraNotificacao.setBackgroundColor(getResources().getColor(esquema.getActionBarColor()));
        barraNotificacaoIcon.setImageResource(esquema.getHeaderIcon());
        barraNotificacaoText.setTextColor(getResources().getColor(R.color.white));
        barraNotificacaoText.setText(getString(esquema.getNotificationTitle()));
        showNotificationBar();
    }

    private void showNotificationBar(){
        if(barraNotificacao !=null) {
            if (!alertaSendoExibido) {
                if (barraNotificacao != null) {
                    animations.start();
                }
                vibe.vibrate(50);
            }
        }
    }


    protected void enableNotificacoes(RelativeLayout barraNotificacao){
        this.barraNotificacao = barraNotificacao;
        this.barraNotificacaoIcon = (ImageView) barraNotificacao.findViewById(R.id.barra_notificacao_icon);
        this.barraNotificacaoText = (TextView) barraNotificacao.findViewById(R.id.barra_notificacao_texto);

        setupAnimation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiverNotificacao, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiverNotificacao);
    }

}
