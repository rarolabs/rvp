package rarolabs.com.br.rvp.services;

import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.json.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by rodrigosol on 12/31/14.
 */
public class BackendExpection extends Exception {

    public BackendExpection(String e) {
        super(e);
    }

}
