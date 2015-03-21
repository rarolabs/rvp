package rarolabs.com.br.rvp.activities.alertas;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.util.DateTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.rarolabs.rvp.api.rvpAPI.model.Alerta;
import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.activities.Locable;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.listeners.GPSTracker;
import rarolabs.com.br.rvp.models.EsquemaAlerta;
import rarolabs.com.br.rvp.models.Rede;
import rarolabs.com.br.rvp.services.tasks.EnviarAlertaAsyncTask;

public class CriarAlertaActivity extends ActionBarActivity implements DatePickerDialog.OnDateSetListener,Locable {



    private EditText detalhes;
    private String tipo;
    private DateTime dataDe;
    private DateTime dataAte;
    private Long membroId;
    private Long redeId;
    private TextView data1;
    private TextView data2;
    private int currentDataView;
    private Location location;
    private GPSTracker tracker;
    private ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tracker = new GPSTracker(this, false);
        setContentView(R.layout.activity_criar_alerta);
        ActionBar mActionBar = getSupportActionBar();
        int id = getIntent().getExtras().getInt(Constants.EXTRA_TIPO_ALERTA, 0);
        EsquemaAlerta esquema = EsquemaAlerta.get(id);

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
                data1 = ((TextView) findViewById(R.id.data_1));
                data1.setHint(getString(esquema.getLabelData1()));;
                findViewById(R.id.data_1_container).setVisibility(View.VISIBLE);

                if(esquema.getLabelData2()!=0){
                    data2 = ((TextView) findViewById(R.id.data_2));
                    data2.setHint(getString(esquema.getLabelData2()));
                    findViewById(R.id.data_2_container).setVisibility(View.VISIBLE);
                }

            }

            ((EditText) findViewById(R.id.detalhes)).setHint(getString(esquema.getLabelDescricao()));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(esquema.getContainerColor()));
            }
        }else{
            Log.d("Alerta", "Não encontrado:" + id);
        }

        detalhes = (EditText) findViewById(R.id.detalhes);
        Rede r = Rede.listAll(Rede.class).get(0);
        redeId = r.getRedeId();
        membroId = r.getMembroId();
        location = tracker.getLocation();
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
        progress = ProgressDialog.show(this, getString(R.string.aguarde),
                getString(R.string.enviando_alerta, true));

        Alerta alerta = new Alerta();
        alerta.setDescricao(detalhes.getText().toString());
        alerta.setTipo(tipo);
        alerta.setDe(dataDe);
        alerta.setAte(dataAte);
        alerta.setMembroId(membroId);
        alerta.setRedeId(redeId);
        if(location!=null){
            alerta.setLatitude(location.getLatitude());
            alerta.setLogitude(location.getLongitude());
        }
        tracker.stopUsingGPS();
        new EnviarAlertaAsyncTask(this).execute(alerta);

    }


    public void showDatePickerDialog(View v) {
        currentDataView = v.getId();
        Calendar cal = Calendar.getInstance();
        new DatePickerDialog(this,this,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Log.d("OnDateSet","Chamado");
        Calendar cal = Calendar.getInstance();
        cal.set(year, monthOfYear, dayOfMonth);
        DateTime data = new DateTime(cal.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if(currentDataView == R.id.data_1){
            dataDe = data;
            data1.setText(sdf.format(cal.getTime()));

        }else if(currentDataView == R.id.data_2){
            dataAte = data;
            data2.setText(sdf.format(cal.getTime()));
        }

    }

    @Override
    public void onLocationChange(Location location) {
        this.location = location;
        if(location != null && location.getLatitude() != 0 ){
            tracker.stopUsingGPS();
        }
    }

    public void ok() {
        Toast.makeText(CriarAlertaActivity.this, R.string.alerta_enviado_com_sucesso,Toast.LENGTH_SHORT).show();
        progress.dismiss();
        tracker.stopUsingGPS();
        finish();
    }

    public void error(String descricao) {
        progress.dismiss();
        Toast.makeText(CriarAlertaActivity.this, descricao,Toast.LENGTH_SHORT).show();
        location = tracker.getLocation();
    }
}


