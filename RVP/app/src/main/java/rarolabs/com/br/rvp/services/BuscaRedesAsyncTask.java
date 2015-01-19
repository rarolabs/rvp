package rarolabs.com.br.rvp.services;

import android.content.Context;
import android.location.Location;
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

public class BuscaRedesAsyncTask extends AsyncTask<Location, Void, List<GeoqueryResponder>> {
    private static BackendServices backendServices = null;
    private final WelcomeActivity activity;
    private Context context;

    public BuscaRedesAsyncTask(Context context) {
        this.context = context;
        this.activity = (WelcomeActivity) context;
    }

    @Override
    protected List<GeoqueryResponder> doInBackground(Location... params) {
        if(backendServices == null) { // Only do this once
            backendServices= new BackendServices(context,null,Constants.BACKEND_URL);
        }

        try {
            Log.d("REDES", "Realizando chamada ao servico");
            List<GeoqueryResponder> result = backendServices.buscarRedesProximas(params[0].getLatitude(), params[0].getLongitude(), 20000.0).getItems();
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
