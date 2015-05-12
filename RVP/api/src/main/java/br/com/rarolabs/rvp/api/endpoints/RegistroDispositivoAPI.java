package br.com.rarolabs.rvp.api.endpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;

import br.com.rarolabs.rvp.api.auth.Constants;
import br.com.rarolabs.rvp.api.models.Dispositivo;

@Api(name = "rvpAPI", version = "v1",
        namespace = @ApiNamespace(ownerDomain = "api.rvp.rarolabs.com.br",
                ownerName = "api.rvp.rarolabs.com.br", packagePath = ""),
        scopes = {Constants.PROFILE_SCOPE},
        clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, Constants.PRODUCTION_ANDROID_CLIENT_ID,com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID},
        audiences = {Constants.ANDROID_AUDIENCE}
)
public class RegistroDispositivoAPI {

    private static final Logger log = Logger.getLogger(RegistroDispositivoAPI.class.getName());

    /**
     * Register a device to the backend
     *
     * @param dispositivoId The Google Cloud Messaging registration Id to add
     */
    @ApiMethod(name = "registrarDispositivo")
    public void registrarDispositivo(@Named("id_dispositivo") String dispositivoId,
                                     @Named("so") String sistemaOperacional,
                                     @Named("versao") String versao,
                                     User user) throws OAuthRequestException {
        if(user==null){
            throw new OAuthRequestException("Usuário não autenticado");
        }
        Dispositivo.registrar(dispositivoId, sistemaOperacional,versao,user.getEmail());
    }

    /**
     * Unregister a device from the backend
     *
     * @param dispositivoId The Google Cloud Messaging registration Id to remove
     */
    @ApiMethod(name = "desregistrarDispositivo")
    public void desregistrarDispositivo(@Named("id_dispositivo") String dispositivoId
            , User user) throws OAuthRequestException {
        if(user==null){
            throw new OAuthRequestException("Usuário não autenticado");
        }
        Dispositivo.desregistrar(dispositivoId,user.getEmail());
    }

//    /**
//     * Return a collection of registered devices
//     *
//     * @param count The number of devices to list
//     * @return a list of Google Cloud Messaging registration Ids
//     */
//    @ApiMethod(name = "listDevices")
//    public CollectionResponse<RegistrationRecord> listDevices(@Named("count") int count) {
//        List<RegistrationRecord> records = ofy().load().type(RegistrationRecord.class).limit(count).list();
//        return CollectionResponse.<RegistrationRecord>builder().setItems(records).build();
//    }


}