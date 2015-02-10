package rarolabs.com.br.rvp.services.tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.IOException;

import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.services.BackendExpection;
import rarolabs.com.br.rvp.services.BackendServices;

/**
 * Created by rodrigosol on 1/19/15.
 */
public class AtualizarAvatarAsyncTask extends AsyncTask<File, Void, Void> {
    private static BackendServices backendServices = null;
    //private final CadastroActivity activity;
    private Context context;
    private SharedPreferences settings;

    public AtualizarAvatarAsyncTask(Context context) {
        this.context = context;
        //this.activity = (CadastroActivity) context;
    }

    @Override
    protected Void doInBackground(File... params) {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        settings = context.getSharedPreferences("RVP", 0);

        if(backendServices == null) { // Only do this once
            GoogleAccountCredential credential = GoogleAccountCredential.usingAudience(context, Constants.OAUTH_CLIENT_ID);
            backendServices= new BackendServices(context,settings.getString(Constants.ACCOUNT,null),Constants.BACKEND_URL);
        }
        //Verifica se o dispositivo existe
        //Tenta criar o usuario
        //Torna-se membro da rede
        try {

            Log.d("membro","Solicitando Url");
            String url = backendServices.obterURLparaUpload();
            //Modo de desenvolvimento
            if(url.contains("8080")){
                url = Constants.BACKEND_URL_UPLOAD + url.split("8080")[1];
            }

            String usuarioId = settings.getString(Constants.ACCOUNT, null);

            if(backendServices.buscarUsuario(usuarioId)!=null) {

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(url);
                httppost.setHeader("rvp-usuario-id", usuarioId);

                FileBody fileBodyProfile = new FileBody(params[0]);
                FileBody fileBodyProfileBlur = new FileBody(params[1]);
                MultipartEntity reqEntity = new MultipartEntity();

                reqEntity.addPart("profile", fileBodyProfile);
                reqEntity.addPart("profile-blur", fileBodyProfileBlur);


                httppost.setEntity(reqEntity);
                HttpResponse response = httpclient.execute(httppost);
                Log.d("Status", "Code:" + response.getStatusLine().getStatusCode());
            }





        } catch (final BackendExpection e) {
            Log.d("REDE", "Deu erro");
            Log.e("BuscaRedes", e.getMessage());
            Log.e("BuscaRedes", e.getDescricao());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        Log.d("Upload", "Enviado...");
    }

}
