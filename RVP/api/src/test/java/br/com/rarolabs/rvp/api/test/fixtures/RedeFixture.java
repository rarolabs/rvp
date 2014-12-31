package br.com.rarolabs.rvp.api.test.fixtures;

import com.google.api.server.spi.response.ConflictException;

import br.com.rarolabs.rvp.api.models.Rede;
import br.com.rarolabs.rvp.api.models.Usuario;

/**
 * Created by rodrigosol on 12/31/14.
 */
public class RedeFixture {
    public static Rede novaRede1() throws ConflictException {
        Usuario u = Usuario.novoUsuario(UsuarioFixture.getRodrigoSol());
        return Rede.novaRede("Amigos do Comiteco",u.getId(), EnderecoFixture.getEndereco1());
    }
}
