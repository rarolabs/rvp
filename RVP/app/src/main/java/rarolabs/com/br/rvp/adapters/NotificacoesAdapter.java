package rarolabs.com.br.rvp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.models.Notificacao;

/**
 * Created by rodrigosol on 1/14/15.
 */
public class NotificacoesAdapter extends RecyclerView.Adapter<NotificacoesAdapter.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;


    private final List<Notificacao> myDataset;
    private final SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy, hh:mm");
    private final Context context;
    private View.OnClickListener mOnClickListener;


    public NotificacoesAdapter(Context context,List<Notificacao> myDataset) {
        this.context = context;
        this.myDataset = myDataset;

    }

    @Override
    public NotificacoesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.notificacao_item, parent, false);
            v.setOnClickListener(mOnClickListener);
            VHItem vh = new VHItem(v);

            vh.icone = (ImageView) v.findViewById(R.id.icone);
            vh.titulo = (TextView) v.findViewById(R.id.titulo);
            vh.texto = (TextView) v.findViewById(R.id.texto);
            vh.secao = (TextView) v.findViewById(R.id.secao);
            vh.data = (TextView) v.findViewById(R.id.data);

            return vh;

        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fake_header, parent, false);

            return new VHHeader(v);
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }


    @Override
    public void onBindViewHolder(NotificacoesAdapter.ViewHolder holder, int position) {
        if (holder instanceof VHItem) {
            Notificacao notificacao = myDataset.get(position - 1);


            if(notificacao.isSecao()){
                ((VHItem)holder).secao.setText(notificacao.getSecao());
                ((VHItem)holder).secao.setVisibility(View.VISIBLE);
            }else{
                ((VHItem)holder).secao.setVisibility(View.GONE);
            }

            ((VHItem)holder).titulo.setText(notificacao.getTitulo());
            if(notificacao.isLido()){
                ((VHItem)holder).titulo.setTextColor(context.getResources().getColor(R.color.fonte_busca_rede));
            }else{
                ((VHItem)holder).titulo.setTextColor(context.getResources().getColor(R.color.titulo_rede));
            }
            ((VHItem)holder).texto.setText(notificacao.getTexto());
            ((VHItem)holder).data.setText(sdfData.format(notificacao.getData()));

             int icone = context.getResources().getIdentifier(notificacao.getIconResource(), "drawable", context.getPackageName());

            ((VHItem)holder).icone.setImageResource(icone);


            //((VHItem)holder).icone.setImageDrawable(notificacao.getIcon());
        }

    }



    @Override
    public int getItemCount() {
        return myDataset.size() + 1;
    }

    public void addAll(List<Notificacao> itens){
        myDataset.addAll(itens);
    }

    public Notificacao get(int position) {
        return myDataset.get(position);
    }

    public void clear() {
        myDataset.clear();
    }

    public void add(Notificacao notificacao) {
        myDataset.add(notificacao);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class VHItem extends ViewHolder {
        public ImageView icone;
        public TextView secao;
        public TextView titulo;
        public TextView texto;
        public TextView data;

        public VHItem(View itemView) {
            super(itemView);
        }
    }

    class VHHeader extends ViewHolder {
        public VHHeader(View itemView) {
            super(itemView);
        }
    }


}
