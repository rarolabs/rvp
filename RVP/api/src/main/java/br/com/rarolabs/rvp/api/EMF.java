package br.com.rarolabs.rvp.api;

import com.googlecode.objectify.ObjectifyService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.rarolabs.rvp.api.models.Rede;


public final class EMF {
    private static final EntityManagerFactory emfInstance =
            Persistence.createEntityManagerFactory("default");

    private EMF() {}

    public static EntityManagerFactory get() {

        return emfInstance;
    }

}