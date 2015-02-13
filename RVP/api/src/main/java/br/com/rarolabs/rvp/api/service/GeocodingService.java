package br.com.rarolabs.rvp.api.service;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderAddressComponent;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.GeocoderStatus;
import com.google.code.geocoder.model.LatLng;

import java.math.BigDecimal;

/**
 * Created by rodrigosol on 2/13/15.
 */
public class GeocodingService {
    public  static String getGeocoding(BigDecimal latitude, BigDecimal longitude) {
        final Geocoder geocoder = new Geocoder();
        GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setLocation(new LatLng(latitude, longitude)).setLanguage("en").getGeocoderRequest();
        GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);


        String bairro = "", cidade = "", estado = "";
        if (geocoderResponse.getStatus().equals(GeocoderStatus.OK)) {
            GeocoderResult result = geocoderResponse.getResults().get(0);
            for (GeocoderAddressComponent ac : result.getAddressComponents()) {
                if (ac.getTypes().contains("neighborhood")) {
                    bairro = ac.getLongName();
                } else if (ac.getTypes().contains("locality")) {
                    cidade = ac.getLongName();
                } else if (ac.getTypes().contains("administrative_area_level_1")) {
                    estado = ac.getShortName();
                }

            }
        }
        return bairro + ", " + cidade + " - " + estado;
    }
}
