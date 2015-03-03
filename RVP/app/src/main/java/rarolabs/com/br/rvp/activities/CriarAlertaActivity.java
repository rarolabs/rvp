package rarolabs.com.br.rvp.activities;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.util.DateTime;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import br.com.rarolabs.rvp.api.rvpAPI.model.Alerta;
import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.fragments.DatePickerDialogFragment;
import rarolabs.com.br.rvp.fragments.NotificacaoDialogFragment;
import rarolabs.com.br.rvp.models.Rede;
import rarolabs.com.br.rvp.services.tasks.EnviarAlertaAsyncTask;

public class CriarAlertaActivity extends ActionBarActivity implements DatePickerDialog.OnDateSetListener, DatePickerDialogFragment.NoticeDialogListener {

    private static HashMap<Integer,AlertaEsquema> esquemas = new HashMap<Integer,AlertaEsquema>();

    static{
        esquemas.put(R.id.pessoa_suspeita,new AlertaEsquema("PESSOA_SUSPEITA",
                     R.string.pessoa_suspeita,
                     R.drawable.ic_alertas_preencher_header_pessoa,
                     R.string.pessoa_suspeita_header,
                     R.color.pessoa_suspeita,
                     R.color.pessoa_suspeita_overlay,0,0,R.string.detalhes_pessoas_suspeita));

        esquemas.put(R.id.veiculo_suspeito,new AlertaEsquema("VEICULO_SUSPEITO",
                     R.string.veiculo_suspeito,
                     R.drawable.ic_alertas_preencher_header_veiculo,
                     R.string.veiculo_suspeito_header,
                     R.color.veiculo_suspeito,R.color.veiculo_suspeito_overlay,0,0,R.string.detalhes_veiculo_suspeito));

        esquemas.put(R.id.ausencia,new AlertaEsquema("AUSENCIA",
                     R.string.ausencia,
                     R.drawable.ic_alertas_preencher_header_ausencia,
                     R.string.ausencia_header,R.color.ausencia,
                     R.color.ausencia_overlay,R.string.data_ausencia_inicio,R.string.data_ausencia_fim,R.string.detalhes_ausencia));

        esquemas.put(R.id.mudanca,new AlertaEsquema("MUDANCA",
                     R.string.mudanca,
                     R.drawable.ic_alertas_preencher_header_mudanca,
                     R.string.mudanca_header,
                     R.color.mudanca,R.color.mudanca_overlay, R.string.data_mudanca,0,R.string.detalhes_mudanca));

        esquemas.put(R.id.incendio,new AlertaEsquema("INCENDIO",
                     R.string.incendio,
                     R.drawable.ic_alertas_preencher_header_incendio,
                     R.string.incendio_header,R.color.incendio,
                     R.color.incendio_overlay,
                     0,0,R.string.detalhes_incendio));

        esquemas.put(R.id.emergencia,new AlertaEsquema("EMERGENCIA_POLICIAL",
                     R.string.emergencia,
                     R.drawable.ic_alertas_preencher_header_policia,
                     R.string.emergencia_header,
                     R.color.emergencia,
                     R.color.emergencia_overlay,0,0,R.string.detalhes_emergecia));
    }

    private EditText detalhes;
    private String tipo;
    private DateTime dataDe;
    private DateTime dataAte;
    private Long membroId;
    private Long redeId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_alerta);
        ActionBar mActionBar = getSupportActionBar();
        int id = getIntent().getExtras().getInt(Constants.EXTRA_TIPO_ALERTA, 0);
        AlertaEsquema esquema = esquemas.get(id);
        if(esquema!=null) {
            tipo = esquema.getType();
            mActionBar.setTitle(getString(esquema.getTitle()));
            mActionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(esquema.getActionBarColor())));
            mActionBar.setDisplayShowTitleEnabled(false);
            mActionBar.setDisplayShowTitleEnabled(true);


            ((ImageView) findViewById(R.id.header_icon)).setImageResource(esquema.getHeaderIcon());
            ((TextView) findViewById(R.id.header_text)).setText(getString(esquema.getHeaderString()));
            ((LinearLayout) findViewById(R.id.header_container)).setBackgroundColor(getResources().getColor(esquema.getContainerColor()));
            if(esquema.getLabelData1()!=0){
                ((EditText) findViewById(R.id.data_1)).setHint(getString(esquema.getLabelData1()));;
                findViewById(R.id.data_1_container).setVisibility(View.VISIBLE);

                if(esquema.getLabelData2()!=0){
                    ((EditText) findViewById(R.id.data_2)).setHint(getString(esquema.getLabelData2()));
                    findViewById(R.id.data_2_container).setVisibility(View.VISIBLE);
                }

            }

            ((EditText) findViewById(R.id.detalhes)).setHint(getString(esquema.getLabelDescricao()));
        }else{
            Log.d("Alerta", "Não encontrado:" + id);
        }

        detalhes = (EditText) findViewById(R.id.detalhes);
        Rede r = Rede.listAll(Rede.class).get(0);
        redeId = r.getRedeId();
        membroId = r.getMembroId();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_criar_alerta, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){

            case  R.id.action_confirmar:
                enviarAlerta();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void enviarAlerta() {
        Alerta alerta = new Alerta();
        alerta.setDescricao(detalhes.getText().toString());
        alerta.setTipo(tipo);
        alerta.setDe(dataDe);
        alerta.setAte(dataAte);
        alerta.setMembroId(membroId);
        alerta.setRedeId(redeId);

        new EnviarAlertaAsyncTask(this).execute(alerta);
        Toast.makeText(this,R.string.enviando_alerta,Toast.LENGTH_SHORT).show();
        finish();
    }


    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerDialogFragment();
        newFragment.show(getFragmentManager(), "DATE_PICKER");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        Calendar cal = Calendar.getInstance();
        cal.set(year, monthOfYear, dayOfMonth);
        DateTime data = new DateTime(cal.getTime());
        if(view.getId() == R.id.data_1){
            dataDe = data;
        }else if(view.getId() == R.id.data_2){
            dataAte = data;
        }
    }

    @Override
    public void returnFromDialog(long notificacaoId) {

    }
}


class AlertaEsquema {
    private String type;
    private int headerIcon;
    private int headerString;
    private int actionBarColor;
    private int containerColor;
    private int title;
    private int labelData1;
    private int labelData2;
    private int labelDescricao;

    public AlertaEsquema(String type,int title,int headerIcon, int headerString, int actionBarColor, int containerColor,int labelData1, int labelData2, int labelDescricao) {
        this.type = type;
        this.headerIcon = headerIcon;
        this.headerString = headerString;
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
}