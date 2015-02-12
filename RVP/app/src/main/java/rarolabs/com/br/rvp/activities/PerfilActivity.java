package rarolabs.com.br.rvp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import br.com.rarolabs.rvp.api.rvpAPI.RvpAPI;
import br.com.rarolabs.rvp.api.rvpAPI.model.Membro;
import br.com.rarolabs.rvp.api.rvpAPI.model.Profile;
import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.listeners.GPSTracker;
import rarolabs.com.br.rvp.models.Notificacao;
import rarolabs.com.br.rvp.services.tasks.AceitarSolicitacaoAsyncTask;
import rarolabs.com.br.rvp.services.tasks.BuscaPerfilAsyncTask;
import rarolabs.com.br.rvp.services.tasks.RejeitarMembroAsyncTask;
import rarolabs.com.br.rvp.services.tasks.TornarAdministradorAsyncTask;
import rarolabs.com.br.rvp.services.tasks.TornarAutoridadeAsyncTask;
import rarolabs.com.br.rvp.utils.ImageUtil;

public class PerfilActivity extends RVPActivity implements CompoundButton.OnCheckedChangeListener {


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
    private View containerAcoes;
    private Button botaoRejeitar;
    private Button botaoAdicionar;
    private TextView labelTornarAdministrador;
    private TextView labelTornarAutoridade;
    private boolean listernersEnabled = false;
    private long notificacaoID;
    private Notificacao notificacao = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        getSupportActionBar().setTitle("");
        membroId = getIntent().getExtras().getLong(Constants.EXTRA_MEMBRO_ID, 0l);
        notificacaoID = getIntent().getExtras().getLong(Constants.EXTRA_NOTIFICACAO_ID, 0l);
        if(notificacaoID > 0){
            notificacao = Notificacao.findById(Notificacao.class, notificacaoID);
        }
        containerProfile = findViewById(R.id.conteiner_profile);

        loading = findViewById(R.id.loading);
        spinner = (ImageView) findViewById(R.id.spinner);
        spinner.startAnimation(
                AnimationUtils.loadAnimation(this, R.anim.rotate_forever));
        nome = (TextView) findViewById(R.id.nome);
        endereco = (TextView) findViewById(R.id.endereco);
        telefoneFixo = (TextView) findViewById(R.id.telefone_fixo);
        telefoneFixo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telefonar(((TextView)v).getText().toString());
            }
        });

        telefoneCelular = (TextView) findViewById(R.id.telefone_celular);
        telefoneCelular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telefonar(((TextView) v).getText().toString());
            }
        });

        profile = (ImageView) findViewById(R.id.profile_image);
        profileBG = findViewById(R.id.profile_image_bg);

        tornarAdministrador = (SwitchCompat) findViewById(R.id.tornar_administrador);
        tornarAdministrador.setOnCheckedChangeListener(this);

        tornarAutoridade = (SwitchCompat) findViewById(R.id.tornar_autoridade);
        tornarAutoridade.setOnCheckedChangeListener(this);

        containerAdministrador = findViewById(R.id.container_admin);
        containerAutoridade = findViewById(R.id.container_autoridade);

        labelTornarAdministrador = (TextView) findViewById(R.id.label_tornar_administrador);
        labelTornarAutoridade = (TextView) findViewById(R.id.label_tornar_autoridade);




        containerAcoes = findViewById(R.id.container_acoes);
        botaoRejeitar = (Button) findViewById(R.id.rejeitar);
        botaoRejeitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejeitar();
            }
        });

        botaoAdicionar = (Button) findViewById(R.id.adicionar);
        botaoAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tornarMembro();

            }
        });

        enableNotificacoes((RelativeLayout) findViewById(R.id.notificacao));
        new BuscaPerfilAsyncTask(this).execute(membroId);


    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView.getId() == R.id.tornar_administrador){
            labelTornarAdministrador.setTextColor(getResources().getColor(getLabelColor(isChecked)));
            if(this.listernersEnabled){
                toggleAdmin(buttonView.isChecked());
            }
        }else{
            labelTornarAutoridade.setTextColor(getResources().getColor(getLabelColor(isChecked)));
            if(this.listernersEnabled){
                toggleAutoridade(buttonView.isChecked());
            }
        }


    }

    private int getLabelColor(boolean isChecked) {
        return isChecked ? R.color.material_green_500 : R.color.fonte_busca_rede_bairro ;
    }


    private void rejeitar() {
        progress = ProgressDialog.show(this, getString(R.string.aguarde),
                getString(R.string.enviando_solicitacao, true));
        new RejeitarMembroAsyncTask(this).execute(membroId);

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
        if( id == R.id.home){
            onBackPressed();
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

            if (p.getStatus().equals("ATIVO")) {
                if (p.getPapel().equals("ADMIN")) {
                    tornarAdministrador.setChecked(true);
                    tornarAutoridade.setChecked(false);
                } else if (p.getPapel().equals("AUTORIDADE")) {
                    tornarAdministrador.setChecked(false);
                    tornarAutoridade.setChecked(true);
                }
                enableListerners();
            }else{
                containerAcoes.setVisibility(View.VISIBLE);
            }



        } else {
            containerAdministrador.setVisibility(View.GONE);
            containerAutoridade.setVisibility(View.GONE);

        }


        containerProfile.setVisibility(View.VISIBLE);


    }

    public void error(String descricao,boolean finish) {
        Toast.makeText(this, descricao, Toast.LENGTH_LONG).show();
        if(finish) {
            finish();
        }
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
        this.listernersEnabled=true;
    }

    private void disableListeners() {
        this.listernersEnabled=false;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void telefonar(String numero){
        numero = numero.replace("(","").replace(")","").replace(" ","");
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + numero));
        startActivity(callIntent);
    }


    public void ok() {
        progress.dismiss();
        Toast.makeText(this, "Sua solicitação foi enviada com sucesso!", Toast.LENGTH_SHORT).show();
        if(notificacao!=null) {
            notificacao.setRespondida(true);
            notificacao.setAbrivel(false);
            notificacao.save();
        }

        finish();
    }

    private void tornarMembro() {
        Log.d("Notificacao", "tornarMembro");
        progress = ProgressDialog.show(this, getString(R.string.aguarde),
                                             getString(R.string.enviando_solicitacao, true));

        Object[] params ={
                membroId,
                Boolean.valueOf(tornarAdministrador.isChecked()),
                Boolean.valueOf(tornarAutoridade.isChecked())
        };

        new AceitarSolicitacaoAsyncTask(this).execute(params);

    }


}
