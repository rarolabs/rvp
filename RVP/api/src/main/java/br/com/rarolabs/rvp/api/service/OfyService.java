package br.com.rarolabs.rvp.api.service;


import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

import br.com.rarolabs.rvp.api.models.Alerta;
import br.com.rarolabs.rvp.api.models.Endereco;
import br.com.rarolabs.rvp.api.models.Membro;
import br.com.rarolabs.rvp.api.models.Mensagem;
import br.com.rarolabs.rvp.api.models.Rede;
import br.com.rarolabs.rvp.api.models.Usuario;


/**
 * Objectify service wrapper so we can statically register our persistence classes
 * More on Objectify here : https://code.google.com/p/objectify-appengine/
 *
 */
public class OfyService {

    static {
        ObjectifyService.register(Alerta.class);
        ObjectifyService.register(Endereco.class);
        ObjectifyService.register(Membro.class);
        ObjectifyService.register(Mensagem.class);
        ObjectifyService.register(Rede.class);
        ObjectifyService.register(Usuario.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }

    public static void removeAll() {

        ObjectifyService.ofy().delete().keys(ObjectifyService.ofy().load().type(Alerta.class).keys().list());
        ObjectifyService.ofy().delete().keys(ObjectifyService.ofy().load().type(Endereco.class).keys().list());
        ObjectifyService.ofy().delete().keys(ObjectifyService.ofy().load().type(Membro.class).keys().list());
        ObjectifyService.ofy().delete().keys(ObjectifyService.ofy().load().type(Mensagem.class).keys().list());
        ObjectifyService.ofy().delete().keys(ObjectifyService.ofy().load().type(Rede.class).keys().list());
        ObjectifyService.ofy().delete().keys(ObjectifyService.ofy().load().type(Usuario.class).keys().list());
        SearchService.cleanIndex();

    }
}