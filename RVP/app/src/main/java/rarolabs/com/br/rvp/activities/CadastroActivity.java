package rarolabs.com.br.rvp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import br.com.rarolabs.rvp.api.rvpAPI.model.Endereco;
import br.com.rarolabs.rvp.api.rvpAPI.model.GeoqueryResponder;
import br.com.rarolabs.rvp.api.rvpAPI.model.Rede;
import br.com.rarolabs.rvp.api.rvpAPI.model.Usuario;
import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.services.TornarMembroAsyncTask;

public class CadastroActivity extends ActionBarActivity implements Validator.ValidationListener {

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


    private Validator validator;
    private Long idRede;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        if(getActionBar()!= null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }else{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

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


        settings = getSharedPreferences("RVP", 0);
        loadFromPrefs();
    }

    private void loadFromPrefs() {

        nome.setText(settings.getString(NOME, null));
        dddFixo.setText(settings.getString(DDD_FIXO, null));
        telFixo.setText(settings.getString(TEL_FIXO, null));
        dddCel.setText(settings.getString(DDD_CEL, null));
        telCel.setText(settings.getString(TEL_CEL, null));
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_confirmar) {
            Toast.makeText(this,"Vou validar!",Toast.LENGTH_LONG).show();
            validator.validate();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        e.setLatitude(1.0);
        e.setLongitude(1.0);
        Object[] params = {u,e,idRede};

        new TornarMembroAsyncTask(CadastroActivity.this).execute(params);


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
}
