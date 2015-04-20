package rarolabs.com.br.rvp.activities;

import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.AccountPicker;
import com.melnykov.fab.FloatingActionButton;
import com.mobsandgeeks.saripaar.QuickRule;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Size;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


import br.com.rarolabs.rvp.api.rvpAPI.model.Endereco;
import br.com.rarolabs.rvp.api.rvpAPI.model.Usuario;
import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.services.tasks.AtualizarAvatarAsyncTask;
import rarolabs.com.br.rvp.services.tasks.CriarNovaRedeAsyncTask;
import rarolabs.com.br.rvp.services.tasks.TornarMembroAsyncTask;
import rarolabs.com.br.rvp.utils.ImageUtil;

public class CadastroActivity extends RVPActivity implements Validator.ValidationListener, View.OnFocusChangeListener {

    private static final Object[] nomeParams = {R.id.icon_nome, R.drawable.ic_cadastro_nome_normal, R.drawable.ic_cadastro_nome_focus};
    private static final Object[] fixoParams = {R.id.icon_tel_fixo, R.drawable.ic_cadastro_telefone_normal, R.drawable.ic_cadastro_telefone_focus};
    private static final Object[] celParams = {R.id.icon_tel_cel, R.drawable.ic_cadastro_telefone_normal, R.drawable.ic_cadastro_telefone_focus};
    private static final Object[] enderecoParams = {R.id.icon_endereco, R.drawable.ic_cadastro_end_normal, R.drawable.ic_cadastro_end_focus};
    private static final HashMap<Integer,Object[]> iconMapping = new HashMap<Integer,Object[]>();

    static{
        iconMapping.put(R.id.ddd_fixo,fixoParams);
        iconMapping.put(R.id.cadastro_nome,nomeParams);
        iconMapping.put(R.id.tel_fixo,fixoParams);
        iconMapping.put(R.id.ddd_cel,celParams);
        iconMapping.put(R.id.tel_cel,celParams);
        iconMapping.put(R.id.cadastro_endereco,enderecoParams);
    }

    private String account;
    private SharedPreferences settings;

    @NotEmpty(messageResId = R.string.error_cadastro_nome)
    private EditText nome;

//    @NotEmpty(messageResId = R.string.error_ddd_fixo)
//    @Size(min = 2, max = 2, messageResId = R.string.tamanho_ddd_invalido)
    private EditText dddFixo;

//    @Size(min = 8, max = 9, messageResId = R.string.tamanho_telefone_invalido)
//    @NotEmpty(messageResId = R.string.error_tel_fixo)
    private EditText telFixo;

    @Size(min = 2, max = 2, messageResId = R.string.tamanho_ddd_invalido)
    @NotEmpty(messageResId = R.string.error_ddd_cel)
    private EditText dddCel;

    @Size(min = 8, max = 9, messageResId = R.string.tamanho_telefone_invalido)
    @NotEmpty(messageResId = R.string.error_tel_cel)
    private EditText telCel;

    @NotEmpty(messageResId = R.string.error_endereco)
    @Size(min = 6, messageResId = R.string.tamanho_endereco_invalido)
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
    ProgressDialog progress;
    private boolean photoAtualizada;
    private File fileProfile;
    private File fileProfileBlur;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        Log.d("Cadastro Actitivity", "OnCreate");
        setContentView(R.layout.activity_cadastro);

        settings = getSharedPreferences("RVP", 0);


        validator = new Validator(this);
        validator.setValidationListener(this);

        Intent i = getIntent();

        Log.d("Cadastro", "Email:" + i.getExtras().getString(Constants.ACCOUNT));
        Log.d("Cadastro", "RedeID:" + i.getExtras().getLong(Constants.EXTRA_ID_REDE));



        account = i.getExtras().getString(Constants.ACCOUNT);
        idRede = i.getExtras().getLong(Constants.EXTRA_ID_REDE);

        nome = ((EditText)findViewById(R.id.cadastro_nome));
        nome.setOnFocusChangeListener(this);
        InputMethodManager imn = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imn.hideSoftInputFromWindow(nome.getWindowToken(), 0);

        dddFixo = ((EditText)findViewById(R.id.ddd_fixo));
        //Close keyboard
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(dddFixo.getWindowToken(), 0);

        dddFixo.setOnFocusChangeListener(this);

        telFixo = ((EditText)findViewById(R.id.tel_fixo));
        imm.hideSoftInputFromWindow(telFixo.getWindowToken(), 0); //Close keyboard
        telFixo.setOnFocusChangeListener(this);

        dddCel = ((EditText)findViewById(R.id.ddd_cel));
        imm.hideSoftInputFromWindow(dddCel.getWindowToken(), 0); //Close keyboard
        dddCel.setOnFocusChangeListener(this);

        telCel = ((EditText)findViewById(R.id.tel_cel));
        imm.hideSoftInputFromWindow(telCel.getWindowToken(), 0); //Close keyboard
        telCel.setOnFocusChangeListener(this);

        endereco = ((EditText)findViewById(R.id.cadastro_endereco));
        endereco.setOnFocusChangeListener(this);


        visibilidadeFixo = (Spinner) findViewById(R.id.visibilidade_fixo);
        visibilidadeFixo.setAdapter(ArrayAdapter.createFromResource(this,R.array.permissoes,R.layout.spinner_item));
        visibilidadeCel = (Spinner) findViewById(R.id.visibilidade_cel);
        visibilidadeCel.setAdapter(ArrayAdapter.createFromResource(this,R.array.permissoes,R.layout.spinner_item));
        visibilidadeEndereco = (Spinner) findViewById(R.id.visibilidade_endereco);
        visibilidadeEndereco.setAdapter(ArrayAdapter.createFromResource(this,R.array.permissoes,R.layout.spinner_item));

        nomeRede = (EditText) findViewById(R.id.nome_rede);
        novaRede = i.getExtras().getBoolean("NOVA_REDE",false);
        if(novaRede){
            nomeRede.setVisibility(View.VISIBLE);
            validator.put(nomeRede, new QuickRule() {
                @Override
                public boolean isValid(View view) {
                    return !((EditText)view).getText().toString().equals("");
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

        enableNotificacoes((RelativeLayout) findViewById(R.id.notificacao));

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

        nome.setText(settings.getString(Constants.NOME, null));
        dddFixo.setText(settings.getString(Constants.DDD_FIXO, null));
        telFixo.setText(settings.getString(Constants.TEL_FIXO, null));
        dddCel.setText(settings.getString(Constants.DDD_CEL, null));
        telCel.setText(settings.getString(Constants.TEL_CEL, null));


        if(settings.getBoolean("PROFILE_IMAGE", false)){
            Log.d("Image Profile", "Carregando das preferencias" );
            profile.setImageBitmap(ImageUtil.loadImageFromStorage(this,"profile.jpg"));
            ((LinearLayout) findViewById(R.id.profile_image_bg)).setBackgroundDrawable(new BitmapDrawable(getResources(), ImageUtil.loadImageFromStorage(this,"profile_blur.jpg")));
        }
    }

    private void saveFromPrefs() {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Constants.NOME, nome.getText().toString());
        editor.putString(Constants.DDD_FIXO, dddFixo.getText().toString());
        editor.putString(Constants.TEL_FIXO, telFixo.getText().toString());
        editor.putString(Constants.DDD_CEL, dddCel.getText().toString());
        editor.putString(Constants.TEL_CEL, telCel.getText().toString());
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
        hideKeyboard();
        progress = ProgressDialog.show(this, getString(R.string.aguarde),
                getString(R.string.enviando_solicitacao, true));

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
        progress.dismiss();
        Toast.makeText(this,descricao,Toast.LENGTH_LONG).show();
    }

    public void ok() {
        progress.dismiss();
        Toast.makeText(this,"Sua solicitação foi enviada com sucesso!",Toast.LENGTH_SHORT).show();
        saveFromPrefs();

        if(photoAtualizada){

            new AtualizarAvatarAsyncTask(this).execute(fileProfile,fileProfileBlur);
        }

        if(settings.getBoolean(Constants.PREF_NEW_USER, true)){
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(Constants.PREF_NEW_USER,false);
            editor.commit();
        }
        finish();


    }



    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        changeIcon(iconMapping.get(v.getId()),hasFocus);
    }

    private void changeIcon(Object[] params, boolean hasFocus) {
        int idx = hasFocus ? 2 :1 ;
        int icon = (int) params[idx];
        ((ImageView)findViewById((Integer) params[0])).setImageResource(icon);
    }

    @Override
    protected void setRequestCodeSelectPhotoOK(Uri selectedImage) {
        InputStream imageStream = null;
        try {
            Bitmap profileImage = ImageUtil.decodeUri(this, selectedImage);
            profile.setImageBitmap(profileImage);

            Bitmap blur = ImageUtil.fastblur(profileImage, 30);
            ((LinearLayout) findViewById(R.id.profile_image_bg)).setBackgroundDrawable(new BitmapDrawable(getResources(), blur));

            fileProfile = new File(ImageUtil.saveToInternalSorage(this,profileImage,"profile.jpg"));
            fileProfileBlur = new File(ImageUtil.saveToInternalSorage(this,blur,"profile_blur.jpg"));

            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("PROFILE_IMAGE", true);
            editor.commit();

            photoAtualizada = true;


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void setRequestCodePickAccountOK(String email) {
        super.setRequestCodePickAccountOK(email);
        this.email = email;
    }

}
