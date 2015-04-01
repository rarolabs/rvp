package rarolabs.com.br.rvp.services;

import android.accounts.Account;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketException;

import br.com.rarolabs.rvp.api.rvpAPI.RvpAPI;
import br.com.rarolabs.rvp.api.rvpAPI.RvpAPIRequest;
import br.com.rarolabs.rvp.api.rvpAPI.RvpAPIRequestInitializer;

import br.com.rarolabs.rvp.api.rvpAPI.model.Alerta;
import br.com.rarolabs.rvp.api.rvpAPI.model.Endereco;
import br.com.rarolabs.rvp.api.rvpAPI.model.Membro;
import br.com.rarolabs.rvp.api.rvpAPI.model.Profile;
import br.com.rarolabs.rvp.api.rvpAPI.model.Rede;
import br.com.rarolabs.rvp.api.rvpAPI.model.RedeDetalhadaCollection;
import br.com.rarolabs.rvp.api.rvpAPI.model.Usuario;
import br.com.rarolabs.rvp.api.rvpAPI.model.Mensagem;
import rarolabs.com.br.rvp.config.Constants;


import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * Created by rodrigosol on 12/31/14.
 */
public class BackendServices {
    private Context context;
    private GoogleAccountCredential credential;
    private RvpAPI service;
    private String url;

    public BackendServices(Context context, GoogleAccountCredential credential){
        this.context = context;
        setCredential(credential);
    }


    public BackendServices(Context context, String email, String url) {
        this.context = context;
        this.url = url;
        GoogleAccountCredential credential = null;
        if (email != null){
            credential = GoogleAccountCredential.usingAudience(context, Constants.OAUTH_CLIENT_ID);
            credential.setSelectedAccountName(email);
        }
        setCredential(credential);
    }


    public void setCredential(GoogleAccountCredential credential){
        this.credential = credential;
        loadService();
    }

    public  Membro ativarVizinho(Long idMembro) throws BackendExpection {
        try {
            return service.ativarVizinho(idMembro).execute();
        } catch (IOException e) {
            throw new BackendExpection(e);
        }
    }

    public  br.com.rarolabs.rvp.api.rvpAPI.model.Rede buscarRede(Long idRede) throws BackendExpection {
        try {
            return service.buscarRede(idRede).execute();
        } catch (IOException e) {
            throw new BackendExpection(e);
        }
    }

    public  br.com.rarolabs.rvp.api.rvpAPI.model.GeoqueryResponderCollection buscarRedesProximas(Double latitude, Double longitude, Double distancia) throws BackendExpection {
        try {
            return service.buscarRedesProximas(latitude,longitude,distancia).execute();
        } catch (IOException e) {
            throw new BackendExpection(e);
        }
    }

    public  Usuario buscarUsuario(String idUsuario) throws BackendExpection {
        try {
            return service.buscarUsuario(idUsuario).execute();
        } catch (IOException e) {
            throw new BackendExpection(e);
        }
    }

    public  Membro inativarVizinho(Long idMembro) throws BackendExpection {
        try {
            return service.inativarVizinho(idMembro).execute();
        } catch (IOException e) {
            throw new BackendExpection(e);
        }
    }

    public  void deixarRede(Long idMembro) throws BackendExpection {
        try {
            service.deixarRede(idMembro).execute();
        } catch (IOException e) {
            throw new BackendExpection(e);
        }
    }

    public  RedeDetalhadaCollection minhasRedes() throws BackendExpection {
        try {
            return service.minhasRedes().execute();
        } catch (IOException e) {
            throw new BackendExpection(e);
        }
    }

    public  Rede novaRede(String nome, Endereco endereco,String visCel, String visTel, String visEnd) throws BackendExpection {
        try {
            return service.novaRede(nome, visCel, visTel,  visEnd, endereco).execute();
        } catch (IOException e) {
            throw new BackendExpection(e);
        }
    }

    public  Usuario novoUsuario(Usuario usuario) throws BackendExpection {
        try {
            return service.novoUsuario(usuario).execute();
        } catch (IOException e) {
            e.printStackTrace();
            throw new BackendExpection(e);
        }
    }

    public  void removerUsuario() throws BackendExpection {
        try {
            service.removerUsuario().execute();
        } catch (IOException e) {
            throw new BackendExpection(e);
        }
    }

    public  Membro aprovarAssociacao(Long idMembro,Boolean admin, Boolean autoridade) throws BackendExpection {
        try {
            return service.aprovarAssociacao(idMembro,admin,autoridade).execute();
        } catch (IOException e) {
            throw new BackendExpection(e);
        }
    }

    public  Membro retirarPermissaoAdministrador(Long idMembro) throws BackendExpection {
        try {
            return service.retirarPermissaoAdministrador(idMembro).execute();
        } catch (IOException e) {
            throw new BackendExpection(e);
        }
    }


    public  Membro retirarPermissaoAutoridade(Long idMembro) throws BackendExpection {
        try {
            return service.retirarPermissaoAutoridade(idMembro).execute();
        } catch (IOException e) {
            throw new BackendExpection(e);
        }
    }


    public  br.com.rarolabs.rvp.api.rvpAPI.model.MembroCollection solicitacoesPendentes(Long idRede) throws BackendExpection {
        try {
            return service.solicitacoesPendentes(idRede).execute();
        } catch (IOException e) {
            throw new BackendExpection(e);
        }
    }

    public  Membro reprovarAssociacao(Long idMembro) throws BackendExpection {
        try {
            return service.reprovarAssociacao(idMembro).execute();
        } catch (IOException e) {
            throw new BackendExpection(e);
        }
    }

    public  Membro solicitarAssociacao(Long redeId, Endereco endereco, String visCel, String visTel, String visEnd) throws BackendExpection {
        try {
            return service.solicitarAssociacao(redeId,visCel,visTel,visEnd,endereco).execute();
        } catch (IOException e) {
            throw new BackendExpection(e);
        }
    }


    public  Membro tornarAdministrador(Long idMembro) throws BackendExpection {
        try {
            return service.tornarAdministrador(idMembro).execute();
        } catch (IOException e) {
            throw new BackendExpection(e);
        }
    }

    public  Membro tornarAutoridade(Long idMembro) throws BackendExpection {
        try {
            return service.tornarAutoridade(idMembro).execute();
        } catch (IOException e) {
            throw new BackendExpection(e);
        }
    }


    public  br.com.rarolabs.rvp.api.rvpAPI.model.Membro buscarDono(Long idRede) throws BackendExpection {
        try {
            return service.buscarDono(idRede).execute();
        } catch (IOException e) {
            throw new BackendExpection(e);
        }
    }

    public  br.com.rarolabs.rvp.api.rvpAPI.model.MembroCollection buscarMembros(Long idRede) throws BackendExpection {
        try {
            return service.buscarMembros(idRede).execute();
        } catch (IOException e) {
            throw new BackendExpection(e);
        }
    }

    public Profile buscarMembro(Long membroId) throws BackendExpection {
        try {
            return service.buscarMembro(membroId).execute();
        } catch (IOException e) {
            throw new BackendExpection(e);
        }
    }

    public  br.com.rarolabs.rvp.api.rvpAPI.model.MembroCollection buscarMembrosAtivos(Long idRede) throws BackendExpection {
        try {
            return service.buscarMembrosAtivos(idRede).execute();
        } catch (IOException e) {
            throw new BackendExpection(e);
        }
    }

    public  void registrarDispositivo(String idDispositivo,String so,String versao) throws BackendExpection {
        try {
            service.registrarDispositivo(idDispositivo,so,versao).execute();
        } catch (IOException e) {
            throw new BackendExpection(e);
        }
    }

    public  void desregistrarDispositivo(String idDispositivo) throws BackendExpection {
        try {
            service.desregistrarDispositivo(idDispositivo).execute();
        } catch (IOException e) {
            throw new BackendExpection(e);
        }
    }

    public  void enviarAlerta(Alerta alerta) throws BackendExpection {
        try {
            service.enviarAlerta(alerta).execute();
        } catch (IOException e) {
            throw new BackendExpection(e);
        }
    }
    public  void enviarMensagen(Mensagem mensagem) throws BackendExpection {
        try {
            service.enviarMensagem(mensagem).execute();
        } catch (IOException e) {
            throw new BackendExpection(e);
        }
    }

    public String obterURLparaUpload() throws BackendExpection {
        try {
            return service.obterURLparaUpload().execute().getValue();
        } catch (IOException e) {
            throw new BackendExpection(e);
        }
    }


    private  void loadService(){
        RvpAPI.Builder builder = new RvpAPI.Builder(
                AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(), credential);
        if(this.url!=null) {
            builder.setRootUrl(url);
        }
        builder.setApplicationName("rvpAPI");
        builder.setGoogleClientRequestInitializer(new RvpAPIRequestInitializer() {
            protected void initializeRvpAPIRequest(RvpAPIRequest<?> request) {
                request.setDisableGZipContent(true);
            }
        });
        service = builder.build();

    }

    public  void cleanForTesting() {
        try {
            service.cleanDataBaseForTesting().execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isNetworkAvailable() {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }
}
