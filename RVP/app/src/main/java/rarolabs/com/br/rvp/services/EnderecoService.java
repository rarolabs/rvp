package rarolabs.com.br.rvp.services;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;

/**
 * Created by rodrigosol on 12/30/14.
 */
public class EnderecoService {
    public static List<Address> getLocationFromAddress(Context context,String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address = null;

        try {
            address =  coder.getFromLocationName(strAddress, 5);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }
}
