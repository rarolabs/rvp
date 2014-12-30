package br.com.rarolabs.rvp.api.test;


import com.google.api.server.spi.response.ConflictException;
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
import java.util.List;

import br.com.rarolabs.rvp.api.endpoints.UtilsAPI;
import br.com.rarolabs.rvp.api.models.Alerta;
import br.com.rarolabs.rvp.api.models.Endereco;
import br.com.rarolabs.rvp.api.models.Membro;
import br.com.rarolabs.rvp.api.models.Mensagem;
import br.com.rarolabs.rvp.api.models.Rede;
import br.com.rarolabs.rvp.api.models.Usuario;
import br.com.rarolabs.rvp.api.models.Visibilidade;
import br.com.rarolabs.rvp.api.responders.GeoqueryResponder;
import br.com.rarolabs.rvp.api.service.OfyService;
import br.com.rarolabs.rvp.api.service.SearchService;
import br.com.rarolabs.rvp.api.util.Utils;

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
    public void testUsuario() throws ConflictException {
        Usuario u = new Usuario();
        u.setNome("Rodrigo");
        u.setEmail("sol@rarolans.com.br");

        OfyService.ofy().save().entity(u).now();

        Endereco e = new Endereco();
        e.setRua("Estrela");
        e.setLatitude(40.7727419);
        e.setLongitude(-73.9348984);
        e.setRua("Rua Amazonas");
        Rede.novaRede("Rede a",u.getId(),e);

        List<GeoqueryResponder> result = SearchService.searchByPosition(40.7688418,	-73.9201355, 2000.00);

        System.out.println("Size:" + result.size());
        for(GeoqueryResponder r : result){
            System.out.println(r.getDistance());
        }

    }

    @Test
    public void testAssociacao() throws ConflictException, NotFoundException {

        //new UtilsAPI().cleanDataBaseForTesting();
        Usuario u = new Usuario();
        u.setNome("João da Silva");
        u.setEmail("joao@rarolabs.com.br");

        u = Usuario.novoUsuario(u);
        assertNotNull(u.getId());
        System.out.println(u.getId());

        Endereco e = new Endereco();
        e.setLatitude(40.7727419);
        e.setLongitude(-73.9348984);
        e.setRua("Rua Amazonas");


        Rede r = Rede.novaRede("Rede 1",u.getId(),e);

        u = new Usuario();
        u.setNome("Rodrigo Sol");
        u.setEmail("rodrigo@rarolabs.com.br");
        u = Usuario.novoUsuario(u);

        List<GeoqueryResponder> result = SearchService.searchByPosition(40.7688418,	-73.9201355, 2000.00);

        assertEquals(1,result.size());
        e = new Endereco();
        e.setLatitude(40.7727419);
        e.setLongitude(-73.9348984);
        e.setRua("Rua Amazonas");

        Long redeId = result.get(0).getIdRede();

        assertNotNull(redeId);

        Rede.solicitarAssociacao(redeId, u.getId(), e);

        Collection<Membro> pendentes = Rede.solicitacoesPendentes(redeId);
        Membro mp = pendentes.iterator().next();
        assertEquals(mp.getUsuario().getId(),u.getId());

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