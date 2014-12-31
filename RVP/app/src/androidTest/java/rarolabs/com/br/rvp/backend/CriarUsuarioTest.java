package rarolabs.com.br.rvp.backend;

import android.app.Application;
import android.test.ApplicationTestCase;

import br.com.rarolabs.rvp.api.rvpAPI.model.Usuario;
import rarolabs.com.br.rvp.fixtures.UsuarioFixture;
import rarolabs.com.br.rvp.services.BackendExpection;
import rarolabs.com.br.rvp.services.BackendServices;

/**
 * Created by rodrigosol on 12/31/14.
 */
public class CriarUsuarioTest extends ApplicationTestCase<Application> {

    public CriarUsuarioTest() {
        super(Application.class);

    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }


    public void testNovoUsuario() {
        BackendServices.cleanForTesting();
        try {
            Usuario u = UsuarioFixture.getRodrigoSol();
            Usuario criado = BackendServices.novoUsuario(u);
            Usuario recuperado = BackendServices.buscarUsuario(criado.getId());

            assertEquals(criado.getId(), recuperado.getId());
            assertEquals(criado.getEmail(), recuperado.getEmail());
            assertEquals("Rodrigo Sol", recuperado.getNome());
            assertEquals("31", recuperado.getDddTelefoneCelular());
            assertEquals("71718438", recuperado.getTelefoneCelular());
            assertEquals("32", recuperado.getDddTelefoneFixo());
            assertEquals("33844939", recuperado.getTelefoneFixo());
        } catch (BackendExpection backendExpection) {
            backendExpection.printStackTrace();
        }
    }


    public void testeUsuarioDuplicado() throws InterruptedException {
        BackendServices.cleanForTesting();
        Usuario a = UsuarioFixture.getRodrigoSol();
        Usuario b = UsuarioFixture.getRodrigoSol();
        a.setEmail("rodrigosol@gmail.com");
        b.setEmail("rodrigosol@gmail.com");

        try {
            BackendServices.novoUsuario(a);
            BackendServices.novoUsuario(b);
            fail();
        } catch (BackendExpection e) {
            assertEquals("e-mail já cadastrado", e.getMessage());
        }

    }

    public void testUsuarioNaoEncontrado() {
        BackendServices.cleanForTesting();
        try {
            BackendServices.buscarUsuario(22l);
            fail();
        } catch (BackendExpection e) {
            assertEquals("Usuario 22 nao encontrado", e.getMessage());
        }

    }

}
