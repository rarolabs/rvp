package br.com.rarolabs.rvp.api.servlet;

// file Serve.java

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.rarolabs.rvp.api.models.Alerta;
import br.com.rarolabs.rvp.api.models.Mensagem;
import br.com.rarolabs.rvp.api.models.Rede;
import br.com.rarolabs.rvp.api.models.Usuario;
import br.com.rarolabs.rvp.api.service.GeocodingService;
import br.com.rarolabs.rvp.api.service.NotificacaoService;
import br.com.rarolabs.rvp.api.service.OfyService;

public class PushMessageServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        String key = req.getParameter("key");
        String user = req.getParameter("user");
        System.out.println("Key:" + key);
        System.out.println("User:" + key);
        Mensagem mensagem = OfyService.ofy().load().type(Mensagem.class).id(new Long(key).longValue()).now();
        Usuario usuario = OfyService.ofy().load().type(Usuario.class).id(user).now();
        if(mensagem!=null){
            NotificacaoService.enviarMensagem(mensagem,usuario);
        }

    }


}
