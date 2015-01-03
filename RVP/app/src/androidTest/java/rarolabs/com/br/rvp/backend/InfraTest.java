package rarolabs.com.br.rvp.backend;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import java.net.SocketException;
import java.net.UnknownHostException;

import br.com.rarolabs.rvp.api.rvpAPI.model.Rede;
import br.com.rarolabs.rvp.api.rvpAPI.model.Usuario;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.fixtures.RedeFixture;
import rarolabs.com.br.rvp.fixtures.UsuarioFixture;
import rarolabs.com.br.rvp.services.BackendExpection;
import rarolabs.com.br.rvp.services.BackendServices;

/**
 * Created by rodrigosol on 1/3/15.
 */
public class InfraTest extends ApplicationTestCase<Application> {
    private BackendServices service;
    private GoogleAccountCredential credential;

    public InfraTest() {
        super(Application.class);

    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        credential = GoogleAccountCredential.usingAudience(getContext(), Constants.OAUTH_CLIENT_ID);
        credential.setSelectedAccountName("rodrigosol@gmail.com");
    }

    public void testServicoIndisponivel(){
        this.service = new BackendServices(getContext(),credential,"http://8.8.8.8/api");
        try {
            service.buscarRede(1l);
            fail();
        } catch (BackendExpection backendExpection) {
            assertEquals("Não foi possível ler dados do servidor",backendExpection.getDescricao());
            assertEquals(BackendExpection.Tipo.REDE,backendExpection.getTipo());
        }
    }

    public void testEnderecoNaoEncontrado(){
        this.service = new BackendServices(getContext(),credential,"http://testervpapi");
        try {
            service.buscarRede(1l);
            fail();
        } catch (BackendExpection backendExpection) {
            assertEquals("Servidor não está disponível",backendExpection.getDescricao());
            assertEquals(BackendExpection.Tipo.REDE,backendExpection.getTipo());
        }
    }

    public void testAuthenticacaoUsuarioInvalido(){
        credential.setSelectedAccountName("foo@foo.com");
        this.service = new BackendServices(getContext(),credential,"http://testervpapi");
        try {
            service.buscarRede(1l);
            fail();
        } catch (BackendExpection backendExpection) {
            assertEquals("Não foi possível autenticar o usuário",backendExpection.getDescricao());
            assertEquals(BackendExpection.Tipo.AUTENTICACAO,backendExpection.getTipo());
        }
    }

    public void testAutorizacao(){
        this.service = new BackendServices(getContext(),credential,Constants.BACKEND_URL);
        service.cleanForTesting();
        try {
            Rede e = RedeFixture.novaRede1(service);
            credential.setSelectedAccountName("admin@rarolabs.com.br");
            service.setCredential(credential);
            service.novoUsuario(UsuarioFixture.getAdmin());
            service.apagarRede(e.getId());
            fail();
        } catch (BackendExpection backendExpection) {
            assertEquals("O usuário admin@rarolabs.com.br não tem permissão para apagar essa rede",backendExpection.getDescricao());
            assertEquals(BackendExpection.Tipo.VALIDACAO,backendExpection.getTipo());
        }

    }

}
