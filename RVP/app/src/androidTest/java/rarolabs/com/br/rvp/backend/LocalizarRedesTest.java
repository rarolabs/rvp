package rarolabs.com.br.rvp.backend;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.util.TreeSet;

import br.com.rarolabs.rvp.api.rvpAPI.model.Endereco;
import br.com.rarolabs.rvp.api.rvpAPI.model.GeoqueryResponderCollection;
import br.com.rarolabs.rvp.api.rvpAPI.model.Membro;
import br.com.rarolabs.rvp.api.rvpAPI.model.MembroCollection;
import br.com.rarolabs.rvp.api.rvpAPI.model.Rede;
import br.com.rarolabs.rvp.api.rvpAPI.model.Usuario;
import rarolabs.com.br.rvp.fixtures.EnderecoFixture;
import rarolabs.com.br.rvp.fixtures.RedeFixture;
import rarolabs.com.br.rvp.fixtures.UsuarioFixture;
import rarolabs.com.br.rvp.services.BackendExpection;
import rarolabs.com.br.rvp.services.BackendServices;

/**
 * Created by rodrigosol on 12/31/14.
 */
public class LocalizarRedesTest  extends ApplicationTestCase<Application> {

    public LocalizarRedesTest() {
        super(Application.class);

    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testMinhasRedes() throws BackendExpection {
        BackendServices.cleanForTesting();
        Usuario rodrigo = BackendServices.novoUsuario(UsuarioFixture.getRodrigoSol());
        Rede rede1 = BackendServices.novaRede("Amigos do Comiteco",rodrigo.getId(), EnderecoFixture.getEnderecoRaro());
        Rede rede2 = BackendServices.novaRede("Amigos do Nectar",rodrigo.getId(), EnderecoFixture.getEnderecoCasa());
        Usuario ramon = BackendServices.novoUsuario(UsuarioFixture.getRamonSetragni());
        Rede rede3 = BackendServices.novaRede("Amigos do Praça",ramon.getId(), EnderecoFixture.getEnderecoPraca());
        BackendServices.solicitarAssociacao(rede3.getId(),rodrigo.getId(), EnderecoFixture.getEnderecoPraca());
        BackendServices.aprovarAssociacao(BackendServices.solicitacoesPendentes(rede3.getId()).getItems().get(0).getId());
        MembroCollection minhasRedes = BackendServices.minhasRedes(rodrigo.getId());

        assertEquals(3,minhasRedes.getItems().size());
    }


    public void testBuscarRedesProximas() throws BackendExpection {
//        BackendServices.cleanForTesting();
//        Endereco raro = EnderecoFixture.getEnderecoRaro();
//        Endereco casa = EnderecoFixture.getEnderecoCasa();
//        Endereco praca = EnderecoFixture.getEnderecoPraca();
//        Endereco escola = EnderecoFixture.getEnderecoEscola();
//
//        Rede rede1 = RedeFixture.novaRede1();
//
//        //Busca exata nas mesma cordenadas da rede
//        GeoqueryResponderCollection result = BackendServices.buscarRedesProximas(raro.getLatitude(), raro.getLongitude(), 0.00);
//        assertEquals(1,result.size());
//        assertEquals("0.0",result.getItems().get(0).getDistance().toString());

        //Não é possivel confiar nos resultados locais
//        //Busca a raro da praca da bandeira se margem
//        result = BackendServices.buscarRedesProximas(praca.getLatitude(), praca.getLongitude(), 0.00);
//        assertEquals(0,result.size());
//        //Busca a raro da praca da bandeira com margen de 200m
//        result = BackendServices.buscarRedesProximas(praca.getLatitude(), praca.getLongitude(), 200.00);
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