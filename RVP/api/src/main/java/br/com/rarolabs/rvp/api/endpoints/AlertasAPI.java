/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package br.com.rarolabs.rvp.api.endpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.ForbiddenException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.users.User;

import javax.inject.Named;

import br.com.rarolabs.rvp.api.auth.Constants;
import br.com.rarolabs.rvp.api.models.Alerta;
import br.com.rarolabs.rvp.api.models.Endereco;
import br.com.rarolabs.rvp.api.models.Membro;
import br.com.rarolabs.rvp.api.models.Rede;
import br.com.rarolabs.rvp.api.responders.Profile;
import br.com.rarolabs.rvp.api.responders.StringResponse;
import br.com.rarolabs.rvp.api.service.NotificacaoService;
import br.com.rarolabs.rvp.api.service.OfyService;

/**
 * API - Rede de Vizinho Protegidos
 * Endpoints para gestão dos membros
 */
@Api(name = "rvpAPI", version = "v1",
     namespace = @ApiNamespace(ownerDomain = "api.rvp.rarolabs.com.br",
                               ownerName = "api.rvp.rarolabs.com.br", packagePath = ""),
        scopes = {Constants.PROFILE_SCOPE},
        clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID,com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID},
        audiences = {Constants.ANDROID_AUDIENCE}
)

public class AlertasAPI {

    /**
     * Solicita a associacao de um usuário a uma rede. A solicitacao
     * ficará pendente até quem um administrador aprove ou rejeite
     * @param alerta Endereco do usuário para essa rede
     * @throws com.google.api.server.spi.response.NotFoundException Pode ser retornada caso a rede e/ou usuário não existam
     * @throws com.google.api.server.spi.response.ConflictException Pode ser retornada caso já exista um pedido de associacao nessa
     * rede para o mesmo usuario
     */
    @ApiMethod(name = "enviarAlerta")
    public void enviar(Alerta alerta,
                       User user) throws OAuthRequestException, NotFoundException, ConflictException {
        if(user==null){
            throw new OAuthRequestException("Usuário não autenticado");
        }
        OfyService.ofy().save().entity(alerta).now();
        Queue q = QueueFactory.getDefaultQueue();
        q.add(TaskOptions.Builder.withUrl("/push_message").param("key", alerta.getId().toString()));
    }

}
