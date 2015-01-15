package rarolabs.com.br.rvp.utils;

import java.text.DecimalFormat;

/**
 * Created by rodrigosol on 1/15/15.
 */
public class Formarter {
    public static String distanciaHumana(Double distance){
        if(distance < 1000){
            return new DecimalFormat("#\nm").format(distance);
        }else{
            return String.format("%.1f\nkm", distance/1000.0);
        }
    }


}
