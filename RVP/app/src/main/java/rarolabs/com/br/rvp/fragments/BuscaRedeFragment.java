package rarolabs.com.br.rvp.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.rarolabs.rvp.api.rvpAPI.model.GeoqueryResponder;
import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.activities.Locable;
import rarolabs.com.br.rvp.listeners.GPSTracker;
import rarolabs.com.br.rvp.services.tasks.BuscaRedesAsyncTask;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BuscaRedeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BuscaRedeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuscaRedeFragment extends Fragment
                               implements Locable,
                                          GeoqueryResponderFragment.OnFragmentInteractionListener
                                          {



    private static final String ARG_MARGIN = "margin";

    private String mMargin;

    private OnFragmentInteractionListener mListener;
    private TextView statusBusca;
    private View loading;
    private ImageView spinner;
    private GeoqueryResponderFragment resultFragment;
    private View resultFragmentView;
    private Button buscarRede;
    private GPSTracker gps;


    public static BuscaRedeFragment newInstance(String margin) {
        BuscaRedeFragment fragment = new BuscaRedeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MARGIN, margin);
        fragment.setArguments(args);
        return fragment;
    }

    public BuscaRedeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resultFragment = new GeoqueryResponderFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_content, resultFragment);
        ft.commit();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_busca_rede, container, false);
        resultFragmentView = view.findViewById(R.id.fragment_content);

        statusBusca = (TextView) view.findViewById(R.id.status_busca);
        loading = view.findViewById(R.id.loading);
        spinner = (ImageView) view.findViewById(R.id.spinner);

        resultFragmentView.setVisibility(View.GONE);


        if (gps == null) {
            gps = new GPSTracker(this);
        }

        // check if GPS enabled
        if (!gps.canGetLocation()) {
            gps.showSettingsAlert();
        } else {
            spinner.startAnimation(
                    AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_forever));

            onLocationChange(gps.getLocation());
        }

        return view;
    }

    @Override
    public void onLocationChange(Location location) {
        if (location !=null && location.getLatitude() != 0) {
            gps.stopUsingGPS();
            buscar(location);
        }
    }

    private void buscar(Location location) {

        if (location == null) {
            location = gps.getLocation();
        }

        SharedPreferences settings = getActivity().getSharedPreferences("RVP", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("USER_LATITUDE",String.valueOf(location.getLatitude()));
        editor.putString("USER_LONGITUDE",String.valueOf(location.getLongitude()));
        editor.commit();


        resultFragmentView.setVisibility(View.GONE);
        statusBusca.setText(R.string.buscando_redes);
        spinner.setImageResource(R.drawable.ic_tutorial_loading);
        spinner.startAnimation(
                AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_forever));

        loading.setVisibility(View.VISIBLE);
        loading.setOnClickListener(null);
        new BuscaRedesAsyncTask(this).execute(location);

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

    @Override
    public void onFragmentInteraction(String id) {

    }

    public void error(String descricao) {
        Toast.makeText(getActivity(),descricao,Toast.LENGTH_SHORT).show();
        notFound();
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public void ok(List<GeoqueryResponder> result) {
        loading.setVisibility(View.GONE);
        resultFragmentView.setVisibility(View.VISIBLE);
        resultFragment.getAdapter().addAll(result);
        resultFragment.getAdapter().notifyDataSetChanged();

        if (result.size() == 0) {
            notFound();
        }
    }

    public void notFound() {

        resultFragmentView.setVisibility(View.GONE);
        spinner.clearAnimation();
        spinner.setImageResource(R.drawable.ic_tutorial_empty);
        statusBusca.setText(R.string.busca_redes_nao_encontradas);
        loading.setVisibility(View.VISIBLE);
        if(this.isVisible()) {
            statusBusca.startAnimation(
                    AnimationUtils.loadAnimation(getActivity(), R.anim.bouce));
        }

        loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscar(null);
            }
        });
    }

}
