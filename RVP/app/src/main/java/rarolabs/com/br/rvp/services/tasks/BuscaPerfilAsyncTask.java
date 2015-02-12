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
import br.com.rarolabs.rvp.api.rvpAPI.model.Profile;
import br.com.rarolabs.rvp.api.rvpAPI.model.Usuario;
import rarolabs.com.br.rvp.activities.PerfilActivity;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.fragments.BuscaRedeFragment;
import rarolabs.com.br.rvp.services.BackendExpection;
import rarolabs.com.br.rvp.services.BackendServices;

public class BuscaPerfilAsyncTask extends AsyncTask<Long, Void, Profile> {
    private static BackendServices backendServices = null;
    private final Activity activity;
    private Context context;
    private SharedPreferences settings;

    public BuscaPerfilAsyncTask(Activity activity) {
        this.activity = activity;
        this.context = activity;
    }

    @Override
    protected Profile doInBackground(Long... params) {
        settings = context.getSharedPreferences("RVP", 0);



        if(backendServices == null) { // Only do this once
            GoogleAccountCredential credential = GoogleAccountCredential.usingAudience(context, Constants.OAUTH_CLIENT_ID);
            backendServices= new BackendServices(context,settings.getString(Constants.ACCOUNT,null),Constants.BACKEND_URL);
        }

        try {
            return backendServices.buscarMembro(params[0]);
        } catch (final BackendExpection e) {
            Log.d("REDE", "Deu erro");
            Log.e("BuscaRedes", e.getMessage());
            Log.e("BuscaRedes", e.getDescricao());
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    ((PerfilActivity)activity).error(e.getDescricao(),true);
                }
            });
            return null;

        }
    }

    @Override
    protected void onPostExecute(final Profile result) {
        if(result!=null) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    ((PerfilActivity) activity).ok(result);
                }
            });
        }
    }

}
