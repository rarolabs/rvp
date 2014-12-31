package rarolabs.com.br.rvp.backend;

import android.app.Application;
import android.test.ApplicationTestCase;

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

    public CriarRedeTest() {
        super(Application.class);

    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testNovaRede() throws BackendExpection {
        BackendServices.cleanForTesting();
        Usuario u = BackendServices.novoUsuario(UsuarioFixture.getRodrigoSol());
        Rede r = BackendServices.novaRede("Amigos do Comiteco",u.getId(), EnderecoFixture.getEnderecoRaro());
        assertNotNull(r.getId());
        Membro dono = BackendServices.buscarDono(r.getId());
        assertEquals(dono.getUsuarioId(),u.getId());

        Membro membros = BackendServices.buscarMembros(r.getId()).getItems().iterator().next();

        assertEquals(u.getId(),dono.getUsuarioId());
        assertEquals(dono.getStatus(), "ATIVO");
        assertEquals(dono.getPapel(), "CRIADOR");
        assertEquals(dono.getVisibilidadeEmail(),"PUBLICO");
        assertEquals(dono.getVisibilidadeEndereco(), "PUBLICO");
        assertEquals(dono.getVisibilidadeTelefoneCelular(), "PUBLICO");
        assertEquals(dono.getVisibilidadeTelefoneFixo(), "PUBLICO");
    }


    public void testRedeComMesmoNome() {
        BackendServices.cleanForTesting();
        Usuario u = null;
        try {
            u = BackendServices.novoUsuario(UsuarioFixture.getRodrigoSol());
            Rede r = BackendServices.novaRede("Amigos do Comiteco",u.getId(), EnderecoFixture.getEnderecoRaro());
            Rede r1 = BackendServices.novaRede("Amigos do Comiteco",u.getId(), EnderecoFixture.getEnderecoRaro());
            fail();
        } catch (BackendExpection e) {
            assertEquals("Já existe uma rede com o nome:Amigos do Comiteco", e.getMessage());
        }
    }


}
