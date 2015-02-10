package rarolabs.com.br.rvp.activities;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import de.hdodenhof.circleimageview.CircleImageView;
import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.config.RVPApp;
import rarolabs.com.br.rvp.services.tasks.DeixarRedeAsyncTask;
import rarolabs.com.br.rvp.utils.ImageUtil;

public class RedeActivity extends Activity {


    private ImageView thumb;
    private FloatingActionButton entrarNaRede;
    private SharedPreferences settings;
    private String email;
    private long redeId;
    private Bundle extras;
    private boolean membro;
    private int quantidadeMembros;
    private String papel;
    ProgressDialog progress;
    private Long membroId;
    private CircleImageView profileAdmin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("REDE","onCreate:" + (savedInstanceState == null));
        setContentView(R.layout.activity_rede);

        if(getActionBar()!=null) {
            getActionBar().hide();
        }
        Intent i = getIntent();
        extras = i.getExtras();
        if(extras == null){
            extras = ((RVPApp)getApplicationContext()).getUltimaRede();
        }

        membro = extras.getBoolean(Constants.EXTRA_MEMBRO, false);
        membroId = extras.getLong(Constants.EXTRA_MEMBRO_ID, 0l);




        ((TextView) findViewById(R.id.nome_rede)).setText(extras.getString(Constants.EXTRA_NOME_REDE));
        ((TextView) findViewById(R.id.endereco_rede)).setText(extras.getString(Constants.EXTRA_ENDERECO_REDE));
        ((TextView) findViewById(R.id.nome_administrador)).setText(extras.getString(Constants.EXTRA_NOME_ADMIN));
        ((TextView) findViewById(R.id.ultima_atividade)).setText(extras.getString(Constants.EXTRA_ULTIMA_ATIVIDADE));
        profileAdmin = (CircleImageView) findViewById(R.id.profile_image);
        ImageUtil.loadIconAssync(extras.getString(Constants.EXTRA_AVATAR),profileAdmin);

        quantidadeMembros = extras.getInt(Constants.EXTRA_QUANTIDADE_MEMBROS);

        ((TextView) findViewById(R.id.quantidade_membros)).setText(quantidadeMembros + " membros");

        redeId = extras.getLong(Constants.EXTRA_ID_REDE,0l);



        entrarNaRede  = (FloatingActionButton) findViewById(R.id.entrar_na_rede);
        entrarNaRede.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entrarOuSair();
            }
        });
        if(membro){
            entrarNaRede.setImageResource(android.R.drawable.ic_menu_delete);
        }else{
            entrarNaRede.setImageResource(R.drawable.ic_actionbar_confirmar);
        }

        thumb = (ImageView) findViewById(R.id.thumb);
        Double[] location = new Double[quantidadeMembros * 2];

        int locationIndex = 0;
        for(int membroCount = 0; membroCount < quantidadeMembros; membroCount++){
            location[locationIndex++] = extras.getDouble("latitude_" + membroCount);
            location[locationIndex++] = extras.getDouble("longitude_" + membroCount);
        }

        ImageUtil.googleMapsThumb(this,location,thumb);

    }

    private void entrarOuSair() {
        if(membro){
            sair();
        }else{
            entrar();
        }
    }

    private void sair() {
        progress = ProgressDialog.show(this, getString(R.string.aguarde),
                getString(R.string.enviando_solicitacao, true));
        new DeixarRedeAsyncTask(this).execute(membroId);
    }


    private void entrar() {
        Intent i = new Intent(RedeActivity.this,CadastroActivity.class);
        PendingIntent pendingIntent =
                TaskStackBuilder.create(this)
                        // add all of DetailsActivity's parents to the stack,
                        // followed by DetailsActivity itself
                        .addNextIntentWithParentStack(i)
                        .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentIntent(pendingIntent);
        i.putExtra(Constants.EXTRA_ID_REDE,redeId);

        ((RVPApp)getApplicationContext()).setUltimaRede(extras);
        startActivity(i);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rede, menu);
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

    public ImageView getThumb() {
        return thumb;
    }

    public void error(String descricao) {
        progress.dismiss();
        Toast.makeText(this,descricao,Toast.LENGTH_LONG).show();
    }

    public void ok() {
        progress.dismiss();
        Toast.makeText(this,R.string.nao_faz_mais_parte_da_rede,Toast.LENGTH_LONG).show();
        finish();
    }
}
