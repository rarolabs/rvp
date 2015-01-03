package br.com.rarolabs.rvp.api.test;

import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalSearchServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.rarolabs.rvp.api.models.Usuario;
import br.com.rarolabs.rvp.api.service.OfyService;
import br.com.rarolabs.rvp.api.test.fixtures.UsuarioFixture;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by rodrigosol on 12/31/14.
 */
public class CriarUsuarioTest {
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
    public void novoUsuarioTest(){
        try {
            Usuario u = UsuarioFixture.getRodrigoSol();


            Usuario criado = Usuario.novoUsuario(u);
            Usuario recuperado = Usuario.buscar(u.getId());

            assertEquals(criado.getId(),recuperado.getId());
            assertEquals("rodrigosol@gmail.com",recuperado.getEmail());
            assertEquals("Rodrigo Sol",recuperado.getNome());
            assertEquals("31",recuperado.getDddTelefoneCelular());
            assertEquals("71718438",recuperado.getTelefoneCelular());
            assertEquals("32",recuperado.getDddTelefoneFixo());
            assertEquals("33844939",recuperado.getTelefoneFixo());

        } catch (ConflictException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (NotFoundException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void usuarioDuplicadoTest(){
        Usuario a = UsuarioFixture.getRodrigoSol();
        Usuario b = UsuarioFixture.getRodrigoSol();

        try {
            Usuario.novoUsuario(a);
            Usuario.novoUsuario(b);
            fail();
        } catch (ConflictException e) {
            assertEquals("e-mail já cadastrado",e.getMessage());
        }

    }

    @Test
    public void usuarioNaoEncontradoTest(){

        try {
            Usuario.buscar("abacate@com.com");
            fail();
        } catch (NotFoundException e) {
            assertEquals("Usuario abacate@com.com nao encontrado",e.getMessage());
        }

    }

}
