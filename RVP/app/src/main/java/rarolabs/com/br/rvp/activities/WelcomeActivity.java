package rarolabs.com.br.rvp.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.location.Location;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import java.util.List;

import br.com.rarolabs.rvp.api.rvpAPI.model.GeoqueryResponder;
import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.listeners.GPSTracker;
import rarolabs.com.br.rvp.services.BuscaRedesAsyncTask;


public class WelcomeActivity extends Activity implements
        GeoqueryResponderFragment.OnFragmentInteractionListener, Locable {
    public static final java.lang.String EXTRA_NOME_REDE = "nome_rede";
    public static final java.lang.String EXTRA_ENDERECO_REDE = "endereco_rede";
    public static final java.lang.String EXTRA_NOME_ADMIN = "nome_admin";
    public static final java.lang.String EXTRA_ULTIMA_ATIVIDADE = "ultima_atividade";
    public static final java.lang.String EXTRA_QUANTIDADE_MEMBROS = "quantidade_membros";
    public static final java.lang.String EXTRA_LATITUDE = "latitude";
    public static final java.lang.String EXTRA_LONGITUDE = "logintude";
    public static final java.lang.String EXTRA_ID_REDE = "id";

    ImageView spinner;
    private Button buscarRede;
    private GeoqueryResponderFragment buscaRedesFragment;
    private View loading;
    private TextView statusBusca;
    private GPSTracker gps;
    private double userLatitude;
    private double userLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        if (getActionBar() != null) {
            getActionBar().hide();
        }
        statusBusca = (TextView) findViewById(R.id.status_busca);
        SliderLayout sliderShow = (SliderLayout) findViewById(R.id.slider);

        DefaultSliderView defaultSliderView = new DefaultSliderView(this);
        defaultSliderView
                .image(R.drawable.img1);

        sliderShow.stopAutoCycle();
        sliderShow.addSlider(defaultSliderView);
        loading = findViewById(R.id.loading);
        spinner = (ImageView) findViewById(R.id.spinner);

        buscaRedesFragment =
                (GeoqueryResponderFragment) getFragmentManager().findFragmentById(R.id.geoquery_responder_fragment);
        buscaRedesFragment.getView().setVisibility(View.GONE);
        buscarRede = (Button) findViewById(R.id.buscar_rede);
        buscarRede.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscar(null);
            }
        });

        if (gps == null) {
            gps = new GPSTracker(WelcomeActivity.this);
        }

        // check if GPS enabled
        if (!gps.canGetLocation()) {
            gps.showSettingsAlert();
        } else {
            spinner.startAnimation(
                    AnimationUtils.loadAnimation(this, R.anim.rotate_forever));

            onLocationChange(gps.getLocation());
        }


    }

    @Override
    public void onLocationChange(Location location) {
        if (location.getLatitude() != 0) {
            gps.stopUsingGPS();
            buscar(location);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void ok(List<GeoqueryResponder> result) {
        loading.setVisibility(View.GONE);
        buscaRedesFragment.getView().setVisibility(View.VISIBLE);
        buscaRedesFragment.getAdapter().addAll(result);
        buscaRedesFragment.getAdapter().notifyDataSetChanged();

        if (result.size() == 0) {
            notFound();
        }
    }

    public void error(String msg) {
        Log.i("BUSCA_REDE", "Erro ao buscar redes:" + msg);
    }

    public void notFound() {


        spinner.clearAnimation();
        spinner.setImageResource(R.drawable.ic_tutorial_empty);
        statusBusca.setText(R.string.busca_redes_nao_encontradas);
        loading.setVisibility(View.VISIBLE);
        statusBusca.startAnimation(
                AnimationUtils.loadAnimation(this, R.anim.bouce));

        loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscar(null);
            }
        });
    }

    private void buscar(Location location) {

        if (location == null) {
            location = gps.getLocation();
        }

        SharedPreferences settings = getSharedPreferences("RVP", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("USER_LATITUDE",String.valueOf(location.getLatitude()));
        editor.putString("USER_LONGITUDE",String.valueOf(location.getLongitude()));
        editor.commit();


        buscaRedesFragment.getView().setVisibility(View.GONE);
        statusBusca.setText(R.string.buscando_redes);
        spinner.setImageResource(R.drawable.ic_tutorial_loading);
        spinner.startAnimation(
                AnimationUtils.loadAnimation(this, R.anim.rotate_forever));

        loading.setVisibility(View.VISIBLE);
        loading.setOnClickListener(null);
        new BuscaRedesAsyncTask(WelcomeActivity.this).execute(location);

    }

    @Override
    public void onFragmentInteraction(String id) {

    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_welcome, container, false);
            return rootView;
        }
    }
}