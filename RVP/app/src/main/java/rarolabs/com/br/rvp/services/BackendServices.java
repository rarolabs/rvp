package rarolabs.com.br.rvp.services;

import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.json.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.com.rarolabs.rvp.api.rvpAPI.RvpAPI;
import br.com.rarolabs.rvp.api.rvpAPI.RvpAPIRequest;
import br.com.rarolabs.rvp.api.rvpAPI.RvpAPIRequestInitializer;
import br.com.rarolabs.rvp.api.rvpAPI.model.Endereco;
import br.com.rarolabs.rvp.api.rvpAPI.model.Rede;
import br.com.rarolabs.rvp.api.rvpAPI.model.Usuario;

/**
 * Created by rodrigosol on 12/31/14.
 */
public class BackendServices {


    public static void apagarRede(Long id) throws BackendExpection {
        try {
            RvpAPI service = getService();
            service.apagarRede(id).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }

    public static void ativarVizinho(Long idMembro) throws BackendExpection {
        try {
            RvpAPI service = getService();
            service.ativarVizinho(idMembro).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }

    public static br.com.rarolabs.rvp.api.rvpAPI.model.Rede buscarRede(Long idRede) throws BackendExpection {
        try {
            RvpAPI service = getService();
            return service.buscarRede(idRede).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }

    public static br.com.rarolabs.rvp.api.rvpAPI.model.GeoqueryResponderCollection buscarRedesProximas(Double latitude, Double longitude, Double distancia) throws BackendExpection {
        try {
            RvpAPI service = getService();
            return service.buscarRedesProximas(latitude,longitude,distancia).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }

    public static Usuario buscarUsuario(Long idUsuario) throws BackendExpection {
        try {
            RvpAPI service = getService();
            return service.buscarUsuario(idUsuario).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }

    public static void inativarVizinho(Long idMembro) throws BackendExpection {
        try {
            RvpAPI service = getService();
            service.inativarVizinho(idMembro).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }

    public static br.com.rarolabs.rvp.api.rvpAPI.model.MembroCollection minhasRedes(Long idUsuario) throws BackendExpection {
        try {
            RvpAPI service = getService();
            return service.minhasRedes(idUsuario).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }

    public static Rede novaRede(String nome, Long usuarioId, Endereco endereco) throws BackendExpection {
        try {
            RvpAPI service = getService();
            return service.novaRede(nome,usuarioId,endereco).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }

    public static Usuario novoUsuario(Usuario usuario) throws BackendExpection {
        try {
            RvpAPI service = getService();
            return service.novoUsuario(usuario).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }

    public static void removerUsuario(Long idUsuario) throws BackendExpection {
        try {
            RvpAPI service = getService();
            service.removerUsuario(idUsuario).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }

    public static void aprovarAssociacao(Long idMembro) throws BackendExpection {
        try {
            RvpAPI service = getService();
            service.aprovarAssociacao(idMembro).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }

    public static void retirarPermissaoAdministrador(Long idMembro) throws BackendExpection {
        try {
            RvpAPI service = getService();
            service.retirarPermissaoAdministrador(idMembro).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }


    public static void retirarPermissaoAutoridade(Long idMembro) throws BackendExpection {
        try {
            RvpAPI service = getService();
            service.retirarPermissaoAutoridade(idMembro).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }

    public static void salvarEndereco(Endereco endereco) throws BackendExpection {
        try {
            RvpAPI service = getService();
            service.salvarEndereco(endereco).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }

    public static br.com.rarolabs.rvp.api.rvpAPI.model.MembroCollection solicitacoesPendentes(Long idRede) throws BackendExpection {
        try {
            RvpAPI service = getService();
            return service.solicitacoesPendentes(idRede).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }

    public static void reprovarAssociacao(Long idMembro) throws BackendExpection {
        try {
            RvpAPI service = getService();
            service.reprovarAssociacao(idMembro).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }

    public static void solicitarAssociacao(Long redeId,Long usuarioId, Endereco endereco) throws BackendExpection {
        try {
            RvpAPI service = getService();
            service.solicitarAssociacao(redeId,usuarioId,endereco).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }


    public static void tornarAdministrador(Long idMembro) throws BackendExpection {
        try {
            RvpAPI service = getService();
            service.tornarAdministrador(idMembro).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }

    public static void tornarAutoridade(Long idMembro) throws BackendExpection {
        try {
            RvpAPI service = getService();
            service.tornarAutoridade(idMembro).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }


    public static br.com.rarolabs.rvp.api.rvpAPI.model.Membro buscarDono(Long idRede) throws BackendExpection {
        try {
            RvpAPI service = getService();
            return service.buscarDono(idRede).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }

    public static br.com.rarolabs.rvp.api.rvpAPI.model.MembroCollection buscarMembros(Long idRede) throws BackendExpection {
        try {
            RvpAPI service = getService();
            return service.buscarMembros(idRede).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }

    public static br.com.rarolabs.rvp.api.rvpAPI.model.MembroCollection buscarMembrosAtivos(Long idRede) throws BackendExpection {
        try {
            RvpAPI service = getService();
            return service.buscarMembrosAtivos(idRede).execute();
        } catch (IOException e) {
            throw new BackendExpection(getExceptionMensage(e));
        }
    }


    private static RvpAPI getService(){
        RvpAPI.Builder builder = new RvpAPI.Builder(
                AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(), null);
        builder.setRootUrl("http://10.0.0.102:8080/_ah/api");
        builder.setApplicationName("rvpAPI");
        builder.setGoogleClientRequestInitializer(new RvpAPIRequestInitializer() {
            protected void initializeRvpAPIRequest(RvpAPIRequest<?> request) {
                request.setDisableGZipContent(true);
            }
        });
        return builder.build();
    }
    private static String getExceptionMensage(Exception e){


            String msg = e.getMessage();
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

    public static void cleanForTesting() {
        RvpAPI service = getService();
        try {
            service.cleanDataBaseForTesting().execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
