package rarolabs.com.br.rvp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.rarolabs.rvp.api.rvpAPI.model.RedeDetalhada;
import de.hdodenhof.circleimageview.CircleImageView;
import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.activities.alertas.AlertaActivity;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.models.Mensagem;
import rarolabs.com.br.rvp.models.Notificacao;
import rarolabs.com.br.rvp.models.Rede;



import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.rarolabs.rvp.api.rvpAPI.model.RedeDetalhada;
import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.models.Rede;
import rarolabs.com.br.rvp.utils.ImageUtil;

/**
 * Created by rodrigosol on 1/14/15.
 */

public class DetalhesAlertaAdapter extends RecyclerView.Adapter<DetalhesAlertaAdapter.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    private final Notificacao notificacao;
    private final AlertaActivity context;
    private View.OnClickListener mOnClickListener;

    public DetalhesAlertaAdapter(AlertaActivity context,Notificacao notificacao) {
        this.notificacao = notificacao;
        this.context = context;
    }

    @Override
    public DetalhesAlertaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.alerta_list_item, parent, false);
            VHItem item = new VHItem(v);
            item.avatar = (CircleImageView) v.findViewById(R.id.avatar);
            item.nome = (TextView) v.findViewById(R.id.nome);
            item.data = (TextView) v.findViewById(R.id.data);
            item.texto = (TextView) v.findViewById(R.id.texto);
            return item;

        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.alerta_list_header, parent, false);
            VHHeader header = new VHHeader(v);
            header.avatar = (CircleImageView) v.findViewById(R.id.avatar);
            header.icone = (CircleImageView) v.findViewById(R.id.icone_alerta);
            header.nome = (TextView) v.findViewById(R.id.nome);
            header.data = (TextView) v.findViewById(R.id.data);
            header.subtitulo = (TextView) v.findViewById(R.id.subtitulo);
            header.texto = (TextView) v.findViewById(R.id.texto);


            return header;
        } else if(viewType == TYPE_FOOTER){
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.alerta_list_footer, parent, false);
            final VHFooter footer = new VHFooter(v);
            footer.texto = (EditText) v.findViewById(R.id.texto);
            footer.enviar = (ImageButton) v.findViewById(R.id.enviar);
            footer.enviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.enviarMensagem(footer.texto.getText().toString());
                }
            });
            return footer;

        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }else if(isPositionFooter(position)){
            return TYPE_FOOTER;
        }
       return TYPE_ITEM;

    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }
    private boolean isPositionFooter(int position) {
        return position == getItemCount() - 1;
    }


    @Override
    public void onBindViewHolder(DetalhesAlertaAdapter.ViewHolder holder, int position) {
        if (holder instanceof VHItem) {
            Mensagem m  = notificacao.getMensagens().get(position - 1);
            ((VHItem)holder).nome.setText(m.getNome());
            ((VHItem)holder).texto.setText(m.getTexto());
            ((VHItem)holder).data.setText(m.getData().toString());
        }else if(holder instanceof VHHeader){
            ((VHHeader)holder).nome.setText(notificacao.getNomeUsuario());
            ((VHHeader)holder).texto.setText(notificacao.getDetalhes());
            ((VHHeader)holder).data.setText(notificacao.getData().toString());
            ImageUtil.loadIconAssync(notificacao.getAvatar(), ((VHHeader) holder).avatar, 40);
            ((VHHeader)holder).subtitulo.setText(notificacao.getAlertaSubTitulo(context));
            ((VHHeader)holder).subtitulo.setTextColor(context.getResources().getColor(notificacao.getEsquema().getActionBarColor()));
            String iconResource = notificacao.getIconResource(((VHHeader)holder).icone);
            //Caso o icone seja estatico ele eh carregado automaticamente
            //Caso contrario, ele sera carregado de forma assincrona
            if(iconResource!=null){
                int icone = context.getResources().getIdentifier(notificacao.getAlertaIconResource(((VHHeader) holder).icone), "drawable", context.getPackageName());
                ((VHHeader)holder).icone.setImageResource(icone);
            }
        }else{

        }
    }


    @Override
    public int getItemCount() {
        return notificacao.getMensagens().size() + 2;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class VHItem extends ViewHolder {
        public CircleImageView avatar;
        public TextView nome;
        public TextView texto;
        public TextView data;

        public VHItem(View itemView) {
            super(itemView);
        }
    }

    public static class VHHeader extends ViewHolder {
        public CircleImageView icone;
        public CircleImageView avatar;
        public TextView nome;
        public TextView subtitulo;
        public TextView data;
        public TextView texto;
        public VHHeader(View itemView) {
            super(itemView);
        }

    }

    public static class VHFooter extends ViewHolder {
        public EditText texto;
        public ImageButton enviar;


        public VHFooter(View itemView) {
            super(itemView);
        }
    }


}


