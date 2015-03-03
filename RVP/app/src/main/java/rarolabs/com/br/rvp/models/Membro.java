package rarolabs.com.br.rvp.models;

import com.google.api.client.util.DateTime;
import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by rodrigosol on 3/3/15.
 */
public class Membro extends SugarRecord<Membro> {

    private String visibilidadeTelefoneFixo;
    private String visibilidadeTelefoneCelular;
    private String visibilidadeEndereco;
    private Double latitude;
    private Double longitude;
    private Long enderecoId;
    private Date dataAssociacao;
    private String papel;
    private String usuarioId;
    private String status;
    private Long membroId;
    private Rede rede;

    public Membro(){}
    public Membro(br.com.rarolabs.rvp.api.rvpAPI.model.Membro membro, Rede rede){
        this.membroId = membro.getId();
        this.status = membro.getStatus();
        this.usuarioId = membro.getUsuarioId();
        this.papel = membro.getPapel();
        if(membro.getDataAssociacao()!=null) {
            this.dataAssociacao = new Date(membro.getDataAssociacao().getValue());
        }
        this.enderecoId = membro.getEnderecoId();
        this.longitude = membro.getLongitude();
        this.latitude = membro.getLatitude();
        this.visibilidadeEndereco = membro.getVisibilidadeEndereco();
        this.visibilidadeTelefoneCelular = membro.getVisibilidadeTelefoneCelular();
        this.visibilidadeTelefoneFixo = membro.getVisibilidadeTelefoneFixo();
    }

    public String getVisibilidadeTelefoneFixo() {
        return visibilidadeTelefoneFixo;
    }

    public void setVisibilidadeTelefoneFixo(String visibilidadeTelefoneFixo) {
        this.visibilidadeTelefoneFixo = visibilidadeTelefoneFixo;
    }

    public String getVisibilidadeTelefoneCelular() {
        return visibilidadeTelefoneCelular;
    }

    public void setVisibilidadeTelefoneCelular(String visibilidadeTelefoneCelular) {
        this.visibilidadeTelefoneCelular = visibilidadeTelefoneCelular;
    }

    public String getVisibilidadeEndereco() {
        return visibilidadeEndereco;
    }

    public void setVisibilidadeEndereco(String visibilidadeEndereco) {
        this.visibilidadeEndereco = visibilidadeEndereco;
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

    public Long getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(Long enderecoId) {
        this.enderecoId = enderecoId;
    }


    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getMembroId() {
        return membroId;
    }

    public void setMembroId(Long membroId) {
        this.membroId = membroId;
    }

    public Rede getRede() {
        return rede;
    }

    public void setRede(Rede rede) {
        this.rede = rede;
    }

    public Date getDataAssociacao() {
        return dataAssociacao;
    }

    public void setDataAssociacao(Date dataAssociacao) {
        this.dataAssociacao = dataAssociacao;
    }
}
