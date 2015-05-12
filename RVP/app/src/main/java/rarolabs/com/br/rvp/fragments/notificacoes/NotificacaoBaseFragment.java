package rarolabs.com.br.rvp.fragments.notificacoes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;

import java.util.List;

import br.com.rarolabs.rvp.api.rvpAPI.model.RedeDetalhada;
import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.activities.RVPActivity;
import rarolabs.com.br.rvp.activities.alertas.AlertaActivity;
import rarolabs.com.br.rvp.activities.MainActivity;
import rarolabs.com.br.rvp.adapters.notificacoes.NotificacaoBaseAdapter;
import rarolabs.com.br.rvp.adapters.notificacoes.NotificacoesAdapter;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.config.RVPApp;
import rarolabs.com.br.rvp.fragments.NotificacaoDialogFragment;
import rarolabs.com.br.rvp.listeners.RecyclerItemClickListener;
import rarolabs.com.br.rvp.models.Notificacao;
import rarolabs.com.br.rvp.views.Loading;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link rarolabs.com.br.rvp.fragments.MinhasRedesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link rarolabs.com.br.rvp.fragments.MinhasRedesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificacaoBaseFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private ObservableRecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private NotificacaoBaseAdapter mAdapter;
    private Loading loading;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String currentUser;


    public NotificacaoBaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = getActivity().getSharedPreferences("RVP", 0).getString(Constants.ACCOUNT, null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notificacoes, container, false);


        mRecyclerView = (ObservableRecyclerView) view.findViewById(R.id.notificacoes_recycler_view);
        MainActivity activity = (MainActivity) getActivity();
        activity.setRecycleView(mRecyclerView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.notificacoes_swipe_refresh_layout);
        mSwipeRefreshLayout.setProgressViewOffset(false, RVPApp.getDesinty().intValue() * 130, RVPApp.getDesinty().intValue() * 170);
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
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (position > mAdapter.datasetSize()) {
                            mAdapter.carregarMais();

                        } else if (position > 0) {
                            Notificacao notificacao = mAdapter.get(position - 1);
                            if (notificacao.getTipo().equals(Notificacao.Tipo.SOLICITACAO)) {
                                if(notificacao.isAbrivel()) {
                                    NotificacaoDialogFragment.newInstance(notificacao.getUsuarioId(),
                                            notificacao.getMembroId(),
                                            notificacao.getNomeRede(),
                                            notificacao.getNomeUsuario(),
                                            notificacao.getId(),
                                            notificacao.getAvatar(),
                                            notificacao.getAvatarBlur()
                                    ).show(getFragmentManager(), "NOTIFICACAO_DIALOG");
                                }else{
                                    Toast.makeText(NotificacaoBaseFragment.this.getActivity(),R.string.notificacao_ja_respondida,Toast.LENGTH_SHORT).show();
                                }
                            }else if(notificacao.getTipo().equals(Notificacao.Tipo.ALERTA)){
                                Intent i =  new Intent(NotificacaoBaseFragment.this.getActivity(), AlertaActivity.class);
                                i.putExtra(Constants.EXTRA_TIPO_ALERTA_STRING,notificacao.getTipoAlerta().toString());
                                i.putExtra(Constants.EXTRA_NOTIFICACAO_ID,notificacao.getId());
                                startActivity(i);
                            }
                        }
                    }
                })
        );

        mSwipeRefreshLayout.post(new Runnable() {
            @Override public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });


        mRecyclerView.setAdapter(mAdapter);

        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                refreshContent();
            }
        }.execute();

        return view;

    }

    public void refreshContent() {
        mAdapter.reflesh();
        mSwipeRefreshLayout.setRefreshing(false);
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
        Toast.makeText(getActivity(), descricao, Toast.LENGTH_SHORT).show();
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

    public void marcarTodasComoLidas() {
        Notificacao.marcarTodasNotificacoesComoLidas(currentUser);
        ((NotificacoesAdapter)mRecyclerView.getAdapter()).reflesh();
        Toast.makeText(this.getActivity(),R.string.notificacoes_marcadas_como_lidas,Toast.LENGTH_SHORT).show();

    }

    public void excluirTodasNotificacoes() {
        new AlertDialog.Builder(this.getActivity())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.apagar_todas_notificacoes)
                .setMessage(R.string.tem_certeza)
                .setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Notificacao.excluirTodasNotificacoes(currentUser);
                        ((NotificacoesAdapter)mRecyclerView.getAdapter()).reflesh();

                        Toast.makeText(NotificacaoBaseFragment.this.getActivity(),R.string.notificacoes_excluidas,Toast.LENGTH_SHORT).show();
                    }

                })
                .setNegativeButton(R.string.nao, null)
                .show();
    }

    public void setAdapter(NotificacaoBaseAdapter adapter) {
        this.mAdapter = adapter;
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
