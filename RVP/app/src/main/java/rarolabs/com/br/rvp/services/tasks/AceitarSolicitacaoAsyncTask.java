package rarolabs.com.br.rvp.services.tasks;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.app.Fragment;
import android.util.Log;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import br.com.rarolabs.rvp.api.rvpAPI.model.Endereco;
import br.com.rarolabs.rvp.api.rvpAPI.model.MembroCollection;
import br.com.rarolabs.rvp.api.rvpAPI.model.Usuario;
import rarolabs.com.br.rvp.activities.CadastroActivity;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.fragments.NotificacaoDialogFragment;
import rarolabs.com.br.rvp.services.BackendExpection;
import rarolabs.com.br.rvp.services.BackendServices;

/**
 * Created by rodrigosol on 1/19/15.
 */
public class AceitarSolicitacaoAsyncTask extends AsyncTask<Object, Void, Void> {
    private static BackendServices backendServices = null;
    private final Activity activity;
    private final Fragment fragment;
    private Context context;
    private SharedPreferences settings;

    public AceitarSolicitacaoAsyncTask(Fragment fragment) {
        this.fragment = fragment;
        this.context = fragment.getActivity();
        this.activity = (Activity) this.context;
    }

    @Override
    protected Void doInBackground(Object... params) {

        Long membroId = (Long) params[0];
        Boolean admin = (Boolean) params[1];
        Boolean autoridade = (Boolean) params[2];

        settings = context.getSharedPreferences("RVP", 0);

        if(backendServices == null) { // Only do this once
            GoogleAccountCredential credential = GoogleAccountCredential.usingAudience(context, Constants.OAUTH_CLIENT_ID);
            backendServices= new BackendServices(context,settings.getString(Constants.ACCOUNT,null),Constants.BACKEND_URL);
        }
        //Verifica se o dispositivo existe
        //Tenta criar o usuario
        //Torna-se membro da rede
        try {
            Log.d("membro","Aprovando associacao");
            backendServices.aprovarAssociacao(membroId, admin, autoridade);
            Log.d("membro", "Aprovado");

        } catch (final BackendExpection e) {
            Log.d("REDE", "Deu erro");
            Log.e("BuscaRedes", e.getMessage());
            Log.e("BuscaRedes", e.getDescricao());
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    ((NotificacaoDialogFragment)fragment).error(e.getDescricao());
                }
            });
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                ((NotificacaoDialogFragment)fragment).ok();
            }
        });
    }

}
