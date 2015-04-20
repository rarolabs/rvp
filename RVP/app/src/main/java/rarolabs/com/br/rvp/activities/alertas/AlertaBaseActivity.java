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
import rarolabs.com.br.rvp.activities.RVPActivity;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.config.RVPApp;
import rarolabs.com.br.rvp.listeners.GPSTracker;
import rarolabs.com.br.rvp.models.EsquemaAlerta;
import rarolabs.com.br.rvp.models.Notificacao;
import rarolabs.com.br.rvp.models.Rede;
import rarolabs.com.br.rvp.services.tasks.EnviarAlertaAsyncTask;

public class AlertaBaseActivity extends RVPActivity {


    private String tipo;
    protected EsquemaAlerta esquema;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent().getExtras()!=null) {
            esquema = EsquemaAlerta.get(getIntent().getExtras().getInt(Constants.EXTRA_TIPO_ALERTA, 0));
            if(esquema==null){
                esquema = EsquemaAlerta.get(getIntent().getExtras().getString(Constants.EXTRA_TIPO_ALERTA_STRING, ""));
            }
            ActionBar mActionBar = getSupportActionBar();
            if (esquema != null) {
                tipo = esquema.getType();
                mActionBar.setTitle(getString(esquema.getTitle()));
                mActionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(esquema.getActionBarColor())));
                mActionBar.setDisplayShowTitleEnabled(false);
                mActionBar.setDisplayShowTitleEnabled(true);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(getResources().getColor(esquema.getContainerColor()));
                }
            } else {
                Log.d("Alerta", "Não encontrado:");
            }
        }
    }

    @Override
    public void mostraMensagem(Notificacao notificacao) {
        super.mostraMensagem(notificacao);
    }


}


