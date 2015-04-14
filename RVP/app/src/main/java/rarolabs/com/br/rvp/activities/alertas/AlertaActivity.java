package rarolabs.com.br.rvp.activities.alertas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;

import java.text.SimpleDateFormat;

import br.com.rarolabs.rvp.api.rvpAPI.RvpAPI;
import br.com.rarolabs.rvp.api.rvpAPI.model.Alerta;
import br.com.rarolabs.rvp.api.rvpAPI.model.Mensagem;
import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.activities.MainActivity;
import rarolabs.com.br.rvp.activities.RedeActivity;
import rarolabs.com.br.rvp.adapters.DetalhesAlertaAdapter;
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

public class AlertaActivity extends AlertaBaseActivity {

    private ObservableRecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private Notificacao notificacao;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerta);
        mRecyclerView = (ObservableRecyclerView) findViewById(R.id.lista_destalhes_alerta_recycler_view);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Log.d("Alerta", "Id:" + getIntent().getExtras().getLong(Constants.EXTRA_NOTIFICACAO_ID, 0l));
        notificacao = Notificacao.findById(Notificacao.class,getIntent().getExtras().getLong(Constants.EXTRA_NOTIFICACAO_ID,0l));
        Log.d("Alerta",notificacao.toString());
        mRecyclerView.setAdapter(new DetalhesAlertaAdapter(this, notificacao));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alerta, menu);
        return true;
    }

    public void error(String descricao) {
        progress.dismiss();
        Toast.makeText(this,R.string.nao_foi_possivel_enviar_mensagem,Toast.LENGTH_SHORT).show();
    }

    public void ok() {
        progress.dismiss();
        mRecyclerView.getAdapter().notifyDataSetChanged();

    }

    public void enviarMensagem(String texto) {
        progress = ProgressDialog.show(this, getString(R.string.aguarde),
                getString(R.string.enviando_mensagem, true));

        Mensagem mensagem =  new Mensagem();
        mensagem.setRedeId(notificacao.getRedeId());
        mensagem.setAlertaId(notificacao.getBackendId());
        mensagem.setTexto(texto);
        new EnviarMensagemAsyncTask(this).execute(mensagem);

    }

}
