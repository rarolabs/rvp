package rarolabs.com.br.rvp.backend;

import android.app.Application;
import android.test.ApplicationTestCase;

import br.com.rarolabs.rvp.api.rvpAPI.model.Membro;
import br.com.rarolabs.rvp.api.rvpAPI.model.MembroCollection;
import br.com.rarolabs.rvp.api.rvpAPI.model.Rede;
import br.com.rarolabs.rvp.api.rvpAPI.model.Usuario;
import rarolabs.com.br.rvp.fixtures.EnderecoFixture;
import rarolabs.com.br.rvp.fixtures.RedeFixture;
import rarolabs.com.br.rvp.fixtures.SolicitacaoFixture;
import rarolabs.com.br.rvp.fixtures.UsuarioFixture;
import rarolabs.com.br.rvp.services.BackendExpection;
import rarolabs.com.br.rvp.services.BackendServices;

/**
 * Created by rodrigosol on 12/31/14.
 */
public class ManterMembrosTest  extends ApplicationTestCase<Application> {

    public ManterMembrosTest() {
        super(Application.class);

    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testTornarAdministrador() throws BackendExpection {
        BackendServices.cleanForTesting();
        Membro m = SolicitacaoFixture.criarSolicitacaoAprovada();

        m = BackendServices.tornarAdministrador(m.getId());
        assertEquals(m.getStatus(), "ATIVO");
        assertEquals(m.getPapel(), "ADMIN");

    }


    public void testRetirarPermissaoAdministrador() throws BackendExpection, InterruptedException {
        BackendServices.cleanForTesting();
        Membro m = SolicitacaoFixture.criarSolicitacaoAprovada();

        m = BackendServices.retirarPermissaoAdministrador(m.getId());
        assertEquals(m.getStatus(), "ATIVO");
        assertEquals(m.getPapel(), "VIVIZINHO");

    }


    public void testTornarAutoriadade() throws BackendExpection {
        BackendServices.cleanForTesting();
        Membro m = SolicitacaoFixture.criarSolicitacaoAprovada();

        m = BackendServices.tornarAutoridade(m.getId());
        assertEquals(m.getStatus(), "ATIVO");
        assertEquals(m.getPapel(), "AUTORIDADE");

    }


    public void testRetirarPermissaoAutoriadade() throws BackendExpection, InterruptedException {
        BackendServices.cleanForTesting();
        Membro m = SolicitacaoFixture.criarSolicitacaoAprovada();

        m = BackendServices.retirarPermissaoAutoridade(m.getId());
        assertEquals(m.getStatus(), "ATIVO");
        assertEquals(m.getPapel(), "VIVIZINHO");

    }


    public void testInativarVizinho() throws BackendExpection {
        BackendServices.cleanForTesting();
        Membro m = SolicitacaoFixture.criarSolicitacaoAprovada();

        m = BackendServices.inativarVizinho(m.getId());
        assertEquals(m.getStatus(), "INATIVO");
        assertEquals(m.getPapel(), "VIVIZINHO");

    }


    public void testAtivarVizinho() throws BackendExpection, InterruptedException {
        BackendServices.cleanForTesting();
        Membro m = SolicitacaoFixture.criarSolicitacaoAprovada();

        m = BackendServices.ativarVizinho(m.getId());
        assertEquals(m.getStatus(), "ATIVO");
        assertEquals(m.getPapel(), "VIVIZINHO");

    }



    public void testSolicitarAcesso() throws BackendExpection, InterruptedException {
        BackendServices.cleanForTesting();
        Rede rede = RedeFixture.novaRede1();
        Usuario lesio = BackendServices.novoUsuario(UsuarioFixture.getLesioPinheiro());
        BackendServices.solicitarAssociacao(rede.getId(),lesio.getId(), EnderecoFixture.getEnderecoCasa());

        MembroCollection solicitacoes = BackendServices.solicitacoesPendentes(rede.getId());
        assertEquals(1,solicitacoes.size());
        Membro m = solicitacoes.getItems().get(0);
        assertEquals(lesio.getId(),m.getUsuarioId());
        assertEquals(m.getStatus(), "AGUARDANDO_APROVACAO");
        assertEquals(m.getPapel(), "VIVIZINHO");
        assertEquals(m.getRedeId(),rede.getId());

        Usuario ramon = BackendServices.novoUsuario(UsuarioFixture.getRamonSetragni());
        BackendServices.solicitarAssociacao(rede.getId(),ramon.getId(), EnderecoFixture.getEnderecoPraca());
        solicitacoes = BackendServices.solicitacoesPendentes(rede.getId());
        assertEquals(2,solicitacoes.getItems().size());


    }

}