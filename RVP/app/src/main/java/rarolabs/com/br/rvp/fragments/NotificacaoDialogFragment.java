package rarolabs.com.br.rvp.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
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

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.activities.MainActivity;
import rarolabs.com.br.rvp.activities.PerfilActivity;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.models.Notificacao;
import rarolabs.com.br.rvp.services.tasks.AceitarSolicitacaoAsyncTask;
import rarolabs.com.br.rvp.services.tasks.DeixarRedeAsyncTask;
import rarolabs.com.br.rvp.services.tasks.TornarMembroAsyncTask;
import rarolabs.com.br.rvp.utils.ImageUtil;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NotificacaoDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NotificacaoDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificacaoDialogFragment extends DialogFragment implements CompoundButton.OnCheckedChangeListener {

    private static final String ARG_USER_ID = "user_id";
    private static final String ARG_MEMBRO_ID = "membro_id";
    private static final String ARG_NOME_REDE = "nome_rede";
    private static final String ARG_NOME_USER = "nome_user";
    private static final String ARG_NOTIFICACAO_ID = "notificacao_id";
    private static final String ARG_AVATAR = "avatar";
    private static final String ARG_AVATAR_BLUR = "avatar_blur";

    private String mUserId;
    private Long mMembroId;
    private String mNomeRede;
    private String mNomeUser;
    private long notificacaoId;


    private TextView mDescricao;
    private SwitchCompat mTornarAdministrador;
    private SwitchCompat mTornarAutoridade;
    private Button mCancelar;
    private Button mTornarMembro;
    private View mView;
    private ProgressDialog progress;
    private long mNotificacaoId;
    private String mAvatar;
    private String mAvatarBlur;
    private TextView mLabelTornarAdministrador;
    private TextView mLabelTornarAutoridade;

    public static NotificacaoDialogFragment newInstance(String userId, Long membroId, String nomeRede,
                                                        String nomeUsuario,Long notificacaoId, String avatar,
                                                        String avatarBlur) {
        NotificacaoDialogFragment fragment = new NotificacaoDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER_ID, userId);
        args.putLong(ARG_MEMBRO_ID, membroId);
        args.putString(ARG_NOME_REDE, nomeRede);
        args.putString(ARG_NOME_USER, nomeUsuario);
        args.putLong(ARG_NOTIFICACAO_ID,notificacaoId);
        args.putString(ARG_AVATAR, avatar);
        args.putString(ARG_AVATAR_BLUR, avatarBlur);
        fragment.setArguments(args);


        return fragment;
    }


    public interface NoticeDialogListener {
        public void returnFromDialog(long notificacaoId);
    }

    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;


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
            mNotificacaoId = getArguments().getLong(ARG_NOTIFICACAO_ID);
            mAvatar = getArguments().getString(ARG_AVATAR);
            mAvatarBlur = getArguments().getString(ARG_AVATAR_BLUR);
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
        mDescricao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perfilDetalhado();
            }
        });

        mLabelTornarAdministrador = (TextView) mView.findViewById(R.id.label_tornar_administrador);
        mTornarAdministrador = (SwitchCompat) mView.findViewById(R.id.tornar_administrador);


        mLabelTornarAutoridade = (TextView) mView.findViewById(R.id.label_tornar_autoridade);
        mTornarAutoridade = (SwitchCompat) mView.findViewById(R.id.tornar_autoridade);
        mCancelar = (Button) mView.findViewById(R.id.cancelar);
        mTornarMembro = (Button) mView.findViewById(R.id.tornar_membro);

        if(mAvatar!=null){
            ImageUtil.loadIconAssync(mAvatar, (ImageView) mView.findViewById(R.id.profile_image),164);
        }

        if(mAvatarBlur!=null){
            ImageUtil.loadIconAssync(mAvatarBlur,  mView.findViewById(R.id.profile_image_bg),216);
        }

        mCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();
            }
        });

        mTornarMembro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tornarMembro();
            }
        });

        mTornarAdministrador.setOnCheckedChangeListener(this);
        mTornarAutoridade.setOnCheckedChangeListener(this);


        builder.setView(mView);

        return builder.create();

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView.getId() == R.id.tornar_administrador){
            mLabelTornarAdministrador.setTextColor(getResources().getColor(getLabelColor(isChecked)));
        }else{
            mLabelTornarAutoridade.setTextColor(getResources().getColor(getLabelColor(isChecked)));
        }

    }

    private int getLabelColor(boolean isChecked) {
        return isChecked ? R.color.material_green_500 : R.color.fonte_busca_rede_bairro ;
    }

    private void perfilDetalhado() {
        Intent i = new Intent(NotificacaoDialogFragment.this.getActivity(), PerfilActivity.class);
        i.putExtra(Constants.EXTRA_MEMBRO_ID,mMembroId);
        i.putExtra(Constants.EXTRA_NOTIFICACAO_ID,mNotificacaoId);
        startActivity(i);
        this.dismiss();
    }

    private void tornarMembro() {
        Log.d("Notificacao", "tornarMembro");
        progress = ProgressDialog.show(NotificacaoDialogFragment.this.getActivity(), NotificacaoDialogFragment.this.getString(R.string.aguarde),
                NotificacaoDialogFragment.this.getString(R.string.enviando_solicitacao, true));

        Object[] params ={
            mMembroId,
            Boolean.valueOf(mTornarAdministrador.isChecked()),
            Boolean.valueOf(mTornarAutoridade.isChecked())
        };

        new AceitarSolicitacaoAsyncTask(NotificacaoDialogFragment.this).execute(params);

    }

    private void cancelar() {
        this.dismiss();
        mListener.returnFromDialog(mNotificacaoId);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.returnFromDialog(mNotificacaoId);
        }
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
    public void onCancel(DialogInterface dialog) {
        Toast.makeText(this.getActivity(),"Fechado",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void error(String descricao) {
        progress.dismiss();
        Toast.makeText(this.getActivity(),descricao,Toast.LENGTH_LONG).show();
    }

    public void ok() {
        progress.dismiss();
        Notificacao notificacao = Notificacao.findById(Notificacao.class,mNotificacaoId);
        notificacao.setRespondida(true);
        notificacao.setAbrivel(false);
        notificacao.save();
        Toast.makeText(this.getActivity(), "Sua solicitação foi enviada com sucesso!", Toast.LENGTH_SHORT).show();
        dismiss();
        mListener.returnFromDialog(mNotificacaoId);
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
