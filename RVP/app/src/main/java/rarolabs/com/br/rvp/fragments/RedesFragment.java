package rarolabs.com.br.rvp.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import br.com.rarolabs.rvp.api.rvpAPI.model.GeoqueryResponder;
import br.com.rarolabs.rvp.api.rvpAPI.model.Membro;
import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.activities.CadastroActivity;
import rarolabs.com.br.rvp.activities.MainActivity;
import rarolabs.com.br.rvp.activities.RedeActivity;
import rarolabs.com.br.rvp.adapters.BuscaRedeAdapter;
import rarolabs.com.br.rvp.adapters.MinhasRedesAdapter;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.listeners.RecyclerItemClickListener;
import rarolabs.com.br.rvp.services.tasks.MinhasRedesAsyncTask;

public class RedesFragment extends Fragment {

    private MinhasRedesAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;

    public static RedesFragment newInstance(String param1, String param2) {
        RedesFragment fragment = new RedesFragment();
        return fragment;
    }

    public RedesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_redes, container, false);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.nova_rede);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                novaRede();
            }
        });


        FragmentManager fragmentManager = getFragmentManager();
        MinhasRedesFragment  f = new MinhasRedesFragment();

        fragmentManager.beginTransaction()
                .replace(R.id.busca_rede_container, f, "MINHAS_REDES")
                .commit();

        return view;

    }

    private void novaRede() {
        Intent i = new Intent(getActivity(),CadastroActivity.class);
        i.putExtra("NOVA_REDE",true);
        startActivity(i);

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void buscar() {
            FragmentTransaction fragTrans = getFragmentManager().beginTransaction();

            BuscaRedeFragment myFragment = new BuscaRedeFragment();//my custom fragment
            fragTrans.replace(R.id.busca_rede_container, myFragment);
            fragTrans.addToBackStack("BUSCA_REDE");
            fragTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragTrans.commit();
    }



}



