package rarolabs.com.br.rvp.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.MapFragment;

import java.util.ArrayList;

import br.com.rarolabs.rvp.api.rvpAPI.model.GeoqueryResponder;
import rarolabs.com.br.rvp.R;

import rarolabs.com.br.rvp.activities.RedeActivity;
import rarolabs.com.br.rvp.adapters.BuscaRedeAdapter;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.listeners.RecyclerItemClickListener;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class GeoqueryResponderFragment extends Fragment implements AbsListView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private BuscaRedeAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;

    // TODO: Rename and change types of parameters
    public static GeoqueryResponderFragment newInstance(String param1, String param2) {
        GeoqueryResponderFragment fragment = new GeoqueryResponderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public BuscaRedeAdapter getAdapter(){
        return mAdapter;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GeoqueryResponderFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        // TODO: Change Adapter to display your content
        mAdapter = new BuscaRedeAdapter(new ArrayList<GeoqueryResponder>());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_geoqueryresponder, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.lista_redes_recycler_view);

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
                        GeoqueryResponder geo = getAdapter().get(position);
                        Intent i = new Intent(GeoqueryResponderFragment.this.getActivity(),RedeActivity.class);
                        i.putExtra(Constants.EXTRA_ID_REDE,geo.getIdRede());
                        i.putExtra(Constants.EXTRA_NOME_REDE,geo.getNomeRede());
                        i.putExtra(Constants.EXTRA_ENDERECO_REDE,"Nao sei ainda");
                        i.putExtra(Constants.EXTRA_NOME_ADMIN,geo.getNomeAdministrador());
                        i.putExtra(Constants.EXTRA_ULTIMA_ATIVIDADE,geo.getUltimaAtividade().toString());
                        i.putExtra(Constants.EXTRA_QUANTIDADE_MEMBROS,geo.getQuantidadeMembros());
                        i.putExtra(Constants.EXTRA_LATITUDE,geo.getLatitude());
                        i.putExtra(Constants.EXTRA_LONGITUDE,geo.getLongitude());
                        startActivity(i);
                        Toast.makeText(getActivity(),"Clicado:" + position,Toast.LENGTH_SHORT).show();
                    }
                })
        );



        return view;
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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(),"Clicado",Toast.LENGTH_SHORT).show();
        if (null != mListener) {
            Toast.makeText(getActivity(),"Clicado",Toast.LENGTH_SHORT).show();
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            //mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).getIdRede().toString());
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
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
        public void onFragmentInteraction(String id);
    }


}
