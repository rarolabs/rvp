//package br.com.rarolabs.rvp.api.test;
//
//import com.google.api.server.spi.response.ConflictException;
//import com.google.api.server.spi.response.NotFoundException;
//import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
//import com.google.appengine.tools.development.testing.LocalSearchServiceTestConfig;
//import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.Collection;
//
//import br.com.rarolabs.rvp.api.models.Membro;
//import br.com.rarolabs.rvp.api.models.Rede;
//import br.com.rarolabs.rvp.api.models.Usuario;
//import br.com.rarolabs.rvp.api.service.OfyService;
//import br.com.rarolabs.rvp.api.test.fixtures.EnderecoFixture;
//import br.com.rarolabs.rvp.api.test.fixtures.RedeFixture;
//import br.com.rarolabs.rvp.api.test.fixtures.UsuarioFixture;
//
//import static org.junit.Assert.*;
///**
// * Created by rodrigosol on 12/31/14.
// */
//public class SolicitarAcessoRede {
//    private final LocalServiceTestHelper helper =
//            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig(),new LocalSearchServiceTestConfig());
//
//    @Before
//    public void setUp() {
//        helper.setUp();
//        OfyService.ofy().clear();
//    }
//
//    @After
//    public void tearDown() {
//        helper.tearDown();
//    }
//
//    @Test
//    public void solicitarAcesso() throws ConflictException, NotFoundException {
//        Rede rede = RedeFixture.novaRede1();
//        Usuario lesio = Usuario.novoUsuario(UsuarioFixture.getLesioPinheiro());
//        Rede.solicitarAssociacao(rede.getId(),lesio.getId(), EnderecoFixture.getEnderecoCasa(), visibilidadeFixo, visibilidadeCel, visibilidadeEndereco);
//
//        Collection<Membro> solicitacoes = rede.solicitacoesPendentes();
//        assertEquals(1,solicitacoes.size());
//        Membro m = solicitacoes.iterator().next();
//        assertEquals(lesio.getId(),m.getUsuario().getId());
//        assertEquals(m.getStatus(), Membro.Status.AGUARDANDO_APROVACAO);
//        assertEquals(m.getPapel(), Membro.Papel.VIVIZINHO);
//        assertEquals(m.getRede().getId(),rede.getId());
//
//        Usuario ramon = Usuario.novoUsuario(UsuarioFixture.getRamonSetragni());
//        Rede.solicitarAssociacao(rede.getId(),ramon.getId(), EnderecoFixture.getEnderecoPraca(), visibilidadeFixo, visibilidadeCel, visibilidadeEndereco);
//
//        solicitacoes = rede.solicitacoesPendentes();
//        assertEquals(2,solicitacoes.size());
//
//
//    }
//
//    @Test
//    public void pedidoDuplicado() throws  NotFoundException {
//        Rede rede = null;
//        try {
//            rede = RedeFixture.novaRede1();
//            Usuario lesio = Usuario.novoUsuario(UsuarioFixture.getLesioPinheiro());
//            Rede.solicitarAssociacao(rede.getId(),lesio.getId(), EnderecoFixture.getEnderecoCasa(), visibilidadeFixo, visibilidadeCel, visibilidadeEndereco);
//
//            Collection<Membro> solicitacoes = rede.solicitacoesPendentes();
//            assertEquals(1,solicitacoes.size());
//            Membro m = solicitacoes.iterator().next();
//            assertEquals(lesio.getId(),m.getUsuario().getId());
//            assertEquals(m.getStatus(), Membro.Status.AGUARDANDO_APROVACAO);
//            assertEquals(m.getPapel(), Membro.Papel.VIVIZINHO);
//            assertEquals(m.getRede().getId(),rede.getId());
//
//            Usuario ramon = Usuario.novoUsuario(UsuarioFixture.getRamonSetragni());
//            Rede.solicitarAssociacao(rede.getId(),lesio.getId(), EnderecoFixture.getEnderecoPraca(), visibilidadeFixo, visibilidadeCel, visibilidadeEndereco);
//
//            fail();
//        } catch (ConflictException e) {
//            assertEquals("Já existe um pedido de associacao para esse usuario", e.getMessage());
//        }
//
//
//
//    }
//
//}
