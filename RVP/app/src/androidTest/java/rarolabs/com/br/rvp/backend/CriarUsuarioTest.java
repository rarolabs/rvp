package rarolabs.com.br.rvp.backend;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import br.com.rarolabs.rvp.api.rvpAPI.model.Usuario;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.fixtures.UsuarioFixture;
import rarolabs.com.br.rvp.services.BackendExpection;
import rarolabs.com.br.rvp.services.BackendServices;

/**
* Created by rodrigosol on 12/31/14.
*/
public class CriarUsuarioTest extends ApplicationTestCase<Application> {

    private BackendServices service;

    public CriarUsuarioTest() {
        super(Application.class);

    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        GoogleAccountCredential credential = GoogleAccountCredential.usingAudience(getContext(), Constants.OAUTH_CLIENT_ID);
        credential.setSelectedAccountName("rodrigosol@gmail.com");
        this.service = new BackendServices(getContext(),credential,Constants.BACKEND_URL);
    }


    public void testNovoUsuario() throws Exception{
        //service.cleanForTesting();
        try {
            Usuario u = UsuarioFixture.getRodrigoSol();
            Usuario criado = service.novoUsuario(u);
            Usuario recuperado = service.buscarUsuario(criado.getId());

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


    public void testeUsuarioDuplicado() throws Exception {
        service.cleanForTesting();
        Usuario a = UsuarioFixture.getRodrigoSol();
        Usuario b = UsuarioFixture.getRodrigoSol();
        a.setEmail("rodrigosol@gmail.com");
        b.setEmail("rodrigosol@gmail.com");

        try {
            service.novoUsuario(a);
            service.novoUsuario(b);
            fail();
        } catch (BackendExpection e) {
            assertEquals("e-mail já cadastrado", e.getDescricao());
        }

    }

    public void testUsuarioNaoEncontrado() throws Exception{
        service.cleanForTesting();
        try {
            service.buscarUsuario("a@a.com");
            fail();
        } catch (BackendExpection e) {
            assertEquals("Usuario a@a.com nao encontrado", e.getDescricao());
        }

    }

}
