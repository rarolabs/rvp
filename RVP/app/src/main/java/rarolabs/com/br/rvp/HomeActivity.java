package rarolabs.com.br.rvp;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class HomeActivity extends ActionBarActivity {


    String TITLES[] = {"Redes de Vizinhos","Alertas","Mensagens", "Perfil"};
    int ICONS[] = {R.drawable.ic_drawer_redes_normal,
                   R.drawable.ic_drawer_alertas_normal,
                   R.drawable.ic_drawer_notificacoes_normal,
                   R.drawable.ic_drawer_perfil_normal};

    String NAME = "Rodrigo Sol";
    String EMAIL = "rodrigosol@gmail.com";
    int PROFILE = R.drawable.ic_cadastro_foto_vazia;

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    DrawerLayout drawer;                                  // Declaring DrawerLayout

    ActionBarDrawerToggle mDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//
//        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
//        mRecyclerView.setHasFixedSize(true);
//        mAdpater = new DrawerAdpater(TITLES,ICONS,NAME,PROFILE);
//        mRecyclerView.setAdapter(mAdapter);
//        mLayoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//
//        drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
//        //getSupportActionBar()
//        mDrawerToggle = new ActionBarDrawerToggle(this,drawer,null,R.string.openDrawer,R.string.closeDrawer){
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
//                // open I am not going to put anything here)
//            }
//
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                super.onDrawerClosed(drawerView);
//                // Code here will execute once drawer is closed
//            }
//
//        };



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
}
