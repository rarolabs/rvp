package rarolabs.com.br.rvp.config;

import android.app.Application;
import android.os.Bundle;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import rarolabs.com.br.rvp.adapters.NotificacoesAdapter;

/**
 * Created by rodrigosol on 1/28/15.
 */
public class RVPApp extends com.orm.SugarApp {
    public Bundle getUltimaRede() {
        return ultimaRede;
    }

    public void setUltimaRede(Bundle ultimaRede) {
        this.ultimaRede = ultimaRede;
    }

    private Bundle ultimaRede;

    @Override
    public void onCreate() {
        super.onCreate();
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
        .cacheInMemory(true)
        .cacheOnDisk(true).build();


        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
        .defaultDisplayImageOptions(defaultOptions)
        .build();
        ImageLoader.getInstance().init(config);
    }
}


