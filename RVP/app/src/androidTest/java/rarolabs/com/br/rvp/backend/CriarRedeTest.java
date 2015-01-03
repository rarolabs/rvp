package rarolabs.com.br.rvp.backend;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import br.com.rarolabs.rvp.api.rvpAPI.model.Membro;
import br.com.rarolabs.rvp.api.rvpAPI.model.Rede;
import br.com.rarolabs.rvp.api.rvpAPI.model.Usuario;
import rarolabs.com.br.rvp.fixtures.EnderecoFixture;
import rarolabs.com.br.rvp.fixtures.UsuarioFixture;
import rarolabs.com.br.rvp.services.BackendExpection;
import rarolabs.com.br.rvp.services.BackendServices;

/**
* Created by rodrigosol on 12/31/14.
*/
public class CriarRedeTest extends ApplicationTestCase<Application> {

    private BackendServices service;

    public CriarRedeTest() {
        super(Application.class);

    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        GoogleAccountCredential credential = GoogleAccountCredential.usingAudience(getContext(), "server:client_id:701949285974-83l9d3ibrmaerqboebi7fvpm3s3tcarc.apps.googleusercontent.com");
        credential.setSelectedAccountName("rodrigosol@gmail.com");
        this.service = new BackendServices(credential);

    }

    public void testNovaRede() throws BackendExpection {
        service.cleanForTesting();
        Usuario u = service.novoUsuario(UsuarioFixture.getRodrigoSol());
        Rede r = service.novaRede("Amigos do Comiteco", EnderecoFixture.getEnderecoRaro());
        assertNotNull(r.getId());
        Membro dono = service.buscarDono(r.getId());
        assertEquals(dono.getUsuarioId(),u.getId());

        Membro membros = service.buscarMembros(r.getId()).getItems().iterator().next();

        assertEquals(u.getId(),dono.getUsuarioId());
        assertEquals(dono.getStatus(), "ATIVO");
        assertEquals(dono.getPapel(), "CRIADOR");
        assertEquals(dono.getVisibilidadeEmail(),"PUBLICO");
        assertEquals(dono.getVisibilidadeEndereco(), "PUBLICO");
        assertEquals(dono.getVisibilidadeTelefoneCelular(), "PUBLICO");
        assertEquals(dono.getVisibilidadeTelefoneFixo(), "PUBLICO");
    }


    public void testRedeComMesmoNome() {

        try {
            service.cleanForTesting();
            Usuario u = service.novoUsuario(UsuarioFixture.getRodrigoSol());
            Rede r = service.novaRede("Amigos do Comiteco", EnderecoFixture.getEnderecoRaro());
            Rede r1 = service.novaRede("Amigos do Comiteco", EnderecoFixture.getEnderecoRaro());
            fail();
        } catch (BackendExpection e) {
            assertEquals("Já existe uma rede com o nome:Amigos do Comiteco", e.getMessage());
        }
    }

    public void testBuscarDono() throws BackendExpection {
            service.cleanForTesting();
            Usuario u = service.novoUsuario(UsuarioFixture.getRodrigoSol());
            Rede r = service.novaRede("Amigos do Comiteco", EnderecoFixture.getEnderecoRaro());
            Membro dono = service.buscarDono(r.getId());
            assertEquals(u.getId(),dono.getUsuarioId());

    }



}
