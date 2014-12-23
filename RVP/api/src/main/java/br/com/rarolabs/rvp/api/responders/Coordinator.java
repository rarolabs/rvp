package br.com.rarolabs.rvp.api.responders;

/**
 * Created by rodrigosol on 12/23/14.
 */
public class Coordinator {
    private Double latitude;
    private Double longitude;

    public Coordinator(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
