package rarolabs.com.br.rvp.fixtures;

import br.com.rarolabs.rvp.api.rvpAPI.model.Endereco;

/**
 * Created by rodrigosol on 12/31/14.
 */
public class EnderecoFixture {
    //Distancias entre os enderecos
    //http://www.tytai.com/gmap/distance/
    //********************************************
    //|      |Raro    |Casa    |Praca   |Escola  |
    //|Raro  |0       |11122.93|244.74  |459.66  |
    //|Casa  |11122.93|0       |11325.51|11084.32|
    //|Praca |244.74  |11325.51|0       |645.95  |
    //|Escola|645.95  |11084.32|645.95  | 0      |
    //********************************************

    public static Endereco getEnderecoRaro(){
        Endereco e = new Endereco();
        e.setIdentificacao("Raro");
        e.setDescricao("Rua Paul Bouthiler, 341");
        e.setLocalidade("Belo Horizonte, MG");
        e.setLatitude(-43.921484);
        e.setLongitude(-19.950871);
        return e;


    }

    public static Endereco getEnderecoCasa() {
        Endereco e = new Endereco();
        e.setIdentificacao("Casa");
        e.setDescricao("Rua Americo Magalhaes 690/102, Barreiro");
        e.setLocalidade("Belo Horizonte, MG");
        e.setLatitude(-44.022732);
        e.setLongitude(-19.981698);
        return e;

    }

    public static Endereco getEnderecoPraca() {
        Endereco e = new Endereco();
        e.setIdentificacao("Casa");
        e.setDescricao("Praça da Bandeira");
        e.setLocalidade("Belo Horizonte, MG");
        e.setLatitude(-43.920057);
        e.setLongitude(-19.949126);
        return e;

    }

    public static Endereco getEnderecoEscola() {
        Endereco e = new Endereco();
        e.setIdentificacao("Casa");
        e.setDescricao("Escola Guinard");
        e.setLocalidade("Belo Horizonte, MG");
        e.setLatitude(-43.920572);
        e.setLongitude(-19.954915);
        return e;

    }
}
