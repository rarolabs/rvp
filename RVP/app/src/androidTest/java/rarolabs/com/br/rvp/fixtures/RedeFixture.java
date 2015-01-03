package rarolabs.com.br.rvp.fixtures;

import android.content.Context;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import br.com.rarolabs.rvp.api.rvpAPI.model.Rede;
import br.com.rarolabs.rvp.api.rvpAPI.model.Usuario;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.services.BackendExpection;
import rarolabs.com.br.rvp.services.BackendServices;

/**
 * Created by rodrigosol on 12/31/14.
 */
public class RedeFixture {
    public static Rede novaRede1(BackendServices service) throws BackendExpection {
        Usuario u = service.novoUsuario(UsuarioFixture.getRodrigoSol());
        return service.novaRede("Amigos do Comiteco" + Math.random(), EnderecoFixture.getEnderecoRaro());
    }

    public static Rede novaRede2(BackendServices service) throws BackendExpection {
        Usuario u = service.novoUsuario(UsuarioFixture.getRamonSetragni());
        return service.novaRede("Amigos da Praça" + Math.random(), EnderecoFixture.getEnderecoEscola());
    }

}
