package rarolabs.com.br.rvp.services.tasks;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import br.com.rarolabs.rvp.api.rvpAPI.model.Alerta;
import rarolabs.com.br.rvp.activities.PerfilActivity;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.services.BackendExpection;
import rarolabs.com.br.rvp.services.BackendServices;

public class EnviarAlertaAsyncTask extends AsyncTask<Alerta, Void, Void> {
    private static BackendServices backendServices = null;
    private Context context;
    private SharedPreferences settings;

    public EnviarAlertaAsyncTask(Activity activity) {
        this.context = activity;
    }

    @Override
    protected Void doInBackground(Alerta... params) {
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
        }
        return null;
    }

    @Override
    protected void onPostExecute(final Void result) {

    }

}
