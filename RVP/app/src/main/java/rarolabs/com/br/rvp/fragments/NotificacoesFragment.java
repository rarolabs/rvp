package rarolabs.com.br.rvp.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.rarolabs.rvp.api.rvpAPI.model.RedeDetalhada;
import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.activities.MainActivity;
import rarolabs.com.br.rvp.adapters.NotificacoesAdapter;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.listeners.RecyclerItemClickListener;
import rarolabs.com.br.rvp.models.Notificacao;
import rarolabs.com.br.rvp.views.Loading;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MinhasRedesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MinhasRedesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificacoesFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private ObservableRecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private NotificacoesAdapter mAdapter;
    private Loading loading;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String currentUser;

    // TODO: Rename and change types and number of parameters
    public static NotificacoesFragment newInstance(String param1, String param2) {
        NotificacoesFragment fragment = new NotificacoesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public NotificacoesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new NotificacoesAdapter(getActivity(),new ArrayList<Notificacao>());
        currentUser = getActivity().getSharedPreferences("RVP",0).getString(Constants.ACCOUNT,null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notificacoes, container, false);

        mRecyclerView = (ObservableRecyclerView) view.findViewById(R.id.notificacoes_recycler_view);
        MainActivity activity = (MainActivity) getActivity();
        activity.setRecycleView(mRecyclerView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.notificacoes_swipe_refresh_layout);
        mSwipeRefreshLayout.setProgressViewOffset(false,150,190);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });

        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.material_green_500,
                R.color.material_green_700,
                R.color.material_green_A200);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        if(position > 0) {
                            Notificacao notificacao = mAdapter.get(position - 1);
                            if(notificacao.getTipo().equals(Notificacao.Tipo.SOLICITACAO)){

                                NotificacaoDialogFragment.newInstance(notificacao.getUsuarioId(),
                                                                      notificacao.getMembroId(),
                                                                      notificacao.getNomeRede(),
                                                                   notificacao.getNomeUsuario())
                                        .show(getFragmentManager(),"NOTIFICACAO_DIALOG");
                            }

                        }

                    }
                })
        );


//        mSwipeRefreshLayout.post(new Runnable() {
//            @Override public void run() {
//                mSwipeRefreshLayout.setRefreshing(true);
//            }
//        });
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        try {


            Notificacao n = new Notificacao("Solicitação",sdf.parse("28012015"), Notificacao.Tipo.SOLICITACAO);
            n.setSecao(true);
            n.setUsuarioId("rodrigosol@gmail.com");
            n.setNomeRede("Rede teste");
            n.setNomeUsuario("Rodrigo Sol");
            n.setMembroId(0l);
            n.setTipo(Notificacao.Tipo.SOLICITACAO);
            mAdapter.add(n);

            n = new Notificacao("Novo membro",sdf.parse("28012015"),
                    Html.fromHtml("<b>Amanda Lima</b> foi aceita como membro da rede <b>Ipanema Kings</b>"), Notificacao.Tipo.STATUS);
            n.setTipo(Notificacao.Tipo.STATUS);
            n.setTipoStatus(Notificacao.TipoStatus.NOVO_MEMBRO);
            n.setLido(true);
            mAdapter.add(n);

            n = new Notificacao("Novo Administrador",sdf.parse("27012015"),
                    Html.fromHtml("<b>Henrique Teles</b> te adicionou como novo Administrador da rede <b>Ipanema Kings</b>"), Notificacao.Tipo.STATUS);
            n.setSecao(true);
            n.setTipo(Notificacao.Tipo.STATUS);
            n.setTipoStatus(Notificacao.TipoStatus.ADMINISTRADOR);
            n.setLido(true);

            mAdapter.add(n);

            n = new Notificacao("Pânico",sdf.parse("27012015"),
                    Html.fromHtml("Acabei de ver quatro capivaras em di..."), Notificacao.Tipo.ALERTA);
            n.setTipo(Notificacao.Tipo.ALERTA);
            n.setTipoAlerta(Notificacao.TipoAlerta.PANICO);


            n = new Notificacao("Pessoa suspeita",sdf.parse("27012015"),
                    Html.fromHtml("Vi 4 sujeitos fantasiados de capivara na r..."), Notificacao.Tipo.ALERTA);
            n.setTipo(Notificacao.Tipo.ALERTA);
            n.setTipoAlerta(Notificacao.TipoAlerta.PESSOA_SUSPEITA);
            n.setLido(false);

            mAdapter.add(n);


        } catch (ParseException e) {
            e.printStackTrace();
        }
        for(int i = 0; i< 100;i++){
            mAdapter.add(new Notificacao("Titulo",new Date(),Html.fromHtml("Texto"), Notificacao.Tipo.STATUS));
        }
        //new MinhasRedesAsyncTask(this).execute();

        return view;

    }

    private void refreshContent() {
        //new MinhasRedesAsyncTask(this).execute();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void error(String descricao) {
        Toast.makeText(getActivity(),descricao,Toast.LENGTH_SHORT).show();
    }

    public void ok(List<RedeDetalhada> result) {
//        mSwipeRefreshLayout.setRefreshing(false);
//        if(result!=null){
//            if(result.size()==0){
//                Toast.makeText(getActivity(), "Você ainda não pertence a nenhuma rede", Toast.LENGTH_SHORT).show();
//            }else{
//                mAdapter.clear();
//                mAdapter.addAll(result);
//                mAdapter.notifyDataSetChanged();
//            }
//        }

    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
