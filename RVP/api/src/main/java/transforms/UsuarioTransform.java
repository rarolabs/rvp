package transforms;

import com.google.api.server.spi.config.Transformer;

import br.com.rarolabs.rvp.api.models.Usuario;

/**
 * Created by rodrigosol on 12/22/14.
 */
public class UsuarioTransform implements Transformer<Usuario, String> {

    @Override
    public String transformTo(Usuario usuario) {
        return usuario.getId() + "," +
               usuario.getNome() + "," +
               usuario.getEmail() + "," +
               usuario.getDddTelefoneCelular() + "," +
               usuario.getTelefoneCelular() + "," +
               usuario.getDddTelefoneFixo() + "," +
               usuario.getTelefoneFixo();


    }

    @Override
    public Usuario transformFrom(String s) {
        String[] fields = s.split(",");
        Usuario u =  new Usuario();

        if(fields[0].equals("")){
            u.setId(null);
        }else{
            u.setId(Long.valueOf(fields[0]));
        }

        u.setNome(fields[1]);
        u.setEmail(fields[2]);
        u.setDddTelefoneCelular(fields[3]);
        u.setTelefoneCelular(fields[4]);
        u.setDddTelefoneFixo(fields[5]);
        u.setTelefoneFixo(fields[6]);

        return u;
    }
}
