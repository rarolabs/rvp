package rarolabs.com.br.rvp.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import rarolabs.com.br.rvp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NotificacaoDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NotificacaoDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificacaoDialogFragment extends DialogFragment {

    private static final String ARG_USER_ID = "user_id";
    private static final String ARG_MEMBRO_ID = "membro_id";
    private static final String ARG_NOME_REDE = "nome_rede";
    private static final String ARG_NOME_USER = "nome_user";

    private String mUserId;
    private Long mMembroId;
    private String mNomeRede;
    private String mNomeUser;


    private OnFragmentInteractionListener mListener;
    private TextView mDescricao;
    private SwitchCompat mTornarAdministrador;
    private SwitchCompat mTornarAutoridade;
    private Button mCancelar;
    private Button mTornarMembro;
    private View mView;

    public static NotificacaoDialogFragment newInstance(String userId, Long membroId, String nomeRede, String nomeUsuario) {
        NotificacaoDialogFragment fragment = new NotificacaoDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER_ID, userId);
        args.putLong(ARG_MEMBRO_ID, membroId);
        args.putString(ARG_NOME_REDE, nomeRede);
        args.putString(ARG_NOME_USER, nomeUsuario);

        fragment.setArguments(args);

        return fragment;
    }

    public NotificacaoDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserId = getArguments().getString(ARG_USER_ID);
            mMembroId = getArguments().getLong(ARG_MEMBRO_ID);
            mNomeRede = getArguments().getString(ARG_NOME_REDE);
            mNomeUser = getArguments().getString(ARG_NOME_USER);
        }
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        mView = inflater.inflate(R.layout.notificacao_dialog,null);


        mDescricao = (TextView) mView.findViewById(R.id.descricao);
        String descricao = String.format(getResources().getString(R.string.descricao_notificacao_solicitacao), mNomeUser, mNomeRede);

        mDescricao.setText(Html.fromHtml(descricao));

        mTornarAdministrador = (SwitchCompat) mView.findViewById(R.id.tornar_administrador);
        mTornarAutoridade = (SwitchCompat) mView.findViewById(R.id.tornar_autoridade);
        mCancelar = (Button) mView.findViewById(R.id.cancelar);
        mTornarMembro= (Button) mView.findViewById(R.id.tornar_administrador);


        mCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();
            }
        });

        mTornarMembro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTornarMembro();
            }
        });

        builder.setView(mView);
        return builder.create();

    }

    private void mTornarMembro() {

    }

    private void cancelar() {
        this.dismiss();
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
