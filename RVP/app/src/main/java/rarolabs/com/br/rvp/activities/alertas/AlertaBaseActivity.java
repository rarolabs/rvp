package rarolabs.com.br.rvp.activities.alertas;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
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

public class AlertaBaseActivity extends ActionBarActivity {


    private String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setEsquemaActionBar(TextView data1){
        int id = getIntent().getExtras().getInt(Constants.EXTRA_TIPO_ALERTA, 0);
        ActionBar mActionBar = getSupportActionBar();
        EsquemaAlerta esquema = EsquemaAlerta.get(id);

        if(esquema!=null) {
            tipo = esquema.getType();
            mActionBar.setTitle(getString(esquema.getTitle()));
            mActionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(esquema.getActionBarColor())));
            mActionBar.setDisplayShowTitleEnabled(false);
            mActionBar.setDisplayShowTitleEnabled(true);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(esquema.getContainerColor()));
            }
        }else{
            Log.d("Alerta", "Não encontrado:" + id);
        }
    }

    protected void setEsquemaHeader(){
        ((ImageView) findViewById(R.id.header_icon)).setImageResource(esquema.getHeaderIcon());
        ((TextView) findViewById(R.id.header_text)).setText(getString(esquema.getHeaderString()));
        ((LinearLayout) findViewById(R.id.header_container)).setBackgroundColor(getResources().getColor(esquema.getContainerColor()));
        if(esquema.getLabelData1()!=0){
            ((TextView) findViewById(R.id.data_1)).setHint(getString(esquema.getLabelData1()));
            findViewById(R.id.data_1_container).setVisibility(View.VISIBLE);

            if(esquema.getLabelData2()!=0){
                ((TextView) findViewById(R.id.data_2)).setHint(getString(esquema.getLabelData2()));
                findViewById(R.id.data_2_container).setVisibility(View.VISIBLE);
            }

        }

        ((EditText) findViewById(R.id.detalhes)).setHint(getString(esquema.getLabelDescricao()));

    }


}


