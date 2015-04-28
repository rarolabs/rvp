package rarolabs.com.br.rvp.fragments;

import android.app.Fragment;
import android.os.Bundle;

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
}
