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
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.AccountPicker;
import com.melnykov.fab.FloatingActionButton;
import com.mobsandgeeks.saripaar.QuickRule;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import br.com.rarolabs.rvp.api.rvpAPI.model.Endereco;
import br.com.rarolabs.rvp.api.rvpAPI.model.Usuario;
import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.services.tasks.CriarNovaRedeAsyncTask;
import rarolabs.com.br.rvp.services.tasks.TornarMembroAsyncTask;
import rarolabs.com.br.rvp.utils.ImageUtil;

public class CadastroActivity extends ActionBarActivity implements Validator.ValidationListener {
    private static final int SELECT_PHOTO = 100;

    private static final String NOME = "cadastro_nome";
    private static final String DDD_FIXO = "cadastro_ddd_fixo";
    private static final String TEL_FIXO = "cadastro_fone_fixo";
    private static final String DDD_CEL = "cadastro_ddd_cel";
    private static final String TEL_CEL = "cadastro_fone_cel";

    private String account;
    private SharedPreferences settings;

    @NotEmpty(messageResId = R.string.error_cadastro_nome)
    private EditText nome;

    @NotEmpty(messageResId = R.string.error_ddd_fixo)
    private EditText dddFixo;

    @NotEmpty(messageResId = R.string.error_tel_fixo)
    private EditText telFixo;

    @NotEmpty(messageResId = R.string.error_ddd_cel)
    private EditText dddCel;

    @NotEmpty(messageResId = R.string.error_tel_cel)
    private EditText telCel;

    @NotEmpty(messageResId = R.string.error_endereco)
    private EditText endereco;

    private Validator validator;
    private Long idRede;
    private Spinner visibilidadeFixo;
    private Spinner visibilidadeCel;
    private Spinner visibilidadeEndereco;
    private double userLatitude;
    private double userLongitute;
    private double latitude;
    private double longitude;
    private ImageView profile;
    private String email;
    private EditText nomeRede;
    private boolean novaRede;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        if(getActionBar()!= null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }else{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        settings = getSharedPreferences("RVP", 0);


        validator = new Validator(this);
        validator.setValidationListener(this);

        Intent i = getIntent();
        Log.d("Cadastro", "Email:" + i.getExtras().getString(RedeActivity.PREF_ACCOUNT_NAME));
        Log.d("Cadastro", "RedeID:" + i.getExtras().getLong(WelcomeActivity.EXTRA_ID_REDE));

        account = i.getExtras().getString(RedeActivity.PREF_ACCOUNT_NAME);
        idRede = i.getExtras().getLong(WelcomeActivity.EXTRA_ID_REDE);

        nome = ((EditText)findViewById(R.id.cadastro_nome));
        dddFixo = ((EditText)findViewById(R.id.ddd_fixo));
        telFixo = ((EditText)findViewById(R.id.tel_fixo));
        dddCel = ((EditText)findViewById(R.id.ddd_cel));
        telCel = ((EditText)findViewById(R.id.tel_cel));
        endereco = ((EditText)findViewById(R.id.cadastro_endereco));

        visibilidadeFixo = (Spinner) findViewById(R.id.visibilidade_fixo);
        visibilidadeCel = (Spinner) findViewById(R.id.visibilidade_fixo);
        visibilidadeEndereco = (Spinner) findViewById(R.id.visibilidade_fixo);

        nomeRede = (EditText) findViewById(R.id.nome_rede);
        novaRede = i.getExtras().getBoolean("NOVA_REDE",false);
        if(novaRede){
            nomeRede.setVisibility(View.VISIBLE);
            validator.put(nomeRede, new QuickRule() {
                @Override
                public boolean isValid(View view) {
                    return !((EditText)view).getText().equals("");
                }

                @Override
                public boolean isValid(Object o) {
                    return isValid((View)o);
                }

                @Override
                public String getMessage(Context context) {
                    return "Informe o nome da rede";
                }
            });
            }else{
            nomeRede.setVisibility(View.GONE);
        }


        ((FloatingActionButton) findViewById(R.id.trocar_foto)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trocarFoto();
            }
        });

        profile = (ImageView) findViewById(R.id.profile_image);

        loadAccount();
        loadFromPrefs();
        loadAddress();

    }

    private void loadAccount() {
        String account = settings.getString("account", null);
        if(account == null){
            pickUserAccount();
        }
    }

    private void trocarFoto() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case  REQUEST_CODE_PICK_ACCOUNT:
                // Receiving a result from the AccountPicker
                if (resultCode == RESULT_OK) {
                    email = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    // With the account name acquired, go get the auth token
                    setSelectedAccountName(email);
                } else if (resultCode == RESULT_CANCELED) {
                    // The account picker dialog closed without selecting an account.
                    // Notify users that they must pick an account to proceed.
                    Toast.makeText(this, "Você precisa selecionar uma conta antes de continuar", Toast.LENGTH_SHORT).show();
                    finish();
                }


            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    InputStream imageStream = null;
                    try {
                        Bitmap profileImage = ImageUtil.decodeUri(this, selectedImage);
                        profile.setImageBitmap(profileImage);

                        Bitmap blur = ImageUtil.fastblur(profileImage, 30);
                        ((LinearLayout) findViewById(R.id.profile_image_bg)).setBackgroundDrawable(new BitmapDrawable(getResources(), blur));

                        ImageUtil.saveToInternalSorage(this,profileImage,"profile.jpg");
                        ImageUtil.saveToInternalSorage(this,blur,"profile_blur.jpg");

                        SharedPreferences.Editor editor = settings.edit();
                        editor.putBoolean("PROFILE_IMAGE", true);
                        editor.commit();


                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
        }
    }


    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
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

    private void loadAddress() {
        settings = getSharedPreferences("RVP",0);
        latitude = Double.parseDouble(settings.getString("USER_LATITUDE", "0"));
        longitude = Double.parseDouble(settings.getString("USER_LONGITUDE", "0"));
        Log.d("ADDR","Latitude:" + latitude);
        Log.d("ADDR","Longitude:" + longitude);
        String addr = getCompleteAddressString(latitude,longitude);
        Log.d("ADDR", "Add:" + addr);
        endereco.setText(addr);


    }

    private void loadFromPrefs() {

        nome.setText(settings.getString(NOME, null));
        dddFixo.setText(settings.getString(DDD_FIXO, null));
        telFixo.setText(settings.getString(TEL_FIXO, null));
        dddCel.setText(settings.getString(DDD_CEL, null));
        telCel.setText(settings.getString(TEL_CEL, null));


        if(settings.getBoolean("PROFILE_IMAGE", false)){
            Log.d("Image Profile", "Carregando das preferencias" );
            profile.setImageBitmap(ImageUtil.loadImageFromStorage(this,"profile.jpg"));
            ((LinearLayout) findViewById(R.id.profile_image_bg)).setBackgroundDrawable(new BitmapDrawable(getResources(), ImageUtil.loadImageFromStorage(this,"profile_blur.jpg")));
        }
    }

    private void saveFromPrefs() {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(NOME, nome.getText().toString());
        editor.putString(DDD_FIXO, dddFixo.getText().toString());
        editor.putString(TEL_FIXO, telFixo.getText().toString());
        editor.putString(DDD_CEL, dddCel.getText().toString());
        editor.putString(TEL_CEL, telCel.getText().toString());
        editor.commit();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cadastro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){

            case  R.id.action_confirmar:
                Toast.makeText(this,"Enviando solicitação",Toast.LENGTH_LONG).show();
                validator.validate();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this,"Back",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onValidationSucceeded() {

        Usuario u = new Usuario();
        u.setNome(nome.getText().toString());
        u.setDddTelefoneCelular(dddCel.getText().toString());
        u.setDddTelefoneFixo(dddFixo.getText().toString());
        u.setTelefoneCelular(telCel.getText().toString());
        u.setTelefoneFixo(telFixo.getText().toString());
        u.setEmail(account);


        Endereco e = new Endereco();
        e.setLatitude(latitude);
        e.setLongitude(longitude);
        e.setDescricao(endereco.getText().toString());


        if(novaRede) {
            Object[] params = {u,e,nomeRede.getText().toString(),getVisibilidade()};
            new CriarNovaRedeAsyncTask(CadastroActivity.this).execute(params);
        }else{
            Object[] params = {u,e,idRede,getVisibilidade()};
            new TornarMembroAsyncTask(CadastroActivity.this).execute(params);
        }


    }

    private String[] getVisibilidade() {
        String[] v = new String[3];
        v[0] = parseVisibilidade(visibilidadeFixo);
        v[1] = parseVisibilidade(visibilidadeCel);
        v[2] = parseVisibilidade(visibilidadeEndereco);

        Log.d("Visibilidade", "Vi:" + v[0]);
        Log.d("Visibilidade", "Vi:" + v[1]);
        Log.d("Visibilidade", "Vi:" + v[2]);
        return v;

    }

    private String parseVisibilidade(Spinner visibilidade) {
        Log.d("Visibilidade","Spinner:" + visibilidade.getSelectedItem().toString());
        String current = (String) visibilidade.getSelectedItem();
        if(current.equals(getResources().getString(R.string.todos_membros))){
            return "PUBLICO";
        }else if(current.equals(getResources().getString(R.string.somente_autoridade_policial))){
                return "SOMENTE_COM_AUTORIDADE";
        }else if(current.equals(getResources().getString(R.string.ninguem))){
            return "PRIVADO";
        }
        return null;

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for(ValidationError error : errors){
           if(error.getView() instanceof TextView){
               ((TextView) error.getView()).setError(error.getCollatedErrorMessage(this));
           }
        }
    }


    public void error(String descricao) {
        Toast.makeText(this,descricao,Toast.LENGTH_LONG).show();
    }

    public void ok() {

        Toast.makeText(this,"Sua solicitação foi enviada com sucesso!",Toast.LENGTH_SHORT).show();
        saveFromPrefs();

    }

    static final int REQUEST_CODE_PICK_ACCOUNT = 1000;

    private void pickUserAccount() {
        String[] accountTypes = new String[]{"com.google"};
        Intent intent = AccountPicker.newChooseAccountIntent(null, null,
                accountTypes, false, null, null, null, null);
        startActivityForResult(intent, REQUEST_CODE_PICK_ACCOUNT);
    }


    private void setSelectedAccountName(String accountName) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("account", accountName);
        editor.commit();
    }


}
