package rarolabs.com.br.rvp.backend;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.util.Collection;

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
public class SolicitarAcessoTest  extends ApplicationTestCase<Application> {

    public SolicitarAcessoTest() {
        super(Application.class);

    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }


    public void testSolicitarAcesso() throws BackendExpection {
        Rede rede = RedeFixture.novaRede1();
        Usuario lesio = BackendServices.novoUsuario(UsuarioFixture.getLesioPinheiro());
        BackendServices.solicitarAssociacao(rede.getId(),lesio.getId(), EnderecoFixture.getEnderecoCasa());

        MembroCollection solicitacoes = BackendServices.solicitacoesPendentes(rede.getId());
        assertEquals(1,solicitacoes.getItems().size());
        Membro m = solicitacoes.getItems().get(0);
        assertEquals(lesio.getId(),m.getUsuarioId());
        assertEquals(m.getStatus(), "AGUARDANDO_APROVACAO");
        assertEquals(m.getPapel(), "VIVIZINHO");
        assertEquals(m.getRedeId(),rede.getId());

        Usuario ramon = BackendServices.novoUsuario(UsuarioFixture.getRamonSetragni());
        BackendServices.solicitarAssociacao(rede.getId(),ramon.getId(), EnderecoFixture.getEnderecoPraca());

        solicitacoes = BackendServices.solicitacoesPendentes(rede.getId());
        assertEquals(2, solicitacoes.getItems().size());


    }


    public void pedidoDuplicado(){

        try {
            Rede rede = null;
            rede = RedeFixture.novaRede1();
            Usuario lesio = BackendServices.novoUsuario(UsuarioFixture.getLesioPinheiro());
            BackendServices.solicitarAssociacao(rede.getId(),lesio.getId(), EnderecoFixture.getEnderecoCasa());

            MembroCollection solicitacoes = BackendServices.solicitacoesPendentes(rede.getId());
            assertEquals(1,solicitacoes.getItems().size());
            Membro m = solicitacoes.getItems().get(0);
            assertEquals(lesio.getId(),m.getUsuarioId());
            assertEquals(m.getStatus(), "AGUARDANDO_APROVACAO");
            assertEquals(m.getPapel(), "VIVIZINHO");
            assertEquals(m.getRedeId(),rede.getId());

            Usuario ramon = BackendServices.novoUsuario(UsuarioFixture.getRamonSetragni());
            BackendServices.solicitarAssociacao(rede.getId(),lesio.getId(), EnderecoFixture.getEnderecoPraca());

            fail();
        } catch (BackendExpection e) {
            assertEquals("Já existe um pedido de associacao para esse usuario", e.getMessage());
        }


    }


}