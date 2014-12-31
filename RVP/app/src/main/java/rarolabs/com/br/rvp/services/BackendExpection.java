package rarolabs.com.br.rvp.services;

import android.util.Log;

import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.json.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by rodrigosol on 12/31/14.
 */
public class BackendExpection extends Throwable {


    private String code;
    private String descricao;

    public BackendExpection(Exception e) {
        super(e);
        parseError(e);
    }



    private void parseError(Exception e) {
        JsonParser json = new AndroidJsonFactory().createJsonParser(e.getMessage());
        JSONObject jObject = new JSONObject();
        try {
            json.parse(jObject);
            this.code = jObject.getString("code");
            this.descricao = jObject.getJSONArray("errors").getJSONObject(0).getString("message");
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
