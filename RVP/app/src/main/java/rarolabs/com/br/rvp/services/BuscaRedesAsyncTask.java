package rarolabs.com.br.rvp.services;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import br.com.rarolabs.rvp.api.rvpAPI.model.GeoqueryResponder;
import br.com.rarolabs.rvp.api.rvpAPI.model.Rede;
import rarolabs.com.br.rvp.activities.WelcomeActivity;
import rarolabs.com.br.rvp.config.Constants;

public class BuscaRedesAsyncTask extends AsyncTask<Void, Void, List<GeoqueryResponder>> {
    private static BackendServices backendServices = null;
    private final WelcomeActivity activity;
    private Context context;

    public BuscaRedesAsyncTask(Context context) {
        this.context = context;
        this.activity = (WelcomeActivity) context;
    }

    @Override
    protected List<GeoqueryResponder> doInBackground(Void... params) {
        if(backendServices == null) { // Only do this once
            GoogleAccountCredential credential = GoogleAccountCredential.usingAudience(context, Constants.OAUTH_CLIENT_ID);
            backendServices= new BackendServices(context,null,Constants.BACKEND_URL);
        }

        try {
            Log.d("REDES", "Realizando chamada ao servico");
            List<GeoqueryResponder> result = backendServices.buscarRedesProximas(1.0, 1.0, 100.0).getItems();
            Log.d("REDES", "Realizando chamada ao servico");
            if (result != null){
                Log.d("REDE", "Quantidade retornada:" + result.size());
            }else{
                Log.d("REDE", "Retornou null");
            }
            return result;
        } catch (final BackendExpection e) {
            Log.d("REDE", "Deu erro");
            Log.e("BuscaRedes", e.getMessage());
            Log.e("BuscaRedes", e.getDescricao());
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    activity.error(e.getDescricao());
                }
            });
            return Collections.EMPTY_LIST;

        }
    }

    @Override
    protected void onPostExecute(final List<GeoqueryResponder> result) {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                activity.ok(result);
            }
        });
    }

}
