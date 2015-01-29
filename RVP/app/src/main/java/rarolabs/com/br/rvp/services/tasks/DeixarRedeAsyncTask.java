package rarolabs.com.br.rvp.services.tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import java.io.IOException;

import br.com.rarolabs.rvp.api.rvpAPI.model.Endereco;
import br.com.rarolabs.rvp.api.rvpAPI.model.Usuario;
import rarolabs.com.br.rvp.activities.CadastroActivity;
import rarolabs.com.br.rvp.activities.RedeActivity;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.services.BackendExpection;
import rarolabs.com.br.rvp.services.BackendServices;

/**
 * Created by rodrigosol on 1/19/15.
 */
public class DeixarRedeAsyncTask extends AsyncTask<Long, Void, Void> {
    private static BackendServices backendServices = null;
    private final RedeActivity activity;
    private Context context;
    private SharedPreferences settings;

    public DeixarRedeAsyncTask(Context context) {
        this.context = context;
        this.activity = (RedeActivity) context;
    }

    @Override
    protected Void doInBackground(Long... params) {

        Long membroId = params[0];

        settings = context.getSharedPreferences("RVP", 0);

        if(backendServices == null) { // Only do this once
            GoogleAccountCredential credential = GoogleAccountCredential.usingAudience(context, Constants.OAUTH_CLIENT_ID);
            backendServices= new BackendServices(context,settings.getString(Constants.ACCOUNT,null),Constants.BACKEND_URL);
        }
        //Verifica se o dispositivo existe
        //Tenta criar o usuario
        //Torna-se membro da rede
        try {

            Log.d("membro","Deixar a rede");
            backendServices.deixarRede(membroId);
            Log.d("membro", "Solicitado");


        } catch (final BackendExpection e) {
            Log.d("REDE", "Deu erro");
            Log.e("BuscaRedes", e.getMessage());
            Log.e("BuscaRedes", e.getDescricao());
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    activity.error(e.getDescricao());
                }
            });
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                activity.ok();
            }
        });
    }

}
