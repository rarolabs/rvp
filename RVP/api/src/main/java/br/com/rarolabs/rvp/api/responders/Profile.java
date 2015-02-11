package br.com.rarolabs.rvp.api.responders;

import br.com.rarolabs.rvp.api.models.Membro;

/**
 * Created by rodrigosol on 2/10/15.
 */
public class Profile {
    private Long membroId;
    private String usuarioId;
    private String redeId;
    private String nome;
    private String avatar;
    private String avatarBlur;
    private String endereco;
    private String telefoneFixo;
    private String telefoneCelular;
    private Membro.Papel papel;
    private Membro.Papel papelDoVisualizado;
    private Membro.Status status;


    public Long getMembroId() {
        return membroId;
    }

    public void setMembroId(Long membroId) {
        this.membroId = membroId;
    }

    public String getRedeId() {
        return redeId;
    }

    public void setRedeId(String redeId) {
        this.redeId = redeId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatarBlur() {
        return avatarBlur;
    }

    public void setAvatarBlur(String avatarBlur) {
        this.avatarBlur = avatarBlur;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefoneFixo() {
        return telefoneFixo;
    }

    public void setTelefoneFixo(String telefoneFixo) {
        this.telefoneFixo = telefoneFixo;
    }

    public String getTelefoneCelular() {
        return telefoneCelular;
    }

    public void setTelefoneCelular(String telefoneCelular) {
        this.telefoneCelular = telefoneCelular;
    }

    public Membro.Status getStatus() {
        return status;
    }

    public void setStatus(Membro.Status status) {
        this.status = status;
    }

    public Membro.Papel getPapel() {
        return papel;
    }

    public void setPapel(Membro.Papel papel) {
        this.papel = papel;
    }

    public Membro.Papel getPapelDoVisualizado() {
        return papelDoVisualizado;
    }

    public void setPapelDoVisualizado(Membro.Papel papelDoVisualizado) {
        this.papelDoVisualizado = papelDoVisualizado;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "membroId=" + membroId +
                ", usuarioId='" + usuarioId + '\'' +
                ", redeId='" + redeId + '\'' +
                ", nome='" + nome + '\'' +
                ", avatar='" + avatar + '\'' +
                ", avatarBlur='" + avatarBlur + '\'' +
                ", endereco='" + endereco + '\'' +
                ", telefoneFixo='" + telefoneFixo + '\'' +
                ", telefoneCelular='" + telefoneCelular + '\'' +
                ", papelDoVisualizado=" + papelDoVisualizado +
                '}';
    }
}
