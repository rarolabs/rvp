//package br.com.rarolabs.rvp.api.test;
//
//import com.google.api.server.spi.response.ConflictException;
//import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
//import com.google.appengine.tools.development.testing.LocalSearchServiceTestConfig;
//import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import br.com.rarolabs.rvp.api.models.Membro;
//import br.com.rarolabs.rvp.api.models.Rede;
//import br.com.rarolabs.rvp.api.models.Usuario;
//import br.com.rarolabs.rvp.api.service.OfyService;
//import br.com.rarolabs.rvp.api.test.fixtures.EnderecoFixture;
//import br.com.rarolabs.rvp.api.test.fixtures.UsuarioFixture;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.fail;
//
///**
// * Created by rodrigosol on 12/31/14.
// */
//public class CriarRedesTest {
//
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
//    public void novaRede() throws ConflictException {
//        Usuario u = Usuario.novoUsuario(UsuarioFixture.getRodrigoSol());
//        Rede r = Rede.novaRede("Amigos do Comiteco",u.getId(), EnderecoFixture.getEnderecoRaro(), visibilidadeFixo, visibilidadeCel, visibilidadeEndereco);
//        assertNotNull(r.getId());
//        assertEquals(r.getDono().getUsuario().getId(),u.getId());
//        Membro dono = r.getMembros().iterator().next();
//
//        assertEquals(u.getId(),dono.getUsuario().getId());
//        assertEquals(dono.getStatus(), Membro.Status.ATIVO);
//        assertEquals(dono.getPapel(), Membro.Papel.CRIADOR);
//        assertEquals(dono.getVisibilidadeEmail(), Membro.Visibilidade.PUBLICO);
//        assertEquals(dono.getVisibilidadeEndereco(), Membro.Visibilidade.PUBLICO);
//        assertEquals(dono.getVisibilidadeTelefoneCelular(), Membro.Visibilidade.PUBLICO);
//        assertEquals(dono.getVisibilidadeTelefoneFixo(), Membro.Visibilidade.PUBLICO);
//    }
//
//    @Test
//    public void redeComMesmoNome() {
//        Usuario u = null;
//        try {
//            u = Usuario.novoUsuario(UsuarioFixture.getRodrigoSol());
//            Rede r = Rede.novaRede("Amigos do Comiteco",u.getId(), EnderecoFixture.getEnderecoRaro(), visibilidadeFixo, visibilidadeCel, visibilidadeEndereco);
//            Rede r1 = Rede.novaRede("Amigos do Comiteco",u.getId(), EnderecoFixture.getEnderecoRaro(), visibilidadeFixo, visibilidadeCel, visibilidadeEndereco);
//            fail();
//        } catch (ConflictException e) {
//            assertEquals("Já existe uma rede com o nome:Amigos do Comiteco", e.getMessage());
//        }
//
//    }
//
//
//    }
