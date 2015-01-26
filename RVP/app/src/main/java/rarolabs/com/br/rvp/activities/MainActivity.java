package rarolabs.com.br.rvp.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.fragments.AlertasFragment;
import rarolabs.com.br.rvp.fragments.NotificacoesFragment;
import rarolabs.com.br.rvp.fragments.RedesFragment;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private static final String PREF_NEW_USER = "new_user";

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Log.d("toolbar", "toobar:" + toolbar.toString());
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
            FragmentManager fragmentManager = getSupportFragmentManager();
            switch(position) {
                default:
                case 0:
                    fragment = new RedesFragment();
                    break;
                case 1:
                    fragment = new AlertasFragment();
                    break;
                case 2:
                    fragment = new NotificacoesFragment();
                    break;

            }
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();

    }

    public void onSectionAttached(Fragment fragment) {
        if (fragment instanceof RedesFragment) {
            mTitle = getString(R.string.title_rede_de_vizinhos);
        } else if (fragment instanceof AlertasFragment) {
            mTitle = getString(R.string.title_alertas);
        } else if (fragment instanceof NotificacoesFragment) {
            mTitle = getString(R.string.title_alertas);
        }
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

    /**
     * A placeholder fragment containing a simple view.
     */
}
