package rarolabs.com.br.rvp.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import br.com.rarolabs.rvp.api.rvpAPI.RvpAPI;
import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.fragments.AlertasFragment;
import rarolabs.com.br.rvp.fragments.BuscaRedeFragment;
import rarolabs.com.br.rvp.fragments.GeoqueryResponderFragment;
import rarolabs.com.br.rvp.fragments.MinhasRedesFragment;
import rarolabs.com.br.rvp.fragments.NavigationDrawerFragment;
import rarolabs.com.br.rvp.fragments.NotificacoesFragment;
import rarolabs.com.br.rvp.fragments.RedesFragment;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
                   GeoqueryResponderFragment.OnFragmentInteractionListener,
                   BuscaRedeFragment.OnFragmentInteractionListener,
                   MinhasRedesFragment.OnFragmentInteractionListener{

    private static final int SECTION_REDE_DE_VIZINHOS = 0;
    private static final int SECTION_ALERTAS = 1;
    private static final int SECTION_NOTIFICACOES = 2;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private static final String PREF_NEW_USER = "new_user";

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private int sectionNumer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_actionbar_menu);
        setSupportActionBar(toolbar);


        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        // enable navigation bar tint
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setTintColor(getResources().getColor(R.color.material_green_700));


        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

//        if(sp.getBoolean(PREF_NEW_USER,true)){
//            Intent intent = new Intent(this, WelcomeActivity.class);
//            startActivity(intent);
//        }

    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {
            Fragment fragment;
            FragmentManager fragmentManager = getFragmentManager();
            switch(position) {
                default:
                case 0:
                    sectionNumer = 0;
                    fragment = new RedesFragment();
                    break;
                case 1:
                    fragment = new AlertasFragment();
                    sectionNumer = 1;
                    break;
                case 2:
                    fragment = new NotificacoesFragment();
                    sectionNumer = 2;
                    break;

            }
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment,"MAIN_FRAGMENT_" + sectionNumer )
                    .commit();

    }

    public void onSectionAttached(Fragment fragment) {
        
        if (fragment instanceof RedesFragment) {
            mTitle = getString(R.string.title_rede_de_vizinhos);
            this.sectionNumer = SECTION_REDE_DE_VIZINHOS ;
        } else if (fragment instanceof AlertasFragment) {
            mTitle = getString(R.string.title_alertas);
            this.sectionNumer = SECTION_ALERTAS;
        } else if (fragment instanceof NotificacoesFragment) {
            mTitle = getString(R.string.title_notificacoes);
            this.sectionNumer = SECTION_NOTIFICACOES;
        }

        invalidateOptionsMenu();

    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        int menu_itens = 0;
        switch (sectionNumer){
            default:
            case 0:
                menu_itens = R.menu.menu_fragment_redes;
                break;
            case 1:
                menu_itens = R.menu.menu_fragment_alertas;
                break;
            case 2:
                menu_itens = R.menu.menu_fragment_notificacoes;
                break;
        }

        getMenuInflater().inflate(menu_itens, menu);
        restoreActionBar();

        return super.onPrepareOptionsMenu(menu);

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
        switch (id) {
            case R.id.action_buscar_rede:

                Fragment fragment;
                FragmentManager fragmentManager = getFragmentManager();
                Fragment f = getFragmentManager().findFragmentByTag("BUSCA_REDE");

                if (f == null) {
                    f = new BuscaRedeFragment();
                }


                fragmentManager.beginTransaction()
                        .replace(R.id.busca_rede_container, f, "BUSCA_REDE")
                        .commit();

                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(String id) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
}
