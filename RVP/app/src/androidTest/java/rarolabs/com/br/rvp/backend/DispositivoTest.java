package rarolabs.com.br.rvp.backend;

import android.app.Application;
import android.os.Build;
import android.test.ApplicationTestCase;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import br.com.rarolabs.rvp.api.rvpAPI.model.Rede;
import br.com.rarolabs.rvp.api.rvpAPI.model.Usuario;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.fixtures.RedeFixture;
import rarolabs.com.br.rvp.fixtures.UsuarioFixture;
import rarolabs.com.br.rvp.services.BackendExpection;
import rarolabs.com.br.rvp.services.BackendServices;

/**
 * Created by rodrigosol on 1/3/15.
 */
public class DispositivoTest extends ApplicationTestCase<Application> {
    private BackendServices service;
    private GoogleAccountCredential credential;

    public DispositivoTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        credential = GoogleAccountCredential.usingAudience(getContext(), Constants.OAUTH_CLIENT_ID);
        credential.setSelectedAccountName("rodrigosol@gmail.com");
    }

    public void testRegistrar() throws IOException, BackendExpection {
        this.service = new BackendServices(getContext(),credential,Constants.BACKEND_URL);
        service.cleanForTesting();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(getContext());
        String regId = gcm.register(Constants.PROJECT_NUMBER);
        service.registrarDispositivo(regId,"Android", String.valueOf(Build.VERSION.SDK_INT));


    }

    public void testDesregistrar() throws IOException, BackendExpection {
        this.service = new BackendServices(getContext(),credential,Constants.BACKEND_URL);
        service.cleanForTesting();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(getContext());
        String regId = gcm.register(Constants.PROJECT_NUMBER);
        service.desregistrarDispositivo(regId);
    }


}
