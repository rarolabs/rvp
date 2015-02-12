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
import rarolabs.com.br.rvp.activities.PerfilActivity;
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
    private Fragment fragment = null;
    private Context context;
    private SharedPreferences settings;

    public AceitarSolicitacaoAsyncTask(Object host) {
        Fragment fragment1;
        fragment1 = null;
        if(host instanceof Fragment) {
            fragment1 = (Fragment) host;
            this.fragment = fragment1;
            this.context = fragment.getActivity();
            this.activity = (Activity) this.context;
        }else{
            this.activity = (Activity) host;
            this.context = this.activity;
        }


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
                    if(fragment!=null) {
                        ((NotificacaoDialogFragment) fragment).error(e.getDescricao());
                    }else{
                        ((PerfilActivity)activity).error(e.getDescricao(),false);
                    }
                }
            });
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        activity.runOnUiThread(new Runnable() {
            public void run() {
            if(fragment!=null) {
                ((NotificacaoDialogFragment) fragment).ok();
            }else{
                ((PerfilActivity)activity).ok();
            }
            }
        });
    }

}
