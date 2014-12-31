package rarolabs.com.br.rvp.fixtures;

import br.com.rarolabs.rvp.api.rvpAPI.model.Membro;
import br.com.rarolabs.rvp.api.rvpAPI.model.Rede;
import br.com.rarolabs.rvp.api.rvpAPI.model.Usuario;
import rarolabs.com.br.rvp.services.BackendExpection;
import rarolabs.com.br.rvp.services.BackendServices;

/**
 * Created by rodrigosol on 12/31/14.
 */
public class SolicitacaoFixture {
    public static br.com.rarolabs.rvp.api.rvpAPI.model.MembroCollection criarSolicitacao() throws BackendExpection {
        Rede rede = RedeFixture.novaRede1();
        Usuario lesio = BackendServices.novoUsuario(UsuarioFixture.getLesioPinheiro());
        BackendServices.solicitarAssociacao(rede.getId(), lesio.getId(), EnderecoFixture.getEnderecoCasa());

        return BackendServices.solicitacoesPendentes(rede.getId());

    }

    public static Membro criarSolicitacaoAprovada() throws BackendExpection {
        Rede rede = RedeFixture.novaRede1();
        Usuario lesio = BackendServices.novoUsuario(UsuarioFixture.getLesioPinheiro());
        BackendServices.solicitarAssociacao(rede.getId(), lesio.getId(), EnderecoFixture.getEnderecoCasa());

        Membro m = BackendServices.solicitacoesPendentes(rede.getId()).getItems().get(0);
        BackendServices.aprovarAssociacao(m.getId());


        return m;

    }
}
