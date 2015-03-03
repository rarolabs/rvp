package rarolabs.com.br.rvp.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.activities.CriarAlertaActivity;
import rarolabs.com.br.rvp.activities.PerfilActivity;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.models.Notificacao;
import rarolabs.com.br.rvp.services.tasks.AceitarSolicitacaoAsyncTask;
import rarolabs.com.br.rvp.utils.ImageUtil;

/**
 * A simple {@link android.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link rarolabs.com.br.rvp.fragments.AlertaDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link rarolabs.com.br.rvp.fragments.AlertaDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlertaDialogFragment extends DialogFragment implements View.OnClickListener {


    private View mView;

    public static AlertaDialogFragment newInstance() {
        AlertaDialogFragment fragment = new AlertaDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }



    public interface NoticeDialogListener {
        public void returnFromDialog(long notificacaoId);
    }

    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;


    public AlertaDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
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
        mView = inflater.inflate(R.layout.alerta_dialog,null);

        mView.findViewById(R.id.pessoa_suspeita).setOnClickListener(this);
        mView.findViewById(R.id.veiculo_suspeito).setOnClickListener(this);
        mView.findViewById(R.id.ausencia).setOnClickListener(this);
        mView.findViewById(R.id.mudanca).setOnClickListener(this);
        mView.findViewById(R.id.panico).setOnClickListener(this);
        mView.findViewById(R.id.incendio).setOnClickListener(this);
        mView.findViewById(R.id.emergencia).setOnClickListener(this);


        builder.setView(mView);

        return builder.create();

    }

    @Override
    public void onClick(View v) {
        Intent i =  new Intent(this.getActivity(), CriarAlertaActivity.class);
        i.putExtra(Constants.EXTRA_TIPO_ALERTA,v.getId());
        startActivity(i);
        this.dismiss();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
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
