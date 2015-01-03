package br.com.rarolabs.rvp.api.test;

import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.ForbiddenException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalSearchServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import br.com.rarolabs.rvp.api.models.Endereco;
import br.com.rarolabs.rvp.api.models.Membro;
import br.com.rarolabs.rvp.api.models.Rede;
import br.com.rarolabs.rvp.api.models.Usuario;
import br.com.rarolabs.rvp.api.responders.GeoqueryResponder;
import br.com.rarolabs.rvp.api.service.OfyService;
import br.com.rarolabs.rvp.api.service.SearchService;
import br.com.rarolabs.rvp.api.test.fixtures.EnderecoFixture;
import br.com.rarolabs.rvp.api.test.fixtures.RedeFixture;
import br.com.rarolabs.rvp.api.test.fixtures.UsuarioFixture;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by rodrigosol on 12/31/14.
 */
public class LocalizarRedesTest {

    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig(),new LocalSearchServiceTestConfig());

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
    public void minhasRedes() throws ConflictException, NotFoundException, ForbiddenException {
        Usuario rodrigo = Usuario.novoUsuario(UsuarioFixture.getRodrigoSol());
        Rede rede1 = Rede.novaRede("Amigos do Comiteco",rodrigo.getId(), EnderecoFixture.getEnderecoRaro());
        Rede rede2 = Rede.novaRede("Amigos do Nectar",rodrigo.getId(), EnderecoFixture.getEnderecoCasa());
        Usuario ramon = Usuario.novoUsuario(UsuarioFixture.getRamonSetragni());
        Rede rede3 = Rede.novaRede("Amigos do Praça",ramon.getId(), EnderecoFixture.getEnderecoPraca());
        Rede.solicitarAssociacao(rede3.getId(),rodrigo.getId(), EnderecoFixture.getEnderecoPraca());
        Membro.aprovarAssociacao(rede3.solicitacoesPendentes().iterator().next().getId());
        Collection<Membro> minhasRedes = Usuario.minhasRedes(rodrigo.getId());

        assertEquals(3,minhasRedes.size());
        TreeSet<Long> actual = new TreeSet<Long>();
        for(Membro m : minhasRedes){
            actual.add(m.getRede().getId());
        }

        Long[] expected = {rede1.getId(),rede2.getId(),rede3.getId()};

        for(int i =0; i < expected.length;i++){
            assertEquals(expected[i],actual.toArray()[i]);
        }
    }

    @Test
    public void buscarRedesProximas() throws ConflictException, NotFoundException, ForbiddenException {

        Endereco raro = EnderecoFixture.getEnderecoRaro();
        Endereco casa = EnderecoFixture.getEnderecoCasa();
        Endereco praca = EnderecoFixture.getEnderecoPraca();
        Endereco escola = EnderecoFixture.getEnderecoEscola();

        Rede rede1 = RedeFixture.novaRede1();

        //Busca exata nas mesma cordenadas da rede
        List<GeoqueryResponder> result = SearchService.searchByPosition(raro.getLatitude(), raro.getLongitude(), 0.00);
        assertEquals(1,result.size());
        assertEquals("0.0",result.get(0).getDistance().toString());
        System.out.println("Distancia:" + result.get(0).getDistance());

        //Busca a raro da praca da bandeira se margem
        result = SearchService.searchByPosition(praca.getLatitude(), praca.getLongitude(), 0.00);
        assertEquals(0,result.size());
        //Busca a raro da praca da bandeira com margen de 200m
        result = SearchService.searchByPosition(praca.getLatitude(), praca.getLongitude(), 200.00);
        assertEquals(0,result.size());

        //Busca a raro da praca da bandeira com margen de 300m
        result = SearchService.searchByPosition(praca.getLatitude(), praca.getLongitude(), 300.00);
        assertEquals(1,result.size());
        System.out.println("Distancia:" + result.get(0).getDistance());

        Rede rede2 = RedeFixture.novaRede2();
        //Agora existem duas redes, uma na raro e outra na escola, se busca da praca com 300 metros somente
        //a rede da raro pode aparecer
        result = SearchService.searchByPosition(praca.getLatitude(), praca.getLongitude(), 250.00);

        assertEquals(1, result.size());
        result = SearchService.searchByPosition(praca.getLatitude(), praca.getLongitude(), 500.00);
        assertEquals(2, result.size());

        //Se a procurar parte do endereco casa, que esta mais distante, o retorno deve ser 0
        result = SearchService.searchByPosition(casa.getLatitude(), casa.getLongitude(), 500.00);
        assertEquals(0,result.size());

        Usuario lesio = Usuario.novoUsuario(UsuarioFixture.getLesioPinheiro());
        Rede.solicitarAssociacao(rede1.getId(),lesio.getId(),EnderecoFixture.getEnderecoCasa());
        Membro lesioMembro = rede1.solicitacoesPendentes().iterator().next();
        Membro.aprovarAssociacao(lesioMembro.getId());

        //Agora, ja que houve a aprovacao de membro com endereco casa, uma busca apartir de
        //casa deve retornar a rede Raro
        result = SearchService.searchByPosition(casa.getLatitude(), casa.getLongitude(), 500.00);
        assertEquals(1,result.size());

        //Se esse membro for inativa, a pesquisa deve voltar a retornar 0
        Membro.inativarVizinho(lesioMembro.getId());
        result = SearchService.searchByPosition(casa.getLatitude(), casa.getLongitude(), 500.00);
        assertEquals(0, result.size());


    }




}
