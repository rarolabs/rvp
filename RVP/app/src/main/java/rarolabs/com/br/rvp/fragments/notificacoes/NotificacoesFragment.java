package rarolabs.com.br.rvp.fragments.notificacoes;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rarolabs.com.br.rvp.adapters.notificacoes.NotificacoesAdapter;

public class NotificacoesFragment extends NotificacaoBaseFragment {


    public static NotificacoesFragment newInstance() {
        NotificacoesFragment fragment = new NotificacoesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        setAdapter( new NotificacoesAdapter(NotificacoesFragment.this.getActivity()));
        return super.onCreateView(inflater,container,savedInstanceState);
    }

}
