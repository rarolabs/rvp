package rarolabs.com.br.rvp.services.tasks;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import rarolabs.com.br.rvp.activities.PerfilActivity;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.services.BackendExpection;
import rarolabs.com.br.rvp.services.BackendServices;

public class RejeitarMembroAsyncTask extends AsyncTask<Object, Void, Boolean> {
    private static BackendServices backendServices = null;
    private final Activity activity;
    private Context context;
    private SharedPreferences settings;

    public RejeitarMembroAsyncTask(Activity activity) {
        this.activity = activity;
        this.context = activity;
    }

    @Override
    protected Boolean doInBackground(Object... params) {
        settings = context.getSharedPreferences("RVP", 0);



        if(backendServices == null) { // Only do this once
            GoogleAccountCredential credential = GoogleAccountCredential.usingAudience(context, Constants.OAUTH_CLIENT_ID);
            backendServices= new BackendServices(context,settings.getString(Constants.ACCOUNT,null),Constants.BACKEND_URL);
        }

        Long membroId = (Long) params[0];
        try {
            backendServices.reprovarAssociacao(membroId);

        } catch (final BackendExpection e) {
            Log.d("REDE", "Deu erro");
            Log.e("BuscaRedes", e.getMessage());
            Log.e("BuscaRedes", e.getDescricao());
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    ((PerfilActivity)activity).error(e.getDescricao(),false);
                }
            });
            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute(final Boolean result) {
        if(result) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    ((PerfilActivity) activity).ok();
                }
            });
        }
    }

}
