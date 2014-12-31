package br.com.rarolabs.rvp.api.endpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.googlecode.objectify.Objectify;

import br.com.rarolabs.rvp.api.service.OfyService;

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
        OfyService.removeAll();
    }
}
