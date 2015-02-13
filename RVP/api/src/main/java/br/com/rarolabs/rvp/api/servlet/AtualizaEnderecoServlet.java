package br.com.rarolabs.rvp.api.servlet;

// file Serve.java

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.rarolabs.rvp.api.models.Rede;
import br.com.rarolabs.rvp.api.models.Usuario;
import br.com.rarolabs.rvp.api.service.GeocodingService;
import br.com.rarolabs.rvp.api.service.OfyService;

public class AtualizaEnderecoServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        String key = req.getParameter("key");
        System.out.println("Key:" + key);
        Rede rede = OfyService.ofy().load().type(Rede.class).id(new Long(key).longValue()).now();
        if(rede!=null){
            rede.setLocalizacao(GeocodingService.getGeocoding(new BigDecimal(rede.getLatitude()), new BigDecimal(rede.getLongitude())));
            OfyService.ofy().cache(false).save().entity(rede).now();
            System.out.println("Endereco da Rede atualizada com sucesso");
        }

    }


}
