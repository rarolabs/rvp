package rarolabs.com.br.rvp.models;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by rodrigosol on 3/30/15.
 */
public class Mensagem extends SugarRecord<Notificacao> {

    private String nome;
    private Date data;
    private Date dataInicial;
    private Date dataFinal;
    private String texto;
    private Notificacao notificacao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Notificacao getNotificacao() {
        return notificacao;
    }

    public void setNotificacao(Notificacao notificacao) {
        this.notificacao = notificacao;
    }
}
