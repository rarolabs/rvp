package br.com.rarolabs.rvp.api.responders;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import br.com.rarolabs.rvp.api.models.Membro;

/**
 * Created by rodrigosol on 1/28/15.
 */
public class RedeDetalhada {
    private final Collection<Membro> membros;
    private Long redeId;
    private Long membroId;
    private String nomeRede;
    private String nomeAdministrador;
    private String avatarAdministrador;
    private String localizacao;

    private Integer quantidadeMembros;
    private Date ultimaAtividade;

    public Membro.Status getStatus() {
        return status;
    }

    public void setStatus(Membro.Status status) {
        this.status = status;
    }

    private Membro.Status status;

    public RedeDetalhada(Membro m) {
        this.redeId = m.getRedeId();
        this.membroId = m.getId();
        this.nomeRede = m.getNomeRede();
        this.nomeAdministrador = m.getRede().getDono().getUsuario().getNome();
        this.avatarAdministrador = m.getRede().getDono().getUsuario().getAvatar();
        this.quantidadeMembros = m.getRede().getMembros().size();
        this.setUltimaAtividade(new Date());
        this.membros = m.getRede().membrosAtivos();
        this.status = m.getStatus();
        this.localizacao = m.getRede().getLocalizacao();

        System.out.println("Avatar:" + m.getRede().getDono().getUsuario());
        System.out.println("Avatar:" + this.nomeAdministrador);
        System.out.println("Avatar:" + this.avatarAdministrador);
        System.out.println("Avatar:" + this.toString());

    }

    public Long getRedeId() {
        return redeId;
    }

    public void setRedeId(Long redeId) {
        this.redeId = redeId;
    }

    public Long getMembroId() {
        return membroId;
    }

    public void setMembroId(Long membroId) {
        this.membroId = membroId;
    }

    public String getNomeRede() {
        return nomeRede;
    }

    public void setNomeRede(String nomeRede) {
        this.nomeRede = nomeRede;
    }

    public String getNomeAdministrador() {
        return nomeAdministrador;
    }

    public void setNomeAdministrador(String nomeAdministrador) {
        this.nomeAdministrador = nomeAdministrador;
    }

    public String getAvatarAdministrador() {
        return avatarAdministrador;
    }

    public void setAvatarAdministrador(String avatarAdministrador) {
        this.avatarAdministrador = avatarAdministrador;
    }

    public Integer getQuantidadeMembros() {
        return quantidadeMembros;
    }

    public void setQuantidadeMembros(Integer quantidadeMembros) {
        this.quantidadeMembros = quantidadeMembros;
    }

    public Date getUltimaAtividade() {
        return ultimaAtividade;
    }

    public void setUltimaAtividade(Date ultimaAtividade) {
        this.ultimaAtividade = ultimaAtividade;
    }

    public Collection<Membro> getMembros() {
        return membros;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    @Override
    public String toString() {
        return "RedeDetalhada{" +
                "membros=" + membros +
                ", redeId=" + redeId +
                ", membroId=" + membroId +
                ", nomeRede='" + nomeRede + '\'' +
                ", nomeAdministrador='" + nomeAdministrador + '\'' +
                ", avatarAdministrador='" + avatarAdministrador + '\'' +
                ", quantidadeMembros=" + quantidadeMembros +
                ", ultimaAtividade=" + ultimaAtividade +
                ", status=" + status +
                '}';
    }
}
