package rarolabs.com.br.rvp.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import java.io.IOException;

import br.com.rarolabs.rvp.api.rvpAPI.model.Endereco;
import br.com.rarolabs.rvp.api.rvpAPI.model.Membro;
import br.com.rarolabs.rvp.api.rvpAPI.model.MembroCollection;
import br.com.rarolabs.rvp.api.rvpAPI.model.Usuario;
import rarolabs.com.br.rvp.activities.CadastroActivity;

import rarolabs.com.br.rvp.activities.RedeActivity;
import rarolabs.com.br.rvp.config.Constants;

/**
 * Created by rodrigosol on 1/19/15.
 */
public class TornarMembroAsyncTask extends AsyncTask<Object, Void, Void> {
    private static BackendServices backendServices = null;
    private final CadastroActivity activity;
    private Context context;
    private SharedPreferences settings;

    public TornarMembroAsyncTask(Context context) {
        this.context = context;
        this.activity = (CadastroActivity) context;
    }

    @Override
    protected Void doInBackground(Object... params) {

        Usuario usuario = (Usuario) params[0];
        Endereco endereco = (Endereco) params[1];
        Long redeId = (Long) params[2];

        settings = context.getSharedPreferences("RVP", 0);

        if(backendServices == null) { // Only do this once
            GoogleAccountCredential credential = GoogleAccountCredential.usingAudience(context, Constants.OAUTH_CLIENT_ID);
            backendServices= new BackendServices(context,settings.getString(RedeActivity.PREF_ACCOUNT_NAME,null),Constants.BACKEND_URL);
        }
        //Verifica se o dispositivo existe
        //Tenta criar o usuario
        //Torna-se membro da rede
        try {

            if(settings.getString("USUARIO_ADICIONADO", null) == null){
                Log.d("Membro", "Adicionando usuario");
                backendServices.novoUsuario(usuario);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("USUARIO_ADICIONADO", usuario.getEmail());
                editor.commit();
                Log.d("Membro", "Usuario adicionado");

            }

            if(!settings.getBoolean("DISPOSITIVO_ADICIONADO",false)){
                Log.d("Membro", "Adicionando dispositivo");
                GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
                String regId = gcm.register(Constants.PROJECT_NUMBER);
                backendServices.registrarDispositivo(regId,"Android", String.valueOf(Build.VERSION.SDK_INT));
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("DISPOSITIVO_ADICIONADO", true);
                editor.commit();
                Log.d("Membro", "Dispositivo adicionado");
            }

            Log.d("memebro","Solicitando associacao");
            backendServices.solicitarAssociacao(redeId,endereco);
            Log.d("memebro","Solicitado");

            MembroCollection pendentes = backendServices.solicitacoesPendentes(redeId);
            Log.d("Membro", "Quantidade:" + pendentes.getItems().size());

        } catch (final BackendExpection e) {
            Log.d("REDE", "Deu erro");
            Log.e("BuscaRedes", e.getMessage());
            Log.e("BuscaRedes", e.getDescricao());
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    activity.error(e.getDescricao());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                activity.ok();
            }
        });
    }

}
