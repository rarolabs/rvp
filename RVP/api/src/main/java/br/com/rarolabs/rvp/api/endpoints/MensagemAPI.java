package br.com.rarolabs.rvp.api.endpoints;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;

import br.com.rarolabs.rvp.api.auth.Constants;

/**
 * Created by rodrigosol on 1/3/15.
 */
@Api(name = "rvpAPI", version = "v1",
        namespace = @ApiNamespace(ownerDomain = "api.rvp.rarolabs.com.br",
                ownerName = "api.rvp.rarolabs.com.br", packagePath = ""),
        scopes = {Constants.PROFILE_SCOPE},
        clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID,com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID},
        audiences = {Constants.ANDROID_AUDIENCE}
)
public class MensagemAPI {
    public void enviarMensagem(@Named("mensagem") String mensagen){
        Sender sender = new Sender(Constants.GCM_API_KEY);
        Message msg =  new Message.Builder().addData("mensage",mensagen).build();
        


    }
}
