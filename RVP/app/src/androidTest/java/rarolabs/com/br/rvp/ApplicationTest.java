package rarolabs.com.br.rvp;

import android.app.Application;
import android.location.Address;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.json.JsonParser;


import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import br.com.rarolabs.rvp.api.rvpAPI.RvpAPI;
import br.com.rarolabs.rvp.api.rvpAPI.RvpAPIRequest;
import br.com.rarolabs.rvp.api.rvpAPI.RvpAPIRequestInitializer;
import br.com.rarolabs.rvp.api.rvpAPI.model.Endereco;
import br.com.rarolabs.rvp.api.rvpAPI.model.GeoqueryResponderCollection;
import br.com.rarolabs.rvp.api.rvpAPI.model.Rede;
import br.com.rarolabs.rvp.api.rvpAPI.model.Usuario;
import rarolabs.com.br.rvp.services.EnderecoService;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    public ApplicationTest() {
        super(Application.class);


    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

    }


    public void testNovaRede() {
        try {
            RvpAPI service = getService();
            service.cleanDataBaseForTesting().executeUnparsed();
            Usuario u = new Usuario();
            u.setNome("João da Silva");
            u.setEmail("joao@rarolabs.com.br");
            u = service.novoUsuario(u).execute();

            Endereco e = new Endereco();
            e.setLatitude(40.7727419);
            e.setLongitude(-73.9348984);
            e.setRua("Rua Amazonas");

            Rede r = service.novaRede("Rede 1",u.getId(),e).execute();
            Rede loadedRede = service.buscarRede(r.getId()).execute();
            assertEquals(r.getId(), loadedRede.getId());

            GeoqueryResponderCollection result = service.buscarRedesProximas(40.7688418,	-73.9201355, 2000.00).execute();
            assertEquals(1,result.getItems().size());

            result = service.buscarRedesProximas(40.7688418,-73.9201355, 100.00).execute();
            assertEquals(0,result.getItems().size());


        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    public void testAssociacao(){
        try {
            RvpAPI service = getService();
            service.cleanDataBaseForTesting().executeUnparsed();
            Usuario u = new Usuario();
            u.setNome("João da Silva");
            u.setEmail("joao@rarolabs.com.br");
            u = service.novoUsuario(u).execute();
            assertNotNull(u.getId());
            System.out.println(u.getId());

            Endereco e = new Endereco();
            e.setLatitude(40.7727419);
            e.setLongitude(-73.9348984);
            e.setRua("Rua Amazonas");

            Rede r = service.novaRede("Rede 1",u.getId(),e).execute();

            u = new Usuario();
            u.setNome("Rodrigo Sol");
            u.setEmail("rodrigo@rarolabs.com.br");
            u = service.novoUsuario(u).execute();

            GeoqueryResponderCollection result = service.buscarRedesProximas(40.7688418,	-73.9201355, 2000.00).execute();

            assertEquals(1,result.getItems().size());
            e = new Endereco();
            e.setLatitude(40.7727419);
            e.setLongitude(-73.9348984);
            e.setRua("Rua Amazonas");

            Long redeId = result.getItems().get(0).getIdRede();

            assertNotNull(redeId);

            service.solicitarAssociacao(redeId,u.getId(),e);


        } catch (IOException e) {
            fail(e.getMessage());
        }


    }

    public void testNovoUsuario(){

        try {
            RvpAPI service = getService();
            service.cleanDataBaseForTesting().executeUnparsed();
            Usuario u = new Usuario();
            u.setNome("João da Silva");
            u.setEmail("joao@rarolabs.com.br");
            u = service.novoUsuario(u).execute();
            assertNotNull(u.getId());
            System.out.println(u.getId());
        } catch (IOException e) {
            fail(e.getMessage());
        }

    }

    public void testUsuarioDuplicado(){

        try {
            RvpAPI service = getService();
            service.cleanDataBaseForTesting().executeUnparsed();
            Usuario u = new Usuario();
            u.setNome("João da Silva");
            u.setEmail("joao@rarolabs.com.br");
            u = service.novoUsuario(u).execute();
            assertNotNull(u.getId());
            System.out.println(u.getId());
            u = new Usuario();
            u.setNome("João da Silva");
            u.setEmail("joao@rarolabs.com.br");
            u = service.novoUsuario(u).execute();

        } catch (IOException e) {
            try {
                Log.d("Test",e.getMessage());
                JsonParser json = new AndroidJsonFactory().createJsonParser(e.getMessage());
                JSONObject jObject = new JSONObject();
                json.parse(jObject);

                assertEquals("409",jObject.getString("code"));
                JSONObject error = jObject.getJSONArray("errors").getJSONObject(0);
                assertEquals("e-mail já cadastrado",error.getString("message"));

            } catch (IOException e1) {
                e1.printStackTrace();
                fail();
            } catch (JSONException e1) {
                e1.printStackTrace();
            }

        }



    }

    public void testAddress(){
        Log.e("Endereco", "Buscando por endereco");
        List<Address> results = EnderecoService.getLocationFromAddress(getContext(), "belo horizonte");
        for(Address a: results){
            Log.d("Endereco",a.getAddressLine(0));
            Log.d("Endereco",a.getAddressLine(1));
            Log.d("Endereco",a.getCountryName());
            Log.d("Endereco",a.getPostalCode());
            Log.d("Endereco",a.getLocality());
            Log.d("Endereco",String.valueOf(a.getLatitude()));
            Log.d("Endereco",String.valueOf(a.getLongitude()));
        }
    }

    public RvpAPI getService(){
        RvpAPI.Builder builder = new RvpAPI.Builder(
                AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(), null);
        builder.setRootUrl("http://10.0.0.103:8080/_ah/api");
        builder.setApplicationName("rvpAPI");
        builder.setGoogleClientRequestInitializer(new RvpAPIRequestInitializer() {
            protected void initializeRvpAPIRequest(RvpAPIRequest<?> request) {
                request.setDisableGZipContent(true);
            }
        });
        return builder.build();
    }

}