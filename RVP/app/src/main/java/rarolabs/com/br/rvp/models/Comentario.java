package rarolabs.com.br.rvp.models;

import com.orm.SugarRecord;

/**
 * Created by rodrigosol on 3/4/15.
 */
public class Comentario extends SugarRecord<Comentario>{
    private Notificacao notificacao;
    private Long membroId;
    private String nome;
    private String avatar;
    private String avatarBlur;
    private Long data;
    private String texto;

    public Comentario(){}

    public Comentario(Notificacao notificacao, Long membroId, String nome, String avatar, String avatarBlur, Long data, String texto) {
        this.notificacao = notificacao;
        this.membroId = membroId;
        this.nome = nome;
        this.avatar = avatar;
        this.avatarBlur = avatarBlur;
        this.data = data;
        this.texto = texto;
    }

    public Notificacao getNotificacao() {
        return notificacao;
    }

    public void setNotificacao(Notificacao notificacao) {
        this.notificacao = notificacao;
    }

    public Long getMembroId() {
        return membroId;
    }

    public void setMembroId(Long membroId) {
        this.membroId = membroId;
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

    public Long getData() {
        return data;
    }

    public void setData(Long data) {
        this.data = data;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return "Comentario{" +
                "notificacao=" + notificacao.getId() +
                ", membroId=" + membroId +
                ", nome='" + nome + '\'' +
                ", avatar='" + avatar + '\'' +
                ", avatarBlur='" + avatarBlur + '\'' +
                ", data=" + data +
                ", texto='" + texto + '\'' +
                '}';
    }
}
