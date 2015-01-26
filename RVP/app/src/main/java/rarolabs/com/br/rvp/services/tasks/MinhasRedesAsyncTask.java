package rarolabs.com.br.rvp.services.tasks;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import java.util.Collections;
import java.util.List;

import br.com.rarolabs.rvp.api.rvpAPI.model.GeoqueryResponder;
import br.com.rarolabs.rvp.api.rvpAPI.model.Membro;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.fragments.BuscaRedeFragment;
import rarolabs.com.br.rvp.fragments.MinhasRedesFragment;
import rarolabs.com.br.rvp.fragments.RedesFragment;
import rarolabs.com.br.rvp.services.BackendExpection;
import rarolabs.com.br.rvp.services.BackendServices;

public class MinhasRedesAsyncTask extends AsyncTask<Location, Void, List<Membro>> {
    private static BackendServices backendServices = null;
    private final Activity activity;
    private final Fragment fragment;
    private Context context;
    private SharedPreferences settings;

    public MinhasRedesAsyncTask(Fragment fragment) {
        this.fragment = fragment;
        this.context = fragment.getActivity();
        this.activity = (Activity) context;
    }

    @Override
    protected List<Membro> doInBackground(Location... params) {
        settings = context.getSharedPreferences("RVP", 0);

        if(backendServices == null) { // Only do this once
            GoogleAccountCredential credential = GoogleAccountCredential.usingAudience(context, Constants.OAUTH_CLIENT_ID);
            backendServices= new BackendServices(context,settings.getString(Constants.ACCOUNT,null),Constants.BACKEND_URL);
        }

        try {
            List<Membro> result = backendServices.minhasRedes().getItems();
            return result;
        } catch (final BackendExpection e) {
            Log.d("REDE", "Deu erro");
            Log.e("BuscaRedes", e.getMessage());
            Log.e("BuscaRedes", e.getDescricao());
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    ((MinhasRedesFragment)fragment).error(e.getDescricao());
                }
            });
            return Collections.EMPTY_LIST;

        }
    }

    @Override
    protected void onPostExecute(final List<Membro> result) {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                ((MinhasRedesFragment)fragment).ok(result);
            }
        });
    }

}
