package rarolabs.com.br.rvp.activities.alertas;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Vibrator;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;

import java.text.SimpleDateFormat;
import java.util.Map;


import br.com.rarolabs.rvp.api.rvpAPI.RvpAPI;
import br.com.rarolabs.rvp.api.rvpAPI.model.Alerta;

import de.hdodenhof.circleimageview.CircleImageView;
import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.activities.MainActivity;
import rarolabs.com.br.rvp.activities.PerfilActivity;
import rarolabs.com.br.rvp.activities.RedeActivity;
import rarolabs.com.br.rvp.adapters.DetalhesAlertaAdapter;
import rarolabs.com.br.rvp.adapters.notificacoes.NotificacaoBaseAdapter;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.config.RVPApp;
import rarolabs.com.br.rvp.fragments.MinhasRedesFragment;
import rarolabs.com.br.rvp.listeners.GPSTracker;
import rarolabs.com.br.rvp.listeners.RecyclerItemClickListener;
import rarolabs.com.br.rvp.models.EsquemaAlerta;
import rarolabs.com.br.rvp.models.Membro;
import rarolabs.com.br.rvp.models.Notificacao;
import rarolabs.com.br.rvp.models.Rede;
import rarolabs.com.br.rvp.services.tasks.EnviarAlertaAsyncTask;
import rarolabs.com.br.rvp.services.tasks.EnviarMensagemAsyncTask;
import rarolabs.com.br.rvp.services.tasks.MinhasRedesAsyncTask;
import rarolabs.com.br.rvp.utils.SoundUtil;

import static rarolabs.com.br.rvp.R.id.*;
import static rarolabs.com.br.rvp.R.id.notificacao;

public class AlertaActivity extends AlertaBaseActivity {

    private ObservableRecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private Notificacao notificacao;
    private ProgressDialog progress;
    protected BroadcastReceiver mReceiver;
    protected IntentFilter intentFilter;
    private int avatarSelecionada;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerta);
        mRecyclerView = (ObservableRecyclerView) findViewById(lista_destalhes_alerta_recycler_view);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        Log.d("Alerta", "Id:" + getIntent().getExtras().getLong(Constants.EXTRA_NOTIFICACAO_ID, 0l));
        notificacao = Notificacao.findById(Notificacao.class, getIntent().getExtras().getLong(Constants.EXTRA_NOTIFICACAO_ID, 0l));
        Log.d("Alerta", notificacao.toString());
        mRecyclerView.setAdapter(new DetalhesAlertaAdapter(this, notificacao));

        enableNotificacoes((RelativeLayout) findViewById(R.id.notificacao));

        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("Main", "Mensagem rececida");
                if (intent.getExtras().containsKey(Constants.EXTRA_NOTIFICACAO_ID)) {
                    long notificacaoId = intent.getExtras().getLong(Constants.EXTRA_NOTIFICACAO_ID, 0l);
                    if (notificacaoId == notificacao.getId()) {
                        mRecyclerView.getAdapter();
                        SoundUtil.playNotificationSound(AlertaActivity.this);
                    } else {
                        mostraMensagem(notificacao);
                    }
                }
            }
        };

        intentFilter = new IntentFilter("rarolabs.com.br.rvp.broadcast.MOSTRA_ALERTA");
        onNewIntent(getIntent());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alerta, menu);
        return true;
    }

    public void error(String descricao) {
        progress.dismiss();
        Toast.makeText(this, R.string.nao_foi_possivel_enviar_mensagem, Toast.LENGTH_SHORT).show();
    }

    public void ok() {
        progress.dismiss();
        Toast.makeText(this, R.string.mensagem_enviada_com_sucesso, Toast.LENGTH_SHORT).show();
        EditText msg = ((EditText) findViewById(texto_mensagem));
        msg.setText("");

        InputMethodManager imm = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(msg.getWindowToken(), 0);

        SoundUtil.playNotificationSound(this);

    }

    public void enviarMensagem(String texto) {
        progress = ProgressDialog.show(this, getString(R.string.aguarde),
                getString(R.string.enviando_mensagem, true));

        new EnviarMensagemAsyncTask(this).execute(texto, notificacao.getRedeId(), notificacao.getBackendId());

    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    public void verPerfilCriador(View v) {
        Intent i = new Intent(this, PerfilActivity.class);
        i.putExtra(Constants.EXTRA_MEMBRO_ID, notificacao.getMembroId());
        i.putExtra(Constants.EXTRA_NOTIFICACAO_ID, notificacao.getId());
        startActivity(i);

    }

    public void verPerfil(int position) {
        Intent i = new Intent(this,PerfilActivity.class);
        i.putExtra(Constants.EXTRA_MEMBRO_ID,notificacao.getComentarioPosicao(position).getMembroId());
        i.putExtra(Constants.EXTRA_NOTIFICACAO_ID,notificacao.getId());
        startActivity(i);
    }


}