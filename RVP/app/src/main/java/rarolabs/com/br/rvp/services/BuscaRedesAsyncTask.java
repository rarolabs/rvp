package rarolabs.com.br.rvp.services;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import br.com.rarolabs.rvp.api.rvpAPI.model.GeoqueryResponder;
import br.com.rarolabs.rvp.api.rvpAPI.model.Rede;
import rarolabs.com.br.rvp.activities.WelcomeActivity;

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
            backendServices = new BackendServices(context,null);
        }

        try {
             return backendServices.buscarRedesProximas(1.0,1.0,100.0).getItems();

        } catch (BackendExpection e) {
            Log.e("BuscaRedes", e.getMessage());
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    protected void onPostExecute(List<GeoqueryResponder> result) {
        Log.d("REDES", "Quantidade:" + result.size());
        activity.runOnUiThread(new Runnable() {
            public void run() {
                activity.ok();
            }
        });
    }

}
