package rarolabs.com.br.rvp.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.rarolabs.rvp.api.rvpAPI.model.Membro;
import br.com.rarolabs.rvp.api.rvpAPI.model.Rede;
import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.activities.MainActivity;
import rarolabs.com.br.rvp.adapters.MinhasRedesAdapter;
import rarolabs.com.br.rvp.services.tasks.MinhasRedesAsyncTask;
import rarolabs.com.br.rvp.views.Loading;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MinhasRedesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MinhasRedesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MinhasRedesFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private ObservableRecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private MinhasRedesAdapter mAdapter;
    private Loading loading;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    // TODO: Rename and change types and number of parameters
    public static MinhasRedesFragment newInstance(String param1, String param2) {
        MinhasRedesFragment fragment = new MinhasRedesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MinhasRedesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new MinhasRedesAdapter(new ArrayList<Membro>());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_minhas_redes, container, false);
        mRecyclerView = (ObservableRecyclerView) view.findViewById(R.id.lista_minhas_redes_recycler_view);
        MainActivity activity = (MainActivity) getActivity();
        activity.setRecycleView(mRecyclerView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.minhas_redes_swipe_refresh_layout);
        mSwipeRefreshLayout.setProgressViewOffset(false,145,185);
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
//        mRecyclerView.addOnItemTouchListener(
//                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override public void onItemClick(View view, int position) {
//                        GeoqueryResponder geo = getAdapter().get(position);
//                        Intent i = new Intent(GeoqueryResponderFragment.this.getActivity(),RedeActivity.class);
//                        i.putExtra(Constants.EXTRA_ID_REDE,geo.getIdRede());
//                        i.putExtra(Constants.EXTRA_NOME_REDE,geo.getNomeRede());
//                        i.putExtra(Constants.EXTRA_ENDERECO_REDE,"Nao sei ainda");
//                        i.putExtra(Constants.EXTRA_NOME_ADMIN,geo.getNomeAdministrador());
//                        i.putExtra(Constants.EXTRA_ULTIMA_ATIVIDADE,geo.getUltimaAtividade().toString());
//                        i.putExtra(Constants.EXTRA_QUANTIDADE_MEMBROS,geo.getQuantidadeMembros());
//                        i.putExtra(Constants.EXTRA_LATITUDE,geo.getLatitude());
//                        i.putExtra(Constants.EXTRA_LONGITUDE,geo.getLongitude());
//                        startActivity(i);
//                        Toast.makeText(getActivity(), "Clicado:" + position, Toast.LENGTH_SHORT).show();
//                    }
//                })
//        );



        //new MinhasRedesAsyncTask(this).execute();
        List<Membro> redes = new ArrayList<Membro>();
        for(int i=0; i <10; i++){
           Membro m = new Membro();
           m.setNomeRede("Rede " + i);
           m.setStatus("ATIVO");
            redes.add(m);

        }

        mAdapter.addAll(redes);
        mAdapter.notifyDataSetChanged();

        return view;

    }

    private void refreshContent() {
        Log.d("Scroll","Reflesh iniciado");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Membro> redes = new ArrayList<Membro>();
                for (int i = 0; i < 10; i++) {
                    Membro m = new Membro();
                    m.setNomeRede("Rede " + i);
                    m.setStatus("ATIVO");
                    redes.add(m);

                }

                mAdapter.addAll(redes);
                mAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 5000l);

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

    }

    public void ok(List<Membro> result) {

        mAdapter.addAll(result);
        mAdapter.notifyDataSetChanged();

//        if (result.size() == 0) {
//            notFound();
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
