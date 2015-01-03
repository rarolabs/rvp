package rarolabs.com.br.rvp.backend;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import java.util.TreeSet;

import br.com.rarolabs.rvp.api.rvpAPI.model.Endereco;
import br.com.rarolabs.rvp.api.rvpAPI.model.GeoqueryResponderCollection;
import br.com.rarolabs.rvp.api.rvpAPI.model.Membro;
import br.com.rarolabs.rvp.api.rvpAPI.model.MembroCollection;
import br.com.rarolabs.rvp.api.rvpAPI.model.Rede;
import br.com.rarolabs.rvp.api.rvpAPI.model.Usuario;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.fixtures.EnderecoFixture;
import rarolabs.com.br.rvp.fixtures.RedeFixture;
import rarolabs.com.br.rvp.fixtures.UsuarioFixture;
import rarolabs.com.br.rvp.services.BackendExpection;
import rarolabs.com.br.rvp.services.BackendServices;

/**
* Created by rodrigosol on 12/31/14.
*/
public class LocalizarRedesTest  extends ApplicationTestCase<Application> {

    private BackendServices service;
    private GoogleAccountCredential rodrigoSol;
    private GoogleAccountCredential admin;
    private GoogleAccountCredential carol;

    public LocalizarRedesTest() {
        super(Application.class);

    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        rodrigoSol = GoogleAccountCredential.usingAudience(getContext(), Constants.OAUTH_CLIENT_ID);
        rodrigoSol.setSelectedAccountName("rodrigosol@gmail.com");

        admin = GoogleAccountCredential.usingAudience(getContext(), Constants.OAUTH_CLIENT_ID);
        admin.setSelectedAccountName("admin@rarolabs.com.br");

        carol = GoogleAccountCredential.usingAudience(getContext(), Constants.OAUTH_CLIENT_ID);
        carol.setSelectedAccountName("acarolsm@gmail.com");
        this.service = new BackendServices(getContext(),rodrigoSol,Constants.BACKEND_URL);

    }


    public void testMinhasRedes() throws Exception {
        service.cleanForTesting();
        Usuario rodrigo = service.novoUsuario(UsuarioFixture.getRodrigoSol());
        Rede rede1 = service.novaRede("Amigos do Comiteco", EnderecoFixture.getEnderecoRaro());
        Rede rede2 = service.novaRede("Amigos do Nectar", EnderecoFixture.getEnderecoCasa());

        service.setCredential(carol);
        Usuario ramon = service.novoUsuario(UsuarioFixture.getRamonSetragni());
        Rede rede3 = service.novaRede("Amigos do Praça", EnderecoFixture.getEnderecoPraca());

        service.setCredential(rodrigoSol);
        service.solicitarAssociacao(rede3.getId(), EnderecoFixture.getEnderecoPraca());

        service.setCredential(carol);
        service.aprovarAssociacao(service.solicitacoesPendentes(rede3.getId()).getItems().get(0).getId());

        service.setCredential(rodrigoSol);
        MembroCollection minhasRedes = service.minhasRedes();

        assertEquals(3,minhasRedes.getItems().size());
    }


    public void testBuscarRedesProximas() throws Exception {
        service.cleanForTesting();

        Endereco raro = EnderecoFixture.getEnderecoRaro();
        Endereco casa = EnderecoFixture.getEnderecoCasa();
        Endereco praca = EnderecoFixture.getEnderecoPraca();
        Endereco escola = EnderecoFixture.getEnderecoEscola();

        Rede rede1 = RedeFixture.novaRede1(service);

        //Busca exata nas mesma cordenadas da rede
        GeoqueryResponderCollection result = service.buscarRedesProximas(raro.getLatitude(), raro.getLongitude(), 0.00);
        assertEquals(1,result.size());
        assertEquals("0.0",result.getItems().get(0).getDistance().toString());

//        //Não é possivel confiar nos resultados locais
//        //Busca a raro da praca da bandeira se margem
//        result = service.buscarRedesProximas(praca.getLatitude(), praca.getLongitude(), 0.00);
//        assertEquals(0,result.size());
//        //Busca a raro da praca da bandeira com margen de 200m
//        result = service.buscarRedesProximas(praca.getLatitude(), praca.getLongitude(), 200.00);
//        assertEquals(0,result.size());
//
//        //Busca a raro da praca da bandeira com margen de 300m
//        result = BackendServices.buscarRedesProximas(praca.getLatitude(), praca.getLongitude(), 300.00);
//        assertEquals(1, result.size());
//
//        Rede rede2 = RedeFixture.novaRede2();
//        //Agora existem duas redes, uma na raro e outra na escola, se busca da praca com 300 metros somente
//        //a rede da raro pode aparecer
//        result = BackendServices.buscarRedesProximas(praca.getLatitude(), praca.getLongitude(), 250.00);
//
//        assertEquals(1, result.size());
//        result = BackendServices.buscarRedesProximas(praca.getLatitude(), praca.getLongitude(), 500.00);
//        assertEquals(2, result.size());
//
//        //Se a procurar parte do endereco casa, que esta mais distante, o retorno deve ser 0
//        result = BackendServices.buscarRedesProximas(casa.getLatitude(), casa.getLongitude(), 500.00);
//        assertEquals(0,result.size());
//
//        Usuario lesio = BackendServices.novoUsuario(UsuarioFixture.getLesioPinheiro());
//        BackendServices.solicitarAssociacao(rede1.getId(),lesio.getId(),EnderecoFixture.getEnderecoCasa());
//        Membro lesioMembro = BackendServices.solicitacoesPendentes(rede1.getId()).getItems().get(0);
//        BackendServices.aprovarAssociacao(lesioMembro.getId());
//
//        //Agora, ja que houve a aprovacao de membro com endereco casa, uma busca apartir de
//        //casa deve retornar a rede Raro
//        result = BackendServices.buscarRedesProximas(casa.getLatitude(), casa.getLongitude(), 500.00);
//        assertEquals(1,result.size());
//
//        //Se esse membro for inativa, a pesquisa deve voltar a retornar 0
//        BackendServices.inativarVizinho(lesioMembro.getId());
//        result = BackendServices.buscarRedesProximas(casa.getLatitude(), casa.getLongitude(), 500.00);
//        assertEquals(0, result.size());


    }
}