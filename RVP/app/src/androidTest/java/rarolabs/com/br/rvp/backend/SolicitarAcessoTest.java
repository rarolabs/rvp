package rarolabs.com.br.rvp.backend;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import br.com.rarolabs.rvp.api.rvpAPI.model.Membro;
import br.com.rarolabs.rvp.api.rvpAPI.model.MembroCollection;
import br.com.rarolabs.rvp.api.rvpAPI.model.Rede;
import br.com.rarolabs.rvp.api.rvpAPI.model.Usuario;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.fixtures.EnderecoFixture;
import rarolabs.com.br.rvp.fixtures.RedeFixture;
import rarolabs.com.br.rvp.fixtures.UsuarioFixture;
import rarolabs.com.br.rvp.services.BackendExpection;
import rarolabs.com.br.rvp.services.BackendServices;

/**
* Created by rodrigosol on 12/31/14.
*/
public class SolicitarAcessoTest  extends ApplicationTestCase<Application> {

    private BackendServices service;
    private GoogleAccountCredential rodrigoSol;
    private GoogleAccountCredential admin;
    private GoogleAccountCredential carol;

    public SolicitarAcessoTest() {
        super(Application.class);

    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        rodrigoSol = GoogleAccountCredential.usingAudience(getContext(), Constants.OAUTH_CLIENT_ID);
        rodrigoSol.setSelectedAccountName("rodrigosol@gmail.com");

        admin = GoogleAccountCredential.usingAudience(getContext(), Constants.OAUTH_CLIENT_ID);
        admin.setSelectedAccountName("admin@rarolabs.com.br");

        carol = GoogleAccountCredential.usingAudience(getContext(), Constants.OAUTH_CLIENT_ID);
        carol.setSelectedAccountName("acarolsm@gmail.com");
        this.service = new BackendServices(getContext(),rodrigoSol,Constants.BACKEND_URL);

    }


    public void testSolicitarAcesso() throws Exception {
        service.cleanForTesting();
        Rede rede = RedeFixture.novaRede1(service);
        service.setCredential(admin);
        Usuario lesio = service.novoUsuario(UsuarioFixture.getAdmin());
        service.solicitarAssociacao(rede.getId(), EnderecoFixture.getEnderecoCasa(), null, null, null);
        service.setCredential(rodrigoSol);

        MembroCollection solicitacoes = service.solicitacoesPendentes(rede.getId());
        assertEquals(1,solicitacoes.getItems().size());
        Membro m = solicitacoes.getItems().get(0);
        assertEquals(lesio.getId(),m.getUsuarioId());
        assertEquals(m.getStatus(), "AGUARDANDO_APROVACAO");
        assertEquals(m.getPapel(), "VIVIZINHO");
        assertEquals(m.getRedeId(),rede.getId());

        service.setCredential(carol);
        Usuario ramon = service.novoUsuario(UsuarioFixture.getRamonSetragni());
        service.solicitarAssociacao(rede.getId(), EnderecoFixture.getEnderecoPraca(), null, null, null);

        service.setCredential(rodrigoSol);
        solicitacoes = service.solicitacoesPendentes(rede.getId());
        assertEquals(2, solicitacoes.getItems().size());


    }


    public void pedidoDuplicado() throws Exception{

        try {
            service.cleanForTesting();
            Rede rede = RedeFixture.novaRede1(service);
            service.setCredential(admin);
            Usuario lesio = service.novoUsuario(UsuarioFixture.getAdmin());
            service.solicitarAssociacao(rede.getId(), EnderecoFixture.getEnderecoCasa(), null, null, null);
            service.solicitarAssociacao(rede.getId(), EnderecoFixture.getEnderecoCasa(), null, null, null);

            fail();
        } catch (BackendExpection e) {
            assertEquals("Já existe um pedido de associacao para esse usuario", e.getMessage());
        }


    }


    public void testMembros() throws Exception {
        service.cleanForTesting();
        Rede rede = RedeFixture.novaRede1(service);
        service.setCredential(admin);
        Usuario lesio = service.novoUsuario(UsuarioFixture.getAdmin());
        Membro solicitacao = service.solicitarAssociacao(rede.getId(), EnderecoFixture.getEnderecoCasa(), null, null, null);
        service.setCredential(rodrigoSol);
        assertEquals(2,service.buscarMembros(rede.getId()).getItems().size());
        assertEquals(1,service.buscarMembrosAtivos(rede.getId()).getItems().size());
        service.aprovarAssociacao(solicitacao.getId());
        assertEquals(2,service.buscarMembros(rede.getId()).getItems().size());
        assertEquals(2, service.buscarMembrosAtivos(rede.getId()).getItems().size());
    }


}