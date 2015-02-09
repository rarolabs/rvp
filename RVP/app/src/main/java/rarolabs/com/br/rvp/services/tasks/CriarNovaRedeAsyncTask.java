package rarolabs.com.br.rvp.services.tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import br.com.rarolabs.rvp.api.rvpAPI.model.Endereco;
import br.com.rarolabs.rvp.api.rvpAPI.model.Usuario;
import rarolabs.com.br.rvp.activities.CadastroActivity;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.services.BackendExpection;
import rarolabs.com.br.rvp.services.BackendServices;

/**
 * Created by rodrigosol on 1/19/15.
 */
public class CriarNovaRedeAsyncTask extends AsyncTask<Object, Void, Boolean> {
    private static BackendServices backendServices = null;
    private final CadastroActivity activity;
    private Context context;
    private SharedPreferences settings;

    public CriarNovaRedeAsyncTask(Context context) {
        this.context = context;
        this.activity = (CadastroActivity) context;
    }

    @Override
    protected Boolean doInBackground(Object... params) {

        Usuario usuario = (Usuario) params[0];
        Endereco endereco = (Endereco) params[1];
        String nomeRede = (String) params[2];
        String[] visibilidade = (String[]) params[3];

        settings = context.getSharedPreferences("RVP", 0);

        if(backendServices == null) { // Only do this once
            GoogleAccountCredential credential = GoogleAccountCredential.usingAudience(context, Constants.OAUTH_CLIENT_ID);
            backendServices= new BackendServices(context,settings.getString(Constants.ACCOUNT,null),Constants.BACKEND_URL);
        }
        //Verifica se o dispositivo existe
        //Tenta criar o usuario
        //Torna-se membro da rede
        try {
                Log.d("Membro", "Adicionando usuario");
                backendServices.novoUsuario(usuario);
                Log.d("Membro", "Usuario adicionado");

                Log.d("Membro", "Adicionando dispositivo");

                String regId = settings.getString(Constants.REG_ID,"");
                backendServices.registrarDispositivo(regId,"Android", String.valueOf(Build.VERSION.SDK_INT));


            Log.d("membro","Criar nova rede");
            backendServices.novaRede(nomeRede,endereco,visibilidade[0], visibilidade[1], visibilidade[2]);
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
            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean ok) {
        if(ok) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    activity.ok();
                }
            });
        }
    }

}
