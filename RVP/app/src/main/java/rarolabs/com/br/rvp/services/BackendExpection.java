package rarolabs.com.br.rvp.services;

import android.util.Log;

import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAuthIOException;
import com.google.api.client.json.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import rarolabs.com.br.rvp.R;

/**
 * Created by rodrigosol on 12/31/14.
 */
public class BackendExpection extends Exception {

    public static enum Tipo {VALIDACAO,REDE,AUTENTICACAO,DESCONHECIDO,AUTORIZACAO};

    private Tipo tipo;
    private String code;
    private String descricao;

    public BackendExpection(Exception e){
        super(e);
        parseException(e);
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
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

    private void parseException(Exception e){

        Log.d("EXCEPTION",e.getClass().toString());
        if(e instanceof SocketTimeoutException) {
            this.setTipo(Tipo.REDE);
            this.setDescricao("Não foi possível ler dados do servidor");
        }else if(e instanceof UnknownHostException) {
            this.setTipo(Tipo.REDE);
            this.setDescricao("Servidor não está disponível");
        }else if(e instanceof GoogleAuthIOException) {
            this.setTipo(Tipo.AUTENTICACAO);
            this.setDescricao("Não foi possível autenticar o usuário");
        }else {

            String msg = e.getMessage();
            e.printStackTrace();

            try {
                JSONObject jObject = new JSONObject(msg.substring(msg.indexOf("{"), msg.lastIndexOf("}") + 1));
                this.descricao = jObject.getJSONArray("errors").getJSONObject(0).getString("message");
                this.descricao = this.descricao.replace("com.google.appengine.api.oauth.OAuthRequestException: ","");
                this.code = jObject.getString("code");
                this.tipo = Tipo.VALIDACAO;
            } catch (Exception e1) {
                this.setTipo(Tipo.DESCONHECIDO);
                this.setDescricao("Não foi possível completar essa ação. Verifique sua conexão com a Internet");

            }
        }
    }

}
