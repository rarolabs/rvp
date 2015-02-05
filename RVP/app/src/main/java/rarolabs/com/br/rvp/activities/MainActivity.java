package rarolabs.com.br.rvp.activities;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.fragments.AlertasFragment;
import rarolabs.com.br.rvp.fragments.BuscaRedeFragment;
import rarolabs.com.br.rvp.fragments.GeoqueryResponderFragment;
import rarolabs.com.br.rvp.fragments.MinhasRedesFragment;
import rarolabs.com.br.rvp.fragments.NavigationDrawerFragment;
import rarolabs.com.br.rvp.fragments.NotificacaoDialogFragment;
import rarolabs.com.br.rvp.fragments.NotificacoesFragment;
import rarolabs.com.br.rvp.gcm.GcmRegister;
import rarolabs.com.br.rvp.models.Notificacao;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        GeoqueryResponderFragment.OnFragmentInteractionListener,
        BuscaRedeFragment.OnFragmentInteractionListener,
        MinhasRedesFragment.OnFragmentInteractionListener,
        NotificacoesFragment.OnFragmentInteractionListener,
        NotificacaoDialogFragment.NoticeDialogListener,
        ObservableScrollViewCallbacks {


    private View mFlexibleSpaceView;
    private View mToolbarView;
    private TextView mTitleView;
    private int mFlexibleSpaceHeight;

    private static final int SECTION_MINHAS_REDES = 0;
    private static final int SECTION_ALERTAS = 1;
    private static final int SECTION_NOTIFICACOES = 2;
    private static final int SECTION_BUSCA_REDES = 3;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private static final String PREF_NEW_USER = "new_user";

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */

    private int sectionNumer = 0;
    private View mFab;
    private int mFabMargin;
    private boolean mFabIsShown;
    private MinhasRedesFragment minhasRedesFragment;
    private AlertasFragment alertasFragment;
    private NotificacoesFragment notificacoesFragment;
    private BuscaRedeFragment buscaRedeFragment;
    private GcmRegister gcmRegister;
    private RelativeLayout alerta;
    private AnimatorSet animations;
    private TextSwitcher alertaTexto;
    private Boolean alertaSendoExibido = false;
    private BroadcastReceiver mReceiver;
    private IntentFilter intentFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_actionbar_menu);
        toolbar.setNavigationContentDescription("Menu");

        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);

        gcmRegister = GcmRegister.newInstance(MainActivity.this);

        mFlexibleSpaceView = findViewById(R.id.flexible_space);
        mTitleView = (TextView) findViewById(R.id.title);
        mTitleView.setText(getResources().getString(R.string.drawer_redes));
        setTitle("");
        mToolbarView = findViewById(R.id.toolbar);

        mFab = findViewById(R.id.fab);
        mFabMargin = getResources().getDimensionPixelSize(R.dimen.margin_standard);
        ViewHelper.setScaleX(mFab, 1);
        ViewHelper.setScaleY(mFab, 1);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabClick();
            }
        });


        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        //mTitle = (TextView) findViewById(R.id.title);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));



        alerta = (RelativeLayout) findViewById(R.id.notificacao);

        setupAnimation();

        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("Main", "Alerta rececido");
                mostrarAlerta();
            }
        };

        intentFilter = new IntentFilter("rarolabs.com.br.rvp.broadcast.MOSTRA_ALERTA");
        onNewIntent(getIntent());
    }



    @Override
    public void onNewIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey(Constants.FRAGMENT)) {
                mNavigationDrawerFragment.mostraNotificacoes();
            }
        }
    }

    private void setupAnimation() {
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                42,
                getResources().getDisplayMetrics()
        );

        ObjectAnimator anim1 = ObjectAnimator.ofFloat(alerta, "TranslationY", px);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(alerta, "TranslationY", -px);
        anim2.setStartDelay(2000);
        animations = new AnimatorSet();
        animations.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mostrandoAlerta(true);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mostrandoAlerta(false);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mostrandoAlerta(false);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                mostrandoAlerta(false);
            }
        });
        animations.play(anim1).before(anim2);
    }

    private synchronized void mostrandoAlerta(Boolean status) {
        alertaSendoExibido = status;
    }

    public void mostrarAlerta() {
        if (!alertaSendoExibido) {
            animations.start();
            if (sectionNumer == SECTION_NOTIFICACOES) {
                notificacoesFragment.refreshContent();
            }
        }
    }

    private void fabClick() {
        switch (sectionNumer) {
            case SECTION_MINHAS_REDES:
            case SECTION_BUSCA_REDES:
                novaRede();
                break;
        }
    }

    private void novaRede() {
        Intent i = new Intent(MainActivity.this, CadastroActivity.class);
        i.putExtra("NOVA_REDE", true);
        startActivity(i);
    }

    public Fragment getFragmentBySection(int sectionNumer) {
        switch (sectionNumer) {
            case SECTION_MINHAS_REDES:
                if (minhasRedesFragment == null) {
                    minhasRedesFragment = new MinhasRedesFragment();
                }
                return minhasRedesFragment;

            case SECTION_BUSCA_REDES:
                if (buscaRedeFragment == null) {
                    buscaRedeFragment = new BuscaRedeFragment();
                }
                return buscaRedeFragment;
            case SECTION_ALERTAS:
                if (alertasFragment == null) {
                    alertasFragment = new AlertasFragment();
                }
                return alertasFragment;

            case SECTION_NOTIFICACOES:
                if (notificacoesFragment == null) {
                    notificacoesFragment = new NotificacoesFragment();
                }
                return notificacoesFragment;
        }

        return null;
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        sectionNumer = position;

        Fragment fragment = getFragmentBySection(sectionNumer);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment, "MAIN_FRAGMENT_" + sectionNumer)
                .addToBackStack("MAIN_FRAGMENT_" + sectionNumer)
                .commit();

    }

    public void onSectionAttached(Fragment fragment) {


        if (fragment instanceof MinhasRedesFragment) {
            mTitleView.setText(getString(R.string.title_rede_de_vizinhos));
            this.sectionNumer = SECTION_MINHAS_REDES;
        } else if (fragment instanceof BuscaRedeFragment) {
            mTitleView.setText(getString(R.string.title_busca_redes));
            this.sectionNumer = SECTION_BUSCA_REDES;
        } else if (fragment instanceof AlertasFragment) {
            mTitleView.setText(getString(R.string.title_alertas));
            this.sectionNumer = SECTION_ALERTAS;
        } else if (fragment instanceof NotificacoesFragment) {
            mTitleView.setText(getString(R.string.title_notificacoes));
            this.sectionNumer = SECTION_NOTIFICACOES;
        }

        invalidateOptionsMenu();

    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("");

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
        Log.d("Menu", "section:" + sectionNumer);
        menu.clear();
        int menu_itens = 0;
        switch (sectionNumer) {
            default:
            case 0:
                menu_itens = R.menu.menu_fragment_redes;
                mTitleView.setText(getString(R.string.title_rede_de_vizinhos));
                break;
            case 1:
                menu_itens = R.menu.menu_fragment_alertas;
                mTitleView.setText(getString(R.string.title_alertas));
                break;
            case 2:
                menu_itens = R.menu.menu_fragment_notificacoes;
                mTitleView.setText(getString(R.string.title_notificacoes));
                break;
            case 3:
                menu_itens = R.menu.menu_fragment_busca_redes;
                mTitleView.setText(getString(R.string.title_busca_redes));
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
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = null;
        switch (id) {
            case R.id.action_buscar_rede:

                fragment = getFragmentBySection(SECTION_BUSCA_REDES);
                sectionNumer = SECTION_BUSCA_REDES;
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, "MAIN_FRAGMENT_" + SECTION_BUSCA_REDES)
                        .addToBackStack("MAIN_FRAGMENT_" + SECTION_BUSCA_REDES)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
                onSectionAttached(fragment);
                break;

            case R.id.action_voltar:
                onBackPressed();
                break;

            case R.id.action_marcar_notificacoes_como_lidas:
                notificacoesFragment.marcarTodasComoLidas();
                break;

            case R.id.action_excluir_todas_notificacoes:
                notificacoesFragment.excluirTodasNotificacoes();

                break;

            case R.id.action_atualizar_notificacoes:
                notificacoesFragment.refreshContent();
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

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        updateFlexibleSpaceText(scrollY);
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }

    private void updateFlexibleSpaceText(final int scrollY) {
        ViewHelper.setTranslationY(mFlexibleSpaceView, -scrollY);
        int adjustedScrollY = scrollY;
        if (scrollY < 0) {
            adjustedScrollY = 0;
        } else if (mFlexibleSpaceHeight < scrollY) {
            adjustedScrollY = mFlexibleSpaceHeight;
        }
        float maxScale = (float) (mFlexibleSpaceHeight - mToolbarView.getHeight()) / mToolbarView.getHeight();
        float scale = maxScale * ((float) mFlexibleSpaceHeight - adjustedScrollY) / mFlexibleSpaceHeight;

        ViewHelper.setPivotX(mTitleView, 0);
        ViewHelper.setPivotY(mTitleView, 0);
        ViewHelper.setScaleX(mTitleView, 1 + scale);
        ViewHelper.setScaleY(mTitleView, 1 + scale);
        ViewHelper.setTranslationY(mTitleView, ViewHelper.getTranslationY(mFlexibleSpaceView) + mFlexibleSpaceView.getHeight() - mTitleView.getHeight() * (1 + scale));
        int maxTitleTranslationY = mToolbarView.getHeight() + mFlexibleSpaceHeight - (int) (mTitleView.getHeight() * (1 + scale));
        int titleTranslationY = (int) (maxTitleTranslationY * ((float) mFlexibleSpaceHeight - adjustedScrollY) / mFlexibleSpaceHeight);
        ViewHelper.setTranslationY(mTitleView, titleTranslationY);
        int[] location = {0, 0};
        Rect rect = new Rect();
        mFab.getLocalVisibleRect(rect);

        int mFabTranslateY = ((rect.centerY() - adjustedScrollY) - (mFab.getHeight() / 2));
        ViewHelper.setTranslationY(mFab, mFabTranslateY);

        // Show/hide FAB
        if (scrollY > 80) {
            hideFab();
        } else {
            showFab();
        }
    }

    protected int getActionBarSize() {
        TypedValue typedValue = new TypedValue();
        int[] textSizeAttr = new int[]{R.attr.actionBarSize};
        int indexOfAttrTextSize = 0;
        TypedArray a = obtainStyledAttributes(typedValue.data, textSizeAttr);
        int actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
        a.recycle();
        return actionBarSize;
    }

    public void setRecycleView(final ObservableRecyclerView mRecyclerView) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setScrollViewCallbacks(this);

        mFlexibleSpaceHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_height);
        int flexibleSpaceAndToolbarHeight = mFlexibleSpaceHeight + getActionBarSize();

        //findViewById(R.id.body).setPadding(0, flexibleSpaceAndToolbarHeight, 0, 0);
        mFlexibleSpaceView.getLayoutParams().height = flexibleSpaceAndToolbarHeight;

        ScrollUtils.addOnGlobalLayoutListener(mTitleView, new Runnable() {
            @Override
            public void run() {
                updateFlexibleSpaceText(mRecyclerView.getCurrentScrollY());
            }
        });
    }

    private void showFab() {
        if (!mFabIsShown) {
            ViewPropertyAnimator.animate(mFab).cancel();
            ViewPropertyAnimator.animate(mFab).scaleX(1).scaleY(1).setDuration(200).start();
            mFabIsShown = true;
        }
    }

    private void hideFab() {
        if (mFabIsShown) {
            ViewPropertyAnimator.animate(mFab).cancel();
            ViewPropertyAnimator.animate(mFab).scaleX(0).scaleY(0).setDuration(200).start();
            mFabIsShown = false;
        }
    }

    public void onBackPressed() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStackImmediate();
            fragmentManager.beginTransaction().commit();
        }
        if (sectionNumer == SECTION_BUSCA_REDES) {
            sectionNumer = SECTION_MINHAS_REDES;
        }
        invalidateOptionsMenu();


    }

//    myReceiver = new MyReceiver();
//    intentFilter = new IntentFilter("com.hmkcode.android.USER_ACTION");
//}

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }


    @Override
    public void returnFromDialog(long notificacaoId) {
        Log.d("Dialog", "Fui fechado");
        Notificacao n = Notificacao.findById(Notificacao.class, notificacaoId);
        n.setLido(true);
        n.save();
        notificacoesFragment.refreshContent();
    }
}
