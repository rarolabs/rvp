package br.com.rarolabs.rvp.api.servlet;

// file Serve.java

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.rarolabs.rvp.api.models.Alerta;
import br.com.rarolabs.rvp.api.service.NotificacaoService;
import br.com.rarolabs.rvp.api.service.OfyService;

public class PushAlertaServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        String key = req.getParameter("key");
        System.out.println("Key:" + key);
        Alerta alerta = OfyService.ofy().load().type(Alerta.class).id(new Long(key).longValue()).now();
        if(alerta!=null){
            NotificacaoService.enviarAlerta(alerta);
        }

    }


}
