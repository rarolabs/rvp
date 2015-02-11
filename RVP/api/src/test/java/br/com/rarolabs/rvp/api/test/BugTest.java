package br.com.rarolabs.rvp.api.test;

import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.ForbiddenException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalSearchServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.Objectify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.rarolabs.rvp.api.endpoints.MembrosAPI;
import br.com.rarolabs.rvp.api.endpoints.RedesAPI;
import br.com.rarolabs.rvp.api.models.Endereco;
import br.com.rarolabs.rvp.api.models.Membro;
import br.com.rarolabs.rvp.api.models.Rede;
import br.com.rarolabs.rvp.api.models.Usuario;
import br.com.rarolabs.rvp.api.responders.Profile;
import br.com.rarolabs.rvp.api.service.NotificacaoService;
import br.com.rarolabs.rvp.api.service.OfyService;
import static org.junit.Assert.*;
/**
 * Created by rodrigosol on 2/10/15.
 */
public class BugTest {


    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig(), new LocalSearchServiceTestConfig());

    @Before
    public void setUp() {
        helper.setUp();
        OfyService.ofy().clear();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void buscaMembro() throws ConflictException, NotFoundException, ForbiddenException {
        Endereco e = new Endereco();
        e.setLongitude(1.0);
        e.setLatitude(1.0);

        Objectify ofy = OfyService.ofy();
        Usuario a = new Usuario();
        a.setNome("Rodrigo");
        a.setId("rodrigosol@gmail.com");

        ofy.save().entity(a).now();
        assertNotNull(a.getId());

        Usuario b = new Usuario();
        b.setNome("Joao");
        b.setId("joa@gmail.com");

        ofy.save().entity(b).now();
        assertNotNull(b.getId());
        Rede rede = Rede.novaRede("Teste", "rodrigosol@gmail.com", e, Membro.Visibilidade.PRIVADO, Membro.Visibilidade.PRIVADO, Membro.Visibilidade.PRIVADO);

        Membro m = Rede.solicitarAssociacao(rede.getId(),b.getEmail(),e, Membro.Visibilidade.PRIVADO, Membro.Visibilidade.PRIVADO, Membro.Visibilidade.PRIVADO);
        Membro.aprovarAssociacao(m.getId(),false,false);

        Membro.buscarMembro(m.getId(),"rodrigosol@gmail.com");





    }
}

