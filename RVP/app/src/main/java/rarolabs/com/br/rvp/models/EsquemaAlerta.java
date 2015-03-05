package rarolabs.com.br.rvp.models;

import java.util.HashMap;

import rarolabs.com.br.rvp.R;

/**
 * Created by rodrigosol on 3/4/15.
 */
public class EsquemaAlerta {

    private static HashMap<Integer, EsquemaAlerta> esquemasById = new HashMap<Integer, EsquemaAlerta>();
    private static HashMap<String, EsquemaAlerta> esquemasByType = new HashMap<String, EsquemaAlerta>();

    static {

        EsquemaAlerta pessoaSuspeita = new EsquemaAlerta("PESSOA_SUSPEITA",
                R.string.pessoa_suspeita,
                R.drawable.ic_alertas_preencher_header_pessoa,
                R.string.pessoa_suspeita_header,
                R.string.pessoa_suspeita_notificacao,
                R.color.pessoa_suspeita,
                R.color.pessoa_suspeita_overlay, 0, 0, R.string.detalhes_pessoas_suspeita);

        esquemasById.put(R.id.pessoa_suspeita, pessoaSuspeita);
        esquemasByType.put("PESSOA_SUSPEITA", pessoaSuspeita);

        EsquemaAlerta veiculoSuspeito = new EsquemaAlerta("VEICULO_SUSPEITO",
                R.string.veiculo_suspeito,
                R.drawable.ic_alertas_preencher_header_veiculo,
                R.string.veiculo_suspeito_header,
                R.string.veiculo_suspeito_notificacao,
                R.color.veiculo_suspeito, R.color.veiculo_suspeito_overlay, 0, 0, R.string.detalhes_veiculo_suspeito);

        esquemasById.put(R.id.veiculo_suspeito, veiculoSuspeito);
        esquemasByType.put("VEICULO_SUSPEITO", veiculoSuspeito);

        EsquemaAlerta ausencia = new EsquemaAlerta("AUSENCIA",
                R.string.ausencia,
                R.drawable.ic_alertas_preencher_header_ausencia,
                R.string.ausencia_header,
                R.string.ausencia_notificacao, R.color.ausencia,
                R.color.ausencia_overlay, R.string.data_ausencia_inicio, R.string.data_ausencia_fim, R.string.detalhes_ausencia);

        esquemasById.put(R.id.ausencia, ausencia);
        esquemasByType.put("AUSENCIA", ausencia);

        EsquemaAlerta mudanca = new EsquemaAlerta("MUDANCA",
                R.string.mudanca,
                R.drawable.ic_alertas_preencher_header_mudanca,
                R.string.mudanca_header,
                R.string.mudanca_notificacao,
                R.color.mudanca, R.color.mudanca_overlay, R.string.data_mudanca, 0, R.string.detalhes_mudanca);

        esquemasById.put(R.id.mudanca, mudanca);
        esquemasByType.put("MUDANCA", mudanca);

        EsquemaAlerta incendio = new EsquemaAlerta("INCENDIO",
                R.string.incendio,
                R.drawable.ic_alertas_preencher_header_incendio,
                R.string.incendio_header,
                R.string.incendio_notificacao,
                R.color.incendio,
                R.color.incendio_overlay,
                0, 0, R.string.detalhes_incendio);

        esquemasById.put(R.id.incendio, incendio);
        esquemasByType.put("INCENDIO", incendio);

        EsquemaAlerta emergencia = new EsquemaAlerta("EMERGENCIA_POLICIAL",
                R.string.emergencia,
                R.drawable.ic_alertas_preencher_header_policia,
                R.string.emergencia_header,
                R.string.emergencia_notificacao,
                R.color.emergencia,
                R.color.emergencia_overlay, 0, 0, R.string.detalhes_emergecia);

        esquemasById.put(R.id.emergencia, emergencia);
        esquemasByType.put("EMERGENCIA_POLICIAL", emergencia);
    }

    private int notificationTitle;
    private String type;
    private int headerIcon;
    private int headerString;
    private int actionBarColor;
    private int containerColor;
    private int title;
    private int labelData1;
    private int labelData2;
    private int labelDescricao;

    public EsquemaAlerta(String type, int title, int headerIcon, int headerString, int notificationTitle, int actionBarColor, int containerColor, int labelData1, int labelData2, int labelDescricao) {
        this.type = type;
        this.headerIcon = headerIcon;
        this.headerString = headerString;
        this.notificationTitle = notificationTitle;
        this.actionBarColor = actionBarColor;
        this.containerColor = containerColor;
        this.title = title;
        this.labelData1 = labelData1;
        this.labelData2 = labelData2;
        this.labelDescricao = labelDescricao;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHeaderIcon() {
        return headerIcon;
    }

    public void setHeaderIcon(int headerIcon) {
        this.headerIcon = headerIcon;
    }

    public int getHeaderString() {
        return headerString;
    }

    public void setHeaderString(int headerString) {
        this.headerString = headerString;
    }

    public int getActionBarColor() {
        return actionBarColor;
    }

    public void setActionBarColor(int actionBarColor) {
        this.actionBarColor = actionBarColor;
    }

    public int getContainerColor() {
        return containerColor;
    }

    public void setContainerColor(int containerColor) {
        this.containerColor = containerColor;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getLabelData1() {
        return labelData1;
    }

    public void setLabelData1(int labelData1) {
        this.labelData1 = labelData1;
    }

    public int getLabelData2() {
        return labelData2;
    }

    public void setLabelData2(int labelData2) {
        this.labelData2 = labelData2;
    }

    public int getLabelDescricao() {
        return labelDescricao;
    }

    public void setLabelDescricao(int labelDescricao) {
        this.labelDescricao = labelDescricao;
    }

    public int getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(int notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public static EsquemaAlerta get(int id) {
        return esquemasById.get(id);
    }

    public static EsquemaAlerta get(String tipo) {
        return esquemasByType.get(tipo);
    }

}
