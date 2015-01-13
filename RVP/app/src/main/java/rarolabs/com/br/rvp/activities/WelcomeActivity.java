package rarolabs.com.br.rvp.activities;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.services.BuscaRedesAsyncTask;


public class WelcomeActivity extends Activity {
    ImageView spinner;
    private Button buscarRede;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        SliderLayout sliderShow = (SliderLayout) findViewById(R.id.slider);

        DefaultSliderView defaultSliderView = new DefaultSliderView(this);
        defaultSliderView
                .image(R.drawable.img1);

        sliderShow.stopAutoCycle();
        sliderShow.addSlider(defaultSliderView);
        spinner = (ImageView) findViewById(R.id.spinner);

        spinner.startAnimation(
                AnimationUtils.loadAnimation(this, R.anim.rotate_forever) );

        buscarRede = (Button) findViewById(R.id.buscar_rede);
        buscarRede.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BuscaRedesAsyncTask(WelcomeActivity.this).execute();
            }
        });


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

    public void ok() {
        Toast.makeText(this,"Ok",Toast.LENGTH_LONG);
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
