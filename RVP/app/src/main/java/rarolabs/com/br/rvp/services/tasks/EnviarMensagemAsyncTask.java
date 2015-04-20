package rarolabs.com.br.rvp.services.tasks;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;


import rarolabs.com.br.rvp.activities.alertas.AlertaActivity;
import rarolabs.com.br.rvp.activities.alertas.CriarAlertaActivity;
import rarolabs.com.br.rvp.config.Constants;

import rarolabs.com.br.rvp.services.BackendExpection;
import rarolabs.com.br.rvp.services.BackendServices;

public class EnviarMensagemAsyncTask extends AsyncTask<Object, Void, Boolean> {
    private static BackendServices backendServices = null;
    private AlertaActivity context;
    private SharedPreferences settings;


    public EnviarMensagemAsyncTask(AlertaActivity activity) {
        this.context = activity;
    }

    @Override
    protected Boolean doInBackground(Object... params) {
        settings = context.getSharedPreferences("RVP", 0);

        if(backendServices == null) { // Only do this once
            GoogleAccountCredential credential = GoogleAccountCredential.usingAudience(context, Constants.OAUTH_CLIENT_ID);
            backendServices= new BackendServices(context,settings.getString(Constants.ACCOUNT,null),Constants.BACKEND_URL);
        }

        String texto =  (String) params[0];
        Long redeId =  (Long) params[1];
        Long alertaId =  (Long) params[2];

        try {
            backendServices.enviarMensagen(texto,redeId,alertaId);

        } catch (final BackendExpection e) {
            Log.d("REDE", "Deu erro");
            Log.e("BuscaRedes", e.getMessage());
            Log.e("BuscaRedes", e.getDescricao());
            context.runOnUiThread(new Runnable() {
                public void run() {
                    context.error(e.getDescricao());
                }
            });

            return Boolean.FALSE;

        }
        return Boolean.TRUE;
    }

    @Override
    protected void onPostExecute(final Boolean result) {
        if (result){
            context.runOnUiThread(new Runnable() {
                public void run() {
                    context.ok();
                }
            });
        }



    }

}
