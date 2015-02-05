package br.com.rarolabs.rvp.api.responders;

/**
 * Created by rodrigosol on 2/5/15.
 */
public class StringResponse {
    private String value;

    public StringResponse(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
