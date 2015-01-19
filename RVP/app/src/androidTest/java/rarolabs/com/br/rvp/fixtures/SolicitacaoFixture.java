package rarolabs.com.br.rvp.fixtures;

import android.content.Context;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import br.com.rarolabs.rvp.api.rvpAPI.model.Membro;
import br.com.rarolabs.rvp.api.rvpAPI.model.Rede;
import br.com.rarolabs.rvp.api.rvpAPI.model.Usuario;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.services.BackendServices;

/**
 * Created by rodrigosol on 12/31/14.
 */
public class SolicitacaoFixture {
    public static br.com.rarolabs.rvp.api.rvpAPI.model.MembroCollection criarSolicitacao(Context context,GoogleAccountCredential user,GoogleAccountCredential admin) throws Exception {
        BackendServices service = new BackendServices(context,admin);
        Rede rede = RedeFixture.novaRede1(service);
        service = new BackendServices(context,user, Constants.BACKEND_URL);
        Usuario lesio = service.novoUsuario(UsuarioFixture.getAdmin());
        service.solicitarAssociacao(rede.getId(), EnderecoFixture.getEnderecoCasa(), null, null, null);
        service.setCredential(admin);
        return service.solicitacoesPendentes(rede.getId());

    }

    public static Membro criarSolicitacaoAprovada(Context context,GoogleAccountCredential user,GoogleAccountCredential admin ) throws Exception {
        BackendServices service = new BackendServices(context,admin,Constants.BACKEND_URL);
        Rede rede = RedeFixture.novaRede1(service);
        service.setCredential(user);
        Usuario lesio = service.novoUsuario(UsuarioFixture.getAdmin());
        service.solicitarAssociacao(rede.getId(), EnderecoFixture.getEnderecoCasa(), null, null, null);
        service.setCredential(admin);
        Membro m = service.solicitacoesPendentes(rede.getId()).getItems().get(0);
        service.aprovarAssociacao(m.getId());

        return m;
    }
}
