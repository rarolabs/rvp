package br.com.rarolabs.rvp.api.servlet;

// file Serve.java

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.api.images.Transform;

import br.com.rarolabs.rvp.api.models.Usuario;
import br.com.rarolabs.rvp.api.service.OfyService;

public class UploadServlet extends HttpServlet {
    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        String usuarioID = req.getHeader("rvp-usuario-id");
        System.out.println("Usuario:" + usuarioID);

        List<BlobKey> blobs = blobstoreService.getUploads(req).get("profile");
        BlobKey profile = blobs.get(0);
        blobs = blobstoreService.getUploads(req).get("profile-blur");
        BlobKey profileBlur = blobs.get(0);



        ImagesService imagesService = ImagesServiceFactory.getImagesService();
        ServingUrlOptions opts =  ServingUrlOptions.Builder.withBlobKey(profile);
        String urlProfile = imagesService.getServingUrl(opts);

        ServingUrlOptions optsBlur =  ServingUrlOptions.Builder.withBlobKey(profileBlur);
        String urlProfileBlur = imagesService.getServingUrl(optsBlur);

        Usuario usuario = OfyService.ofy().load().type(Usuario.class).id(usuarioID).now();
        System.out.println("Usuario eh null:" + (usuario == null));
        System.out.println("URL produle:" + (urlProfile));
        usuario.setAvatar(urlProfile);
        usuario.setAvatarBlur(urlProfileBlur);
        OfyService.ofy().save().entity(usuario).now();

    }


}
