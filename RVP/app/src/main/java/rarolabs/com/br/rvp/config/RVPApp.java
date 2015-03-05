package rarolabs.com.br.rvp.config;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

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
    private static float density;

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
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics ();
        display.getMetrics(outMetrics);
        density = outMetrics.density;

    }

    public static Float getDesinty(){
        return density;
    }

}


