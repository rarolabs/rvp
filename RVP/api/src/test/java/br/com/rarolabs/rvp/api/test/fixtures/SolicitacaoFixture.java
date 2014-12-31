package br.com.rarolabs.rvp.api.test.fixtures;

import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.ForbiddenException;
import com.google.api.server.spi.response.NotFoundException;

import br.com.rarolabs.rvp.api.models.Membro;
import br.com.rarolabs.rvp.api.models.Rede;
import br.com.rarolabs.rvp.api.models.Usuario;

import static org.junit.Assert.assertEquals;

/**
 * Created by rodrigosol on 12/31/14.
 */
public class SolicitacaoFixture {
    public static Membro criarSolicitacao() throws ConflictException, NotFoundException {
        Rede rede = RedeFixture.novaRede1();
        Usuario lesio = Usuario.novoUsuario(UsuarioFixture.getLesioPinheiro());
        Rede.solicitarAssociacao(rede.getId(), lesio.getId(), EnderecoFixture.getEnderecoCasa());

        return rede.solicitacoesPendentes().iterator().next();

    }

    public static Membro criarSolicitacaoAprovada() throws ConflictException, NotFoundException, ForbiddenException {
        Rede rede = RedeFixture.novaRede1();
        Usuario lesio = Usuario.novoUsuario(UsuarioFixture.getLesioPinheiro());
        Rede.solicitarAssociacao(rede.getId(), lesio.getId(), EnderecoFixture.getEnderecoCasa());

        Membro m = rede.solicitacoesPendentes().iterator().next();
        Membro.aprovarAssociacao(m.getId());

        return m;

    }

}
