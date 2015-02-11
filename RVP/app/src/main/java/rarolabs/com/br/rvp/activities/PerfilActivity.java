package rarolabs.com.br.rvp.activities;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import br.com.rarolabs.rvp.api.rvpAPI.RvpAPI;
import br.com.rarolabs.rvp.api.rvpAPI.model.Membro;
import br.com.rarolabs.rvp.api.rvpAPI.model.Profile;
import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.listeners.GPSTracker;
import rarolabs.com.br.rvp.services.tasks.BuscaPerfilAsyncTask;
import rarolabs.com.br.rvp.services.tasks.TornarAdministradorAsyncTask;
import rarolabs.com.br.rvp.services.tasks.TornarAutoridadeAsyncTask;
import rarolabs.com.br.rvp.utils.ImageUtil;

public class PerfilActivity extends ActionBarActivity {


    private long membroId;
    private ImageView spinner;
    private View loading;
    private TextView nome;
    private TextView endereco;
    private TextView telefoneFixo;
    private TextView telefoneCelular;
    private ImageView profile;
    private SwitchCompat tornarAdministrador;
    private SwitchCompat tornarAutoridade;
    private View containerProfile;
    private View profileBG;
    ProgressDialog progress;
    private View containerAdministrador;
    private View containerAutoridade;
    private CompoundButton.OnCheckedChangeListener adminListener;
    private CompoundButton.OnCheckedChangeListener autoridadeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        getSupportActionBar().setTitle("");
        membroId = getIntent().getExtras().getLong(Constants.EXTRA_MEMBRO_ID, 0l);
        containerProfile = findViewById(R.id.conteiner_profile);

        loading = findViewById(R.id.loading);
        spinner = (ImageView) findViewById(R.id.spinner);
        spinner.startAnimation(
                AnimationUtils.loadAnimation(this, R.anim.rotate_forever));
        nome = (TextView) findViewById(R.id.nome);
        endereco = (TextView) findViewById(R.id.endereco);
        telefoneFixo = (TextView) findViewById(R.id.telefone_fixo);
        telefoneCelular = (TextView) findViewById(R.id.telefone_celular);
        profile = (ImageView) findViewById(R.id.profile_image);
        profileBG = findViewById(R.id.profile_image_bg);
        tornarAdministrador = (SwitchCompat) findViewById(R.id.tornar_administrador);
        tornarAutoridade = (SwitchCompat) findViewById(R.id.tornar_autoridade);

        containerAdministrador = findViewById(R.id.container_admin);
        containerAutoridade = findViewById(R.id.container_autoridade);
        adminListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleAdmin(buttonView.isChecked());
            }
        };
        autoridadeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleAutoridade(buttonView.isChecked());
            }
        };

        new BuscaPerfilAsyncTask(this).execute(membroId);


    }

    private void toggleAutoridade(Boolean checked) {
        progress = ProgressDialog.show(this, getString(R.string.aguarde),
                getString(R.string.enviando_solicitacao, true));
        new TornarAutoridadeAsyncTask(this).execute(checked,membroId);

    }

    private void toggleAdmin(boolean checked) {
        progress = ProgressDialog.show(this, getString(R.string.aguarde),
                getString(R.string.enviando_solicitacao, true));
        new TornarAdministradorAsyncTask(this).execute(checked, membroId);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_perfil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void ok(Profile p) {
        spinner.clearAnimation();
        loading.setVisibility(View.GONE);
        getSupportActionBar().setTitle(p.getNome().split(" ")[0]);
        if (p.getAvatar() != null) {
            ImageUtil.loadIconAssync(p.getAvatar(), profile, 162);
        }
        if (p.getAvatarBlur() != null) {
            ImageUtil.loadIconAssync(p.getAvatarBlur(), profileBG, 216);
        }
        nome.setText(p.getNome());
        if (p.getEndereco() != null) {
            endereco.setText(p.getEndereco());
        } else {
            endereco.setText("Endereço oculto");
        }
        if (p.getTelefoneCelular() != null) {
            telefoneCelular.setText("(" + p.getTelefoneCelular().substring(0, 2) + ") " + p.getTelefoneCelular().substring(2));
        } else {
            telefoneCelular.setText("Telefone celular oculto");
        }
        if (p.getTelefoneFixo() != null) {
            telefoneFixo.setText("(" + p.getTelefoneFixo().substring(0, 2) + ") " + p.getTelefoneFixo().substring(2));
        } else {
            telefoneFixo.setText("Telefone fixo oculto");
        }

        if (p.getPapelDoVisualizado().equals("ADMIN") ||
                p.getPapelDoVisualizado().equals("CRIADOR")) {
            if (p.getPapel().equals("ADMIN")) {
                tornarAdministrador.setChecked(true);
                tornarAutoridade.setChecked(false);
            } else if (p.getPapel().equals("AUTORIDADE")) {
                tornarAdministrador.setChecked(false);
                tornarAutoridade.setChecked(true);
            }

            if (p.getStatus().equals("ATIVO")) {
                enableListerners();            }

        } else {
            containerAdministrador.setVisibility(View.GONE);
            containerAutoridade.setVisibility(View.GONE);

        }


        containerProfile.setVisibility(View.VISIBLE);


    }

    public void error(String descricao) {
        Toast.makeText(this, descricao, Toast.LENGTH_LONG).show();
        finish();
    }


    public void okToggleAdmnistrador() {
        progress.dismiss();
        disableListeners();
        if(tornarAutoridade.isChecked()) {
            tornarAutoridade.setChecked(false);
        }
        enableListerners();
    }

    public void errorToggleAdministrador(String descricao) {
        Toast.makeText(this, descricao, Toast.LENGTH_LONG).show();
        disableListeners();
        tornarAdministrador.setChecked(!tornarAdministrador.isChecked());
        enableListerners();
        progress.dismiss();
    }

    public void errorToggleAutoridade(String descricao) {
        Toast.makeText(this, descricao, Toast.LENGTH_LONG).show();
        disableListeners();
        tornarAutoridade.setChecked(!tornarAutoridade.isChecked());
        enableListerners();
        progress.dismiss();
    }

    public void okToggleAutoridade() {
        progress.dismiss();
        disableListeners();
        if(tornarAdministrador.isChecked()) {
            tornarAdministrador.setChecked(false);
        }
        enableListerners();
   }

    private void enableListerners() {
        tornarAdministrador.setOnCheckedChangeListener(adminListener);
        tornarAutoridade.setOnCheckedChangeListener(autoridadeListener);
    }

    private void disableListeners() {
        tornarAdministrador.setOnCheckedChangeListener(null);
        tornarAutoridade.setOnCheckedChangeListener(null);
    }

}
