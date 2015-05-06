package rarolabs.com.br.rvp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import br.com.rarolabs.rvp.api.rvpAPI.model.RedeDetalhada;
import de.hdodenhof.circleimageview.CircleImageView;
import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.activities.PerfilActivity;
import rarolabs.com.br.rvp.activities.alertas.AlertaActivity;
import rarolabs.com.br.rvp.adapters.notificacoes.AlertasAdapter;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.models.Comentario;
import rarolabs.com.br.rvp.models.Notificacao;
import rarolabs.com.br.rvp.models.Rede;



import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.api.client.util.DateTime;

import java.util.List;
import java.util.TimeZone;

import javax.xml.datatype.Duration;

import br.com.rarolabs.rvp.api.rvpAPI.model.RedeDetalhada;
import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.models.Rede;
import rarolabs.com.br.rvp.utils.DateUtils;
import rarolabs.com.br.rvp.utils.ImageUtil;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by rodrigosol on 1/14/15.
 */

public class DetalhesAlertaAdapter extends RecyclerView.Adapter<DetalhesAlertaAdapter.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    private final Notificacao notificacao;
    private final AlertaActivity context;
    private Comentario[] cache = new Comentario[]{};
    private int size;

    public DetalhesAlertaAdapter(AlertaActivity context,Notificacao notificacao) {
        this.notificacao = notificacao;
        this.context = context;
        update();

    }

    @Override
    public DetalhesAlertaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.alerta_list_item, parent, false);

            final VHItem item = new VHItem(v);
            item.avatar = (CircleImageView) v.findViewById(R.id.avatar);
            item.nome = (TextView) v.findViewById(R.id.nome);
            item.data = (TextView) v.findViewById(R.id.data);
            item.texto = (TextView) v.findViewById(R.id.texto);
            item.avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positionClick(item.getPosition());
                }
            });
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
            footer.texto = (EditText) v.findViewById(R.id.texto_mensagem);
            footer.enviar = (ImageButton) v.findViewById(R.id.enviar);
            footer.texto.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus){
                        footer.enviar.setPressed(true);
                    }else {
                        Log.d("DetalhesAlertaAdapter","Button not pressed");
                    }
                }
            });
//            footer.texto.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    footer.enviar.setPressed(true);
//                }
//            });
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

    private void positionClick(int position){
       Log.d("Posicao de click", " " + position);
       context.verPerfil(position);
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
            Comentario c  = cache[position - 1];
            ((VHItem)holder).nome.setText(c.getNome());
            ImageUtil.loadIconAssync(c.getAvatar(), ((VHItem) holder).avatar, 40);
            ((VHItem)holder).texto.setText(c.getTexto());
            //((VHItem)holder).data.setText(new DateTime(c.getData()).toString());
            ((VHItem)holder).data.setText(returnTime(new Date(), new Date(c.getData())));

        }else if(holder instanceof VHHeader){
            ((VHHeader)holder).nome.setText(notificacao.getNomeUsuario());
            ((VHHeader)holder).texto.setText(notificacao.getDetalhes());
            //((VHHeader)holder).data.setText(notificacao.getData().toString());

            ((VHHeader)holder).data.setText(returnTime(new Date(), notificacao.getData()));

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
        return size + 2;
    }

    public void update() {
        this.cache = notificacao.getComentarios().toArray(cache);
        this.size = notificacao.getComentarios().size();
        notifyDataSetChanged();

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

    public static String returnTime(Date startTime, Date endTime){
        long mills = startTime.getTime() - endTime.getTime();
        int hrs = (int) (mills/(1000 * 60 * 60));
        int min = (int) (mills/(1000*60)) % 60;

        if((hrs == 0)  && (min == 0)){
            return "agora";
        }else if(hrs == 0){
            return "há " + min + " minutos";
        }else if(hrs < 24){
            return hrs + " h : " + min + " min atrás";
        }else{
            SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy, hh:mm");
            return sdfData.format(endTime);
        }

    }

}



