package rarolabs.com.br.rvp.fixtures;

import br.com.rarolabs.rvp.api.rvpAPI.model.Rede;
import br.com.rarolabs.rvp.api.rvpAPI.model.Usuario;
import rarolabs.com.br.rvp.services.BackendExpection;
import rarolabs.com.br.rvp.services.BackendServices;

/**
 * Created by rodrigosol on 12/31/14.
 */
public class RedeFixture {
    public static Rede novaRede1() throws BackendExpection {
        Usuario u = BackendServices.novoUsuario(UsuarioFixture.getRodrigoSol());
        return BackendServices.novaRede("Amigos do Comiteco" + Math.random(),u.getId(), EnderecoFixture.getEnderecoRaro());
    }

    public static Rede novaRede2() throws BackendExpection {
        Usuario u = BackendServices.novoUsuario(UsuarioFixture.getRamonSetragni());
        return BackendServices.novaRede("Amigos da Praça" + Math.random(),u.getId(), EnderecoFixture.getEnderecoEscola());
    }

}
