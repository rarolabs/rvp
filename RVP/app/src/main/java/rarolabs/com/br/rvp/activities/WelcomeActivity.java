package rarolabs.com.br.rvp.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.fragments.BuscaRedeFragment;
import rarolabs.com.br.rvp.fragments.GeoqueryResponderFragment;


public class WelcomeActivity extends Activity implements
        GeoqueryResponderFragment.OnFragmentInteractionListener,
        BuscaRedeFragment.OnFragmentInteractionListener {

    private Button buscarRede;
    private Button novaRede;
    private BuscaRedeFragment buscaRedeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        if (getActionBar() != null) {
            getActionBar().hide();
        }

        SliderLayout sliderShow = (SliderLayout) findViewById(R.id.slider);

        DefaultSliderView defaultSliderView = new DefaultSliderView(this);
        defaultSliderView
                .image(R.drawable.img1);

        sliderShow.stopAutoCycle();
        sliderShow.addSlider(defaultSliderView);

        buscarRede = (Button) findViewById(R.id.buscar_rede);
        novaRede = (Button) findViewById(R.id.nova_rede);
        novaRede.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarNovaRede();
            }
        });


        buscaRedeFragment = (BuscaRedeFragment) getFragmentManager().findFragmentById(R.id.busca_rede_fragment);

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


    public void criarNovaRede(){

            Intent i = new Intent(WelcomeActivity.this,CadastroActivity.class);
            i.putExtra("NOVA_REDE",true);
            startActivity(i);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
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