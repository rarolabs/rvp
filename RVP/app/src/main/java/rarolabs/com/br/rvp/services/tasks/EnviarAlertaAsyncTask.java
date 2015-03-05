package rarolabs.com.br.rvp.services.tasks;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import br.com.rarolabs.rvp.api.rvpAPI.model.Alerta;
import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.activities.CriarAlertaActivity;
import rarolabs.com.br.rvp.activities.PerfilActivity;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.listeners.GPSTracker;
import rarolabs.com.br.rvp.services.BackendExpection;
import rarolabs.com.br.rvp.services.BackendServices;

public class EnviarAlertaAsyncTask extends AsyncTask<Alerta, Void, Boolean> {
    private static BackendServices backendServices = null;
    private CriarAlertaActivity context;
    private SharedPreferences settings;


    public EnviarAlertaAsyncTask(CriarAlertaActivity activity) {
        this.context = activity;
    }

    @Override
    protected Boolean doInBackground(Alerta... params) {
        settings = context.getSharedPreferences("RVP", 0);

        if(backendServices == null) { // Only do this once
            GoogleAccountCredential credential = GoogleAccountCredential.usingAudience(context, Constants.OAUTH_CLIENT_ID);
            backendServices= new BackendServices(context,settings.getString(Constants.ACCOUNT,null),Constants.BACKEND_URL);
        }

        Alerta alerta = params[0];

        try {
            backendServices.enviarAlerta(alerta);

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
