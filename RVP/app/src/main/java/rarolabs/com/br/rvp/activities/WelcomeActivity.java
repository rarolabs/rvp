package rarolabs.com.br.rvp.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;

import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.fragments.BuscaRedeFragment;
import rarolabs.com.br.rvp.fragments.GeoqueryResponderFragment;
import rarolabs.com.br.rvp.gcm.GcmRegister;
import rarolabs.com.br.rvp.views.RVPSlideView;


public class WelcomeActivity extends RVPActivity implements
        GeoqueryResponderFragment.OnFragmentInteractionListener,
        BuscaRedeFragment.OnFragmentInteractionListener{

    private Button buscarRede;
    private Button novaRede;
    private BuscaRedeFragment buscaRedeFragment;
    private GcmRegister gcmRegister;
    private BroadcastReceiver mReceiver;
    private IntentFilter intentFilter;
    private Button logar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        if (getActionBar() != null) {
            getActionBar().hide();
        }

        SliderLayout sliderShow = (SliderLayout) findViewById(R.id.slider);

        RVPSlideView image1 = new RVPSlideView (this);
        image1.image(R.drawable.tutorial_slider_1);
        image1.description(getString(R.string.descricao_tutorial_1));

        RVPSlideView  image2 = new RVPSlideView (this);
        image2.image(R.drawable.tutorial_slider_2);
        image2.description(getString(R.string.descricao_tutorial_2));

        RVPSlideView  image3 = new RVPSlideView (this);
        image3.image(R.drawable.tutorial_slider_3);
        image3.description(getString(R.string.descricao_tutorial_3));

        RVPSlideView  image4 = new RVPSlideView (this);
        image4.image(R.drawable.tutorial_slider_4);
        image4.description(getString(R.string.descricao_tutorial_4));

        sliderShow.addSlider(image1);
        sliderShow.addSlider(image2);
        sliderShow.addSlider(image3);
        sliderShow.addSlider(image4);

        gcmRegister = GcmRegister.newInstance(this);

        logar = (Button) findViewById(R.id.logar);
        logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logar();
            }
        });

        novaRede = (Button) findViewById(R.id.nova_rede);
        novaRede.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarNovaRede();
            }
        });


        buscaRedeFragment = (BuscaRedeFragment) getFragmentManager().findFragmentById(R.id.busca_rede_fragment);


    }

    private void logar() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle(R.string.title_logar)
                .setMessage(R.string.text_logar)
                .setPositiveButton(R.string.logar, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pickUserAccount();
                    }

                })
                .setNegativeButton(R.string.cancelar, null)
                .show();
    }

    @Override
    public void onResume(){
        super.onResume();
        if(!getSharedPreferences("RVP",0).getBoolean(Constants.PREF_NEW_USER, true)){
            getSharedPreferences("RVP",0).edit().putBoolean(Constants.WELCOME,false).commit();
            Intent i = new Intent(this,StartUpActivity.class);
            finish();
            startActivity(i);
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

    protected void setRequestCodePickAccountCanceled() {
        Toast.makeText(this, "Você precisa selecionar uma conta antes de continuar", Toast.LENGTH_SHORT).show();
    }

    protected void setRequestCodePickAccountOK(String email) {
        super.setRequestCodePickAccountOK(email);
        disableWelcomeActivity();
        startActivity(new Intent(this,StartUpActivity.class));
        finish();
    }

}