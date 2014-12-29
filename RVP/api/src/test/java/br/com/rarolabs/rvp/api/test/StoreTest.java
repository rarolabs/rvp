package br.com.rarolabs.rvp.api.test;


import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SearchServiceFactory;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.apphosting.api.search.DocumentPb;
import com.googlecode.objectify.Objectify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import br.com.rarolabs.rvp.api.models.Alerta;
import br.com.rarolabs.rvp.api.models.Endereco;
import br.com.rarolabs.rvp.api.models.Membro;
import br.com.rarolabs.rvp.api.models.Mensagem;
import br.com.rarolabs.rvp.api.models.Rede;
import br.com.rarolabs.rvp.api.models.Usuario;
import br.com.rarolabs.rvp.api.models.Visibilidade;
import br.com.rarolabs.rvp.api.responders.GeoqueryResponder;
import br.com.rarolabs.rvp.api.service.OfyService;

import static org.junit.Assert.*;


public class StoreTest {
    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    @Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void testUsuario(){
        Objectify ofy = OfyService.ofy();
        ofy.delete().keys(ofy.load().type(Alerta.class).keys().list());
        ofy.delete().keys(ofy.load().type(Endereco.class).keys().list());
        ofy.delete().keys(ofy.load().type(Membro.class).keys().list());
        ofy.delete().keys(ofy.load().type(Mensagem.class).keys().list());
        ofy.delete().keys(ofy.load().type(Rede.class).keys().list());
        ofy.delete().keys(ofy.load().type(Usuario.class).keys().list());
        ofy.delete().keys(ofy.load().type(Visibilidade.class).keys().list());


    }

//    @Test
//    public void testSearch(){
//
//        Rede r = new Rede();
//        r.setLatitude(1.0);
//        r.setLongitude(2.0);
//        r.setNome("Teste");
//
//        Usuario u = new Usuario();
//        u.setNome("Rodrigo");
//        u.setEmail("sol@rarolans.com.br");
//
//        Endereco endereco = new Endereco();
//        endereco.setLatitude(1.0);
//        endereco.setLongitude(2.0);
//
//
//        Objectify ofy = OfyService.ofy();
//        ofy.save().entity(r).now();
//        ofy.save().entity(u).now();
//        ofy.save().entity(endereco).now();
//
//        Membro m = new Membro();
//
//        m.setRede(r);
//        m.setUsuario(u);
//        m.setEndereco(endereco);
//
//        ofy.save().entity(m).now();
//
//
//        IndexSpec indexSpec = IndexSpec.newBuilder().setName("membros").build();
//        Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);
//
//        String queryString = "distance(memberPosition, geopoint(1, 2)) < 100";
//        Results<ScoredDocument> results = index.search(queryString);
//
//        long totalMatches = results.getNumberFound();
//        int numberOfDocsReturned = results.getNumberReturned();
//
//        System.out.println("Total Matches:"+ totalMatches);
//        System.out.println("Returned:"+ numberOfDocsReturned);
//
//
//        for(ScoredDocument doc : results){
//        }
//
//    }

}