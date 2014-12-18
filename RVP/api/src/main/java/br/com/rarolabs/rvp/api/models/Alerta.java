package br.com.rarolabs.rvp.api.models;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.Date;

/**
 * Created by rodrigosol on 12/18/14.
 */
@Entity
public class Alerta {

    public enum Tipo {VEICULO_SUSPEITO,PESSOA_SUSPEITA,
                      PANICO,PORTAO_ABERTO,SUSPEITA_DE_INVACAO,AUSENCIA, MUDANCA}

    //Identificacao do Alerta
    @Id
    private String id;
    private Tipo tipo;
    private Date data;

    //Relacionamentos
    private Key<Rede> rede;
    private Key<Membro> membro;
    private Key<Mensagem> mensagem;

    //Campos adicionais dependendo do contexto
    private String descricao;
    private String local;
    private String veiculo;
    private String marca;
    private String modelo;
    private String cor;
    private String quantidade;
    private String caracteristicas;
    private Date de;
    private Date ate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Key<Rede> getRede() {
        return rede;
    }

    public void setRede(Key<Rede> rede) {
        this.rede = rede;
    }

    public Key<Membro> getMembro() {
        return membro;
    }

    public void setMembro(Key<Membro> membro) {
        this.membro = membro;
    }

    public Key<Mensagem> getMensagem() {
        return mensagem;
    }

    public void setMensagem(Key<Mensagem> mensagem) {
        this.mensagem = mensagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public Date getDe() {
        return de;
    }

    public void setDe(Date de) {
        this.de = de;
    }

    public Date getAte() {
        return ate;
    }

    public void setAte(Date ate) {
        this.ate = ate;
    }
}
