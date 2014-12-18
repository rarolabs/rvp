/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package br.com.rarolabs.rvp.api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;


import javax.inject.Named;

import javax.persistence.EntityManager;


import br.com.rarolabs.rvp.api.models.Rede;
import br.com.rarolabs.rvp.api.service.OfyService;

/**
 * An endpoint class we are exposing
 */
@Api(name = "myApi", version = "v1", namespace = @ApiNamespace(ownerDomain = "api.rvp.rarolabs.com.br", ownerName = "api.rvp.rarolabs.com.br", packagePath = ""))
public class MyEndpoint {

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String name) {
        MyBean response = new MyBean();
        response.setData("Hi, " + name);

        return response;
    }

    @ApiMethod(name = "sayHi2")
    public Rede sayHi2(@Named("name") String name) {
        Objectify ofy = OfyService.ofy();

        Rede rede = new Rede();
        rede.setNome(name);
        rede.setKey("1");

        ofy.save().entity(rede).now();
        return rede;
    }

}
