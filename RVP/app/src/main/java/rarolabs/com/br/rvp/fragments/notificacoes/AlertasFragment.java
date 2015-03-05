package rarolabs.com.br.rvp.fragments.notificacoes;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rarolabs.com.br.rvp.adapters.notificacoes.AlertasAdapter;
import rarolabs.com.br.rvp.adapters.notificacoes.NotificacoesAdapter;

public class AlertasFragment extends NotificacaoBaseFragment {

    public static AlertasFragment newInstance() {
        AlertasFragment fragment = new AlertasFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        setAdapter( new AlertasAdapter(AlertasFragment.this.getActivity()));
        return super.onCreateView(inflater,container,savedInstanceState);
    }

}
