package br.com.rarolabs.rvp.api.test.fixtures;

import br.com.rarolabs.rvp.api.models.Usuario;

/**
 * Created by rodrigosol on 12/31/14.
 */
public class UsuarioFixture {
    public static Usuario getRodrigoSol(){
        Usuario u = new Usuario();
        u.setId("rodrigosol@gmail.com");
        u.setNome("Rodrigo Sol");
        u.setDddTelefoneCelular("31");
        u.setTelefoneCelular("71718438");
        u.setDddTelefoneFixo("32");
        u.setTelefoneFixo("33844939");
        return u;
    }

    public static Usuario getLesioPinheiro() {
        Usuario u = new Usuario();
        u.setId("lesio.lap@gmail.com");
        u.setNome("Lesio Pinheiro");
        u.setDddTelefoneCelular("31");
        u.setTelefoneCelular("71718438");
        u.setDddTelefoneFixo("32");
        u.setTelefoneFixo("33844939");
        return u;

    }

    public static Usuario getRamonSetragni() {
        Usuario u = new Usuario();
        u.setId("ramon.setragni@gmail.com");
        u.setNome("Ramon Setragni");
        u.setDddTelefoneCelular("31");
        u.setTelefoneCelular("71718438");
        u.setDddTelefoneFixo("32");
        u.setTelefoneFixo("33844939");
        return u;

    }

    public static Usuario getLeonardoHerbert() {
        Usuario u = new Usuario();
        u.setId("leonardo.robotica@gmail.com");
        u.setNome("Leonardo Herbert");
        u.setDddTelefoneCelular("31");
        u.setTelefoneCelular("71718438");
        u.setDddTelefoneFixo("32");
        u.setTelefoneFixo("33844939");
        return u;

    }

}
