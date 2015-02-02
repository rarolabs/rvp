package br.com.rarolabs.rvp.api.endpoints;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import br.com.rarolabs.rvp.api.auth.Constants;
import br.com.rarolabs.rvp.api.models.Dispositivo;
import br.com.rarolabs.rvp.api.models.Mensagem;
import br.com.rarolabs.rvp.api.service.OfyService;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.Objectify;

import java.io.IOException;
import java.util.List;

import javax.inject.Named;

/**
 * Created by rodrigosol on 12/30/14.
 */

@Api(name = "rvpAPI", version = "v1",
        namespace = @ApiNamespace(ownerDomain = "api.rvp.rarolabs.com.br",
                ownerName = "api.rvp.rarolabs.com.br", packagePath = ""),
        scopes = {Constants.PROFILE_SCOPE},
        clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID},
        audiences = {Constants.ANDROID_AUDIENCE}
)
    public class UtilsAPI {
    /**
     * Metodo utilizado somente no contexto de testes para limpar a base de desenvolvimento
     * a cada caso de teste
     */
    @ApiMethod(name = "cleanDataBaseForTesting")
    public void cleanDataBaseForTesting() {
        OfyService.removeAll();
    }

    @ApiMethod(name = "enviarMensagem")
    public void enviarMensagem(@com.google.api.server.spi.config.Named("mensagem") String mensagen){
        Sender sender = new Sender(Constants.GCM_API_KEY);
        Message msg =  new Message.Builder()
                                  .addData("tipo","SOLICITACAO")
                                  .addData("usuario_id","rodrigosol@gmail.com")
                                  .addData("membro_id","0")
                                  .addData("rede_id","0")
                                  .addData("nome_rede","Rede de Teste")
                                  .addData("nome_usuario","Rodrigo Sol")
                                  .build();

        List<Dispositivo> dipositivos = OfyService.ofy().load().type(Dispositivo.class).list();
        for(Dispositivo dispositivo: dipositivos) {
            try {
                System.out.println("Enviando mensagem para:" + dispositivo.getDispositivoId());
                sender.sendNoRetry(msg,dispositivo.getDispositivoId());
            } catch (IOException e) {
                System.out.println("Não foi possivel enviar uma mensagem:" + e.getMessage());
            }
        }

    }


}
