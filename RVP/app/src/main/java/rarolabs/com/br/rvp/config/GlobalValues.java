package rarolabs.com.br.rvp.config;

import android.app.Application;
import android.os.Bundle;

/**
 * Created by rodrigosol on 1/28/15.
 */
public class GlobalValues extends Application {
    public Bundle getUltimaRede() {
        return ultimaRede;
    }

    public void setUltimaRede(Bundle ultimaRede) {
        this.ultimaRede = ultimaRede;
    }

    private Bundle ultimaRede;

}


