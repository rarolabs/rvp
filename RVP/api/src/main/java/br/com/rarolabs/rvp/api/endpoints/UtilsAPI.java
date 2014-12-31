package br.com.rarolabs.rvp.api.endpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.googlecode.objectify.Objectify;

import br.com.rarolabs.rvp.api.models.Alerta;
import br.com.rarolabs.rvp.api.models.Endereco;
import br.com.rarolabs.rvp.api.models.Membro;
import br.com.rarolabs.rvp.api.models.Mensagem;
import br.com.rarolabs.rvp.api.models.Rede;
import br.com.rarolabs.rvp.api.models.Usuario;
import br.com.rarolabs.rvp.api.models.Visibilidade;
import br.com.rarolabs.rvp.api.service.OfyService;
import br.com.rarolabs.rvp.api.service.SearchService;

/**
 * Created by rodrigosol on 12/30/14.
 */

    @Api(name = "rvpAPI", version = "v1", namespace = @ApiNamespace(ownerDomain = "api.rvp.rarolabs.com.br", ownerName = "api.rvp.rarolabs.com.br", packagePath = ""))
    public class UtilsAPI {
    /**
     * Metodo utilizado somente no contexto de testes para limpar a base de desenvolvimento
     * a cada caso de teste
     */
    @ApiMethod(name = "cleanDataBaseForTesting")
    public void cleanDataBaseForTesting() {
        Objectify ofy = OfyService.ofy();
        ofy.delete().keys(ofy.load().type(Alerta.class).keys().list());
        ofy.delete().keys(ofy.load().type(Endereco.class).keys().list());
        ofy.delete().keys(ofy.load().type(Membro.class).keys().list());
        ofy.delete().keys(ofy.load().type(Mensagem.class).keys().list());
        ofy.delete().keys(ofy.load().type(Rede.class).keys().list());
        ofy.delete().keys(ofy.load().type(Usuario.class).keys().list());
        ofy.delete().keys(ofy.load().type(Visibilidade.class).keys().list());
        SearchService.cleanIndex();
    }
}
