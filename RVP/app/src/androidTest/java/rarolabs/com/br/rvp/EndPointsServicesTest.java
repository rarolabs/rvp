package rarolabs.com.br.rvp;

import android.app.Application;
import android.location.Address;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.json.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import br.com.rarolabs.rvp.api.rvpAPI.RvpAPI;
import br.com.rarolabs.rvp.api.rvpAPI.RvpAPIRequest;
import br.com.rarolabs.rvp.api.rvpAPI.RvpAPIRequestInitializer;
import br.com.rarolabs.rvp.api.rvpAPI.model.Endereco;
import br.com.rarolabs.rvp.api.rvpAPI.model.GeoqueryResponderCollection;
import br.com.rarolabs.rvp.api.rvpAPI.model.Membro;
import br.com.rarolabs.rvp.api.rvpAPI.model.Rede;
import br.com.rarolabs.rvp.api.rvpAPI.model.Usuario;
import rarolabs.com.br.rvp.fixtures.EnderecoFixture;
import rarolabs.com.br.rvp.fixtures.UsuarioFixture;
import rarolabs.com.br.rvp.services.BackendExpection;
import rarolabs.com.br.rvp.services.BackendServices;
import rarolabs.com.br.rvp.services.EnderecoServices;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class EndPointsServicesTest extends ApplicationTestCase<Application> {

    public EndPointsServicesTest() {
        super(Application.class);

    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }


    public void testNovaRede() throws BackendExpection {
        Usuario u = BackendServices.novoUsuario(UsuarioFixture.getRodrigoSol());
        Rede r = BackendServices.novaRede("Amigos do Comiteco",u.getId(), EnderecoFixture.getEnderecoRaro());
        assertNotNull(r.getId());
        Membro dono = BackendServices.buscarDono(r.getId());
        assertEquals(dono.getUsuarioId(),u.getId());

        Membro membros = BackendServices.buscarMembros(r.getId()).getItems().iterator().next();

        assertEquals(u.getId(),dono.getUsuarioId());
        assertEquals(dono.getStatus(), "ATIVO");
        assertEquals(dono.getPapel(), "CRIADOR");
        assertEquals(dono.getVisibilidade().getEmail(),"PUBLICO");
        assertEquals(dono.getVisibilidade().getEndereco(), "PUBLICO");
        assertEquals(dono.getVisibilidade().getTelefoneCelular(), "PUBLICO");
        assertEquals(dono.getVisibilidade().getTelefoneFixo(), "PUBLICO");
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