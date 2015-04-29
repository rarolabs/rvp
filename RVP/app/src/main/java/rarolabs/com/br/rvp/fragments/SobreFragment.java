package rarolabs.com.br.rvp.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.activities.MainActivity;
import rarolabs.com.br.rvp.activities.SobreActivity;
import rarolabs.com.br.rvp.adapters.MinhasRedesAdapter;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.models.Rede;

/**
 * Created by thauanz on 4/28/15.
 */
public class SobreFragment extends Fragment {

    public static SobreFragment newInstance() {
        SobreFragment fragment = new SobreFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public SobreFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sobre, container, false);
        return view;
    }
}
