package rarolabs.com.br.rvp.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Stack;

import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.fragments.AlertaDialogFragment;

import rarolabs.com.br.rvp.fragments.BuscaRedeFragment;
import rarolabs.com.br.rvp.fragments.GeoqueryResponderFragment;
import rarolabs.com.br.rvp.fragments.MinhasRedesFragment;
import rarolabs.com.br.rvp.fragments.NavigationDrawerFragment;
import rarolabs.com.br.rvp.fragments.NotificacaoDialogFragment;
import rarolabs.com.br.rvp.fragments.SobreFragment;
import rarolabs.com.br.rvp.fragments.notificacoes.AlertasFragment;
import rarolabs.com.br.rvp.fragments.notificacoes.NotificacoesFragment;
import rarolabs.com.br.rvp.gcm.GcmRegister;
import rarolabs.com.br.rvp.models.Notificacao;
import rarolabs.com.br.rvp.services.tasks.AtualizarAvatarAsyncTask;
import rarolabs.com.br.rvp.utils.ImageUtil;


public class MainActivity extends RVPActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        GeoqueryResponderFragment.OnFragmentInteractionListener,
        BuscaRedeFragment.OnFragmentInteractionListener,
        MinhasRedesFragment.OnFragmentInteractionListener,
        NotificacoesFragment.OnFragmentInteractionListener,
        NotificacaoDialogFragment.NoticeDialogListener,
        AlertaDialogFragment.NoticeDialogListener,
        ObservableScrollViewCallbacks {


    private View mFlexibleSpaceView;
    private View mToolbarView;
    private TextView mTitleView;
    private int mFlexibleSpaceHeight;

    private static final int SECTION_MINHAS_REDES = 0;
    private static final int SECTION_ALERTAS = 1;
    private static final int SECTION_NOTIFICACOES = 2;
    private static final int SECTION_BUSCA_REDES = 4;
    private static final int SECTION_SOBRE = 3;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;


    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */

    private int sectionNumer = -1;
    private View mFab;
    private int mFabMargin;
    private boolean mFabIsShown;
    private MinhasRedesFragment minhasRedesFragment;
    private AlertasFragment alertasFragment;
    private NotificacoesFragment notificacoesFragment;
    private SobreFragment sobreFragment;
    private BuscaRedeFragment buscaRedeFragment;
    private GcmRegister gcmRegister;
    private boolean mudarParaNotificacoes = false;
    private boolean mudarParaAlertas;
    Stack<Integer> stackSection = new Stack<Integer>();
    private int cont = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
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

        enableNotificacoes((RelativeLayout) findViewById(R.id.notificacao));
        findViewById(R.id.notificacao).setVisibility(View.VISIBLE);


        if(mudarParaNotificacoes) {
            mNavigationDrawerFragment.mostraNotificacoes();
        }else if(mudarParaAlertas){
            mNavigationDrawerFragment.mostraAlertas();
        }

    }




    @Override
    public void onNewIntent(Intent intent) {
        Bundle extras = intent.getExtras();

        if (extras != null) {
            Log.d("Notificacao","Extra:" + extras.getString(Constants.FRAGMENT_NOTIFICACOES,""));
            if (extras.getString(Constants.FRAGMENT_NOTIFICACOES,"").equals("NOTIFICACAO")) {
                this.mudarParaNotificacoes = true;
            }else if (extras.getString(Constants.FRAGMENT_NOTIFICACOES,"").equals("ALERTA")) {
                this.mudarParaAlertas = true;
            }

        }
    }



    private void fabClick() {
        switch (sectionNumer) {
            case SECTION_MINHAS_REDES:
            case SECTION_BUSCA_REDES:
                novaRede();
                break;
            case SECTION_ALERTAS:
                novoAlerta();
                findViewById(R.id.notificacao).setVisibility(View.VISIBLE);
        }
    }

    private void novoAlerta() {
        AlertaDialogFragment.newInstance().show(getFragmentManager(), "ALERTA_DIALOG");
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
                    alertasFragment = AlertasFragment.newInstance();
                }
                return alertasFragment;

            case SECTION_NOTIFICACOES:
                if (notificacoesFragment == null) {
                    notificacoesFragment =  NotificacoesFragment.newInstance();
                }
                return notificacoesFragment;
            case SECTION_SOBRE:
                if(sobreFragment == null){
                    sobreFragment = new SobreFragment();
               }
                return sobreFragment;
        }

        return null;
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        sectionNumer = position;
        stackSection.push(sectionNumer);
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
        } else if(fragment instanceof SobreFragment) {
            //mTitleView.setText(getString(R.string.title_sobre));
            this.sectionNumer = SECTION_SOBRE;
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
                mFab.setVisibility(View.VISIBLE);
                break;
            case 1:
                menu_itens = R.menu.menu_fragment_alertas;
                mTitleView.setText(getString(R.string.title_alertas));
                mFab.setVisibility(View.VISIBLE);
                break;
            case 2:
                menu_itens = R.menu.menu_fragment_notificacoes;
                mTitleView.setText(getString(R.string.title_notificacoes));
                mFab.setVisibility(View.GONE);
                break;
//            case 3:
//                menu_itens = R.menu.menu_fragment_busca_redes;
//                mTitleView.setText(getString(R.string.title_busca_redes));
//                mFab.setVisibility(View.VISIBLE);
//                break;
            case 3:
               menu_itens = R.menu.menu_fragment_alertas;
               mTitleView.setText(getString(R.string.title_sobre));
               mFab.setVisibility(View.GONE);
               break;
        }

        getMenuInflater().inflate(menu_itens, menu);
        restoreActionBar();

        return super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

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
                stackSection.push(sectionNumer);
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

//        int mFabTranslateY = ((rect.centerY() - adjustedScrollY) - (mFab.getHeight() / 2));
//        ViewHelper.setTranslationY(mFab, mFabTranslateY);

        // Show/hide FAB
        if (scrollY > 0) {
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
        Log.d("onBackPressed", "Back");
        FragmentManager fragmentManager = getFragmentManager();
//        if (fragmentManager.getBackStackEntryCount() > 1) {
//            Toast.makeText(this, "Back", Toast.LENGTH_SHORT).show();
//            fragmentManager.popBackStackImmediate();
//            fragmentManager.beginTransaction().commit();
        if(fragmentManager.getBackStackEntryCount() > 0){
            fragmentManager.popBackStack();
            if(stackSection.size() > 0) {  // adaptação
                if(cont == 0){
                    stackSection.pop();
                }
                cont++;
                if(stackSection.size() > 0) {
                    sectionNumer = stackSection.pop();
                    if(sectionNumer == 3 && stackSection.size() > 0){ sectionNumer = stackSection.pop();}else{cont=0;}
                }else{ cont = 0;}
                Log.d("onBackPressed","Pilha tamanho: " + stackSection.size());
                Log.d("onBackPressed","Section: " + sectionNumer);
            }else{
                cont = 0;
            }
        }else{
            super.onBackPressed();
        }
//        if (sectionNumer == SECTION_BUSCA_REDES) {
//            sectionNumer = SECTION_ALERTAS;
//
//        }
        invalidateOptionsMenu();
    }

    @Override
    public void mostraBarraNotificacao(String tipoAlerta) {
        super.mostraBarraNotificacao(tipoAlerta);
        if (sectionNumer == SECTION_NOTIFICACOES) {
            notificacoesFragment.refreshContent();
        }else if(sectionNumer == SECTION_ALERTAS){
            alertasFragment.refreshContent();
        }
    }

    @Override
    public void returnFromDialog(long notificacaoId) {
        Log.d("Dialog", "Fui fechado");
        Notificacao n = Notificacao.findById(Notificacao.class, notificacaoId);
        n.setLido(true);
        n.save();
        notificacoesFragment.refreshContent();
    }


        @Override
    protected void setRequestCodeSelectPhotoOK(Uri selectedImage) {
        InputStream imageStream = null;
        try {
            Bitmap profileImage = ImageUtil.decodeUri(this, selectedImage);

            mNavigationDrawerFragment.getProfileImage().setImageBitmap(profileImage);


            Bitmap blur = ImageUtil.fastblur(profileImage, 30);

            File fileProfile = new File(ImageUtil.saveToInternalSorage(this,profileImage,"profile.jpg"));
            File fileProfileBlur = new File(ImageUtil.saveToInternalSorage(this,blur,"profile_blur.jpg"));

            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("PROFILE_IMAGE", true);
            editor.commit();

            new AtualizarAvatarAsyncTask(this).execute(fileProfile,fileProfileBlur);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_trocar_foto:
                        trocarFoto();
                        return true;
                    case R.id.action_signout:
                        sair();
                        return true;
                }
                return false;
            }
        });

        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_draweler, popup.getMenu());
        popup.show();
    }

    private void sair() {

        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Constants.ACCOUNT, null);
        editor.putBoolean(Constants.WELCOME, true);
        editor.putBoolean(Constants.PREF_NEW_USER,true);
        editor.commit();
        startActivity(new Intent(this,StartUpActivity.class));
        finish();

    }


}
