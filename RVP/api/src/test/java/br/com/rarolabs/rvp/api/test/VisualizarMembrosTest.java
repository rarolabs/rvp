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
import java.util.Set;
import java.util.TreeSet;

import br.com.rarolabs.rvp.api.models.Membro;
import br.com.rarolabs.rvp.api.models.Rede;
import br.com.rarolabs.rvp.api.models.Usuario;
import br.com.rarolabs.rvp.api.service.OfyService;
import br.com.rarolabs.rvp.api.test.fixtures.EnderecoFixture;
import br.com.rarolabs.rvp.api.test.fixtures.UsuarioFixture;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by rodrigosol on 12/31/14.
 */
public class VisualizarMembrosTest {

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
    public void visualizarMembros() throws ConflictException, NotFoundException, ForbiddenException {

        Usuario rodrigo = Usuario.novoUsuario(UsuarioFixture.getRodrigoSol());
        Rede rede = Rede.novaRede("Amigos do Comiteco",rodrigo.getId(), EnderecoFixture.getEnderecoRaro(), visibilidadeFixo, visibilidadeCel, visibilidadeEndereco);

        Usuario lesio = Usuario.novoUsuario(UsuarioFixture.getLesioPinheiro());
        Rede.solicitarAssociacao(rede.getId(), lesio.getId(), EnderecoFixture.getEnderecoCasa(), visibilidadeFixo, visibilidadeCel, visibilidadeEndereco);

        Membro lesioMembro = rede.solicitacoesPendentes().iterator().next();
        Membro.aprovarAssociacao(lesioMembro.getId(), tornarAdministrador, tornarAutoridade);


        Usuario ramon = Usuario.novoUsuario(UsuarioFixture.getRamonSetragni());
        Rede.solicitarAssociacao(rede.getId(), ramon.getId(), EnderecoFixture.getEnderecoPraca(), visibilidadeFixo, visibilidadeCel, visibilidadeEndereco);

        Membro ramonMembro = rede.solicitacoesPendentes().iterator().next();
        Membro.aprovarAssociacao(ramonMembro.getId(), tornarAdministrador, tornarAutoridade);


        Usuario leo = Usuario.novoUsuario(UsuarioFixture.getLeonardoHerbert());
        Rede.solicitarAssociacao(rede.getId(), leo.getId(), EnderecoFixture.getEnderecoEscola(), visibilidadeFixo, visibilidadeCel, visibilidadeEndereco);


        Collection<Membro> membrosAtivos = Rede.membrosAtivos(rede.getId());
        assertEquals(3,membrosAtivos.size());
        Set<Long> actual = new TreeSet<Long>();
        for(Membro m: membrosAtivos){
            actual.add(m.getId());
        }
        Set<Long> expected = new TreeSet<Long>();
        expected.add(rede.getDono().getId());
        expected.add(ramonMembro.getId());
        expected.add(lesioMembro.getId());

        for(int i =0; i < expected.size(); i++){
            assertEquals(expected.toArray()[i],actual.toArray()[i]);
        }

    }
}