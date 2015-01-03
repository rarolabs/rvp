package rarolabs.com.br.rvp.services;

import android.accounts.Account;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.com.rarolabs.rvp.api.rvpAPI.RvpAPI;
import br.com.rarolabs.rvp.api.rvpAPI.RvpAPIRequest;
import br.com.rarolabs.rvp.api.rvpAPI.RvpAPIRequestInitializer;
import br.com.rarolabs.rvp.api.rvpAPI.model.Endereco;
import br.com.rarolabs.rvp.api.rvpAPI.model.Membro;
import br.com.rarolabs.rvp.api.rvpAPI.model.Rede;
import br.com.rarolabs.rvp.api.rvpAPI.model.Usuario;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * Created by rodrigosol on 12/31/14.
 */
public class BackendServices {
    private GoogleAccountCredential credential;
    private RvpAPI service;

    public BackendServices(GoogleAccountCredential credential){
        setCredential(credential);
    }

    public void setCredential(GoogleAccountCredential credential){
        this.credential = credential;
        loadService();
    }

    public  void apagarRede(Long id) throws BackendExpection {
        try {
            service.apagarRede(id).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }

    public  Membro ativarVizinho(Long idMembro) throws BackendExpection {
        try {
            return service.ativarVizinho(idMembro).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }

    public  br.com.rarolabs.rvp.api.rvpAPI.model.Rede buscarRede(Long idRede) throws BackendExpection {
        try {
            return service.buscarRede(idRede).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }

    public  br.com.rarolabs.rvp.api.rvpAPI.model.GeoqueryResponderCollection buscarRedesProximas(Double latitude, Double longitude, Double distancia) throws BackendExpection {
        try {
            return service.buscarRedesProximas(latitude,longitude,distancia).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }

    public  Usuario buscarUsuario(String idUsuario) throws BackendExpection {
        try {
            return service.buscarUsuario(idUsuario).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }

    public  Membro inativarVizinho(Long idMembro) throws BackendExpection {
        try {
            return service.inativarVizinho(idMembro).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }

    public  br.com.rarolabs.rvp.api.rvpAPI.model.MembroCollection minhasRedes() throws BackendExpection {
        try {
            return service.minhasRedes().execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }

    public  Rede novaRede(String nome, Endereco endereco) throws BackendExpection {
        try {
            return service.novaRede(nome,endereco).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }

    public  Usuario novoUsuario(Usuario usuario) throws BackendExpection {
        try {
            return service.novoUsuario(usuario).execute();
        } catch (IOException e) {
            e.printStackTrace();
            throw new BackendExpection(getExceptionMensage(e));
        }
    }

    public  void removerUsuario() throws BackendExpection {
        try {
            service.removerUsuario().execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }

    public  Membro aprovarAssociacao(Long idMembro) throws BackendExpection {
        try {
            return service.aprovarAssociacao(idMembro).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }

    public  Membro retirarPermissaoAdministrador(Long idMembro) throws BackendExpection {
        try {
            return service.retirarPermissaoAdministrador(idMembro).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }


    public  Membro retirarPermissaoAutoridade(Long idMembro) throws BackendExpection {
        try {
            return service.retirarPermissaoAutoridade(idMembro).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }


    public  br.com.rarolabs.rvp.api.rvpAPI.model.MembroCollection solicitacoesPendentes(Long idRede) throws BackendExpection {
        try {
            return service.solicitacoesPendentes(idRede).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }

    public  Membro reprovarAssociacao(Long idMembro) throws BackendExpection {
        try {
            return service.reprovarAssociacao(idMembro).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }

    public  Membro solicitarAssociacao(Long redeId, Endereco endereco) throws BackendExpection {
        try {
            return service.solicitarAssociacao(redeId,endereco).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }


    public  Membro tornarAdministrador(Long idMembro) throws BackendExpection {
        try {
            return service.tornarAdministrador(idMembro).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }

    public  Membro tornarAutoridade(Long idMembro) throws BackendExpection {
        try {
            return service.tornarAutoridade(idMembro).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }


    public  br.com.rarolabs.rvp.api.rvpAPI.model.Membro buscarDono(Long idRede) throws BackendExpection {
        try {
            return service.buscarDono(idRede).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }

    public  br.com.rarolabs.rvp.api.rvpAPI.model.MembroCollection buscarMembros(Long idRede) throws BackendExpection {
        try {
            return service.buscarMembros(idRede).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }

    public  br.com.rarolabs.rvp.api.rvpAPI.model.MembroCollection buscarMembrosAtivos(Long idRede) throws BackendExpection {
        try {
            return service.buscarMembrosAtivos(idRede).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }


    private  void loadService(){
        RvpAPI.Builder builder = new RvpAPI.Builder(
                AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(), credential);
        builder.setRootUrl("http://10.0.0.102:8080/_ah/api");
        builder.setApplicationName("rvpAPI");
        builder.setGoogleClientRequestInitializer(new RvpAPIRequestInitializer() {
            protected void initializeRvpAPIRequest(RvpAPIRequest<?> request) {
                request.setDisableGZipContent(true);
            }
        });
        service = builder.build();

    }

    private static String getExceptionMensage(Exception e){


            String msg = e.getMessage();
            e.printStackTrace();

            try {
                JSONObject jObject = new JSONObject(msg.substring(msg.indexOf("{"), msg.lastIndexOf("}") + 1));

                Log.d("PARSE", jObject.toString());
                msg = jObject.getJSONArray("errors").getJSONObject(0).getString("message");
                Log.d("PARSE",msg);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        return msg;
    }

    public  void cleanForTesting() {
        try {
            service.cleanDataBaseForTesting().execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
