package rarolabs.com.br.rvp.backend;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import br.com.rarolabs.rvp.api.rvpAPI.model.Membro;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.fixtures.SolicitacaoFixture;
import rarolabs.com.br.rvp.services.BackendExpection;
import rarolabs.com.br.rvp.services.BackendServices;

/**
* Created by rodrigosol on 12/31/14.
*/
public class ManterMembrosTest  extends ApplicationTestCase<Application> {

    private BackendServices service;

    private GoogleAccountCredential rodrigoSol;
    private GoogleAccountCredential admin;

    public ManterMembrosTest() {
        super(Application.class);

    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        rodrigoSol = GoogleAccountCredential.usingAudience(getContext(), Constants.OAUTH_CLIENT_ID);
        rodrigoSol.setSelectedAccountName("rodrigosol@gmail.com");

        admin = GoogleAccountCredential.usingAudience(getContext(), Constants.OAUTH_CLIENT_ID);
        admin.setSelectedAccountName("admin@rarolabs.com.br");
        this.service = new BackendServices(rodrigoSol,Constants.BACKEND_URL);
    }

    public void testTornarAdministrador() throws BackendExpection {
        service.setCredential(rodrigoSol);
        service.cleanForTesting();
        Membro m = SolicitacaoFixture.criarSolicitacaoAprovada(admin,rodrigoSol);
        m = service.tornarAdministrador(m.getId());
        assertEquals(m.getStatus(), "ATIVO");
        assertEquals(m.getPapel(), "ADMIN");

    }


    public void testRetirarPermissaoAdministrador() throws BackendExpection, InterruptedException {
        service.setCredential(rodrigoSol);
        service.cleanForTesting();
        Membro m = SolicitacaoFixture.criarSolicitacaoAprovada(admin,rodrigoSol);
        m = service.retirarPermissaoAdministrador(m.getId());
        assertEquals(m.getStatus(), "ATIVO");
        assertEquals(m.getPapel(), "VIVIZINHO");

    }


    public void testTornarAutoriadade() throws BackendExpection {
        service.setCredential(rodrigoSol);
        service.cleanForTesting();
        Membro m = SolicitacaoFixture.criarSolicitacaoAprovada(admin,rodrigoSol);
        m = service.tornarAutoridade(m.getId());
        assertEquals(m.getStatus(), "ATIVO");
        assertEquals(m.getPapel(), "AUTORIDADE");

    }


    public void testRetirarPermissaoAutoriadade() throws BackendExpection, InterruptedException {
        service.setCredential(rodrigoSol);
        service.cleanForTesting();
        Membro m = SolicitacaoFixture.criarSolicitacaoAprovada(admin,rodrigoSol);
        m = service.retirarPermissaoAutoridade(m.getId());
        assertEquals(m.getStatus(), "ATIVO");
        assertEquals(m.getPapel(), "VIVIZINHO");

    }


    public void testInativarVizinho() throws BackendExpection {
        service.setCredential(rodrigoSol);
        service.cleanForTesting();
        Membro m = SolicitacaoFixture.criarSolicitacaoAprovada(admin,rodrigoSol);
        m = service.inativarVizinho(m.getId());
        assertEquals(m.getStatus(), "INATIVO");
        assertEquals(m.getPapel(), "VIVIZINHO");

    }


    public void testAtivarVizinho() throws BackendExpection, InterruptedException {
        service.setCredential(rodrigoSol);
        service.cleanForTesting();
        Membro m = SolicitacaoFixture.criarSolicitacaoAprovada(admin,rodrigoSol);
        m = service.ativarVizinho(m.getId());
        assertEquals(m.getStatus(), "ATIVO");
        assertEquals(m.getPapel(), "VIVIZINHO");

    }

}