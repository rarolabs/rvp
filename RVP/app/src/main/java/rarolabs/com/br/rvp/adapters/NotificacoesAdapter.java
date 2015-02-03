package rarolabs.com.br.rvp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.models.Notificacao;

/**
 * Created by rodrigosol on 1/14/15.
 */
public class NotificacoesAdapter extends RecyclerView.Adapter<NotificacoesAdapter.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    private static final int PAGE_SIZE = 20;
    private int currentPage;


    private final List<Notificacao> myDataset;
    private final SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy, hh:mm");
    private final Context context;
    private View.OnClickListener mOnClickListener;
    private long totalNotificacoes;


    public NotificacoesAdapter(Context context) {
        this.currentPage = 0;
        this.context = context;
        this.myDataset = new ArrayList<Notificacao>();
        this.totalNotificacoes = Notificacao.totalNotificacoes();
    }

    public void reflesh(){
        this.myDataset.clear();
        this.myDataset.addAll(Notificacao.getNotificacoes(currentPage * PAGE_SIZE, PAGE_SIZE, null));
        notifyDataSetChanged();
    }

    public void carregarMais() {
        if(myDataset.size() < totalNotificacoes){
            this.currentPage++;
            this.myDataset.addAll(Notificacao.getNotificacoes(currentPage * PAGE_SIZE, PAGE_SIZE,
                    myDataset.get(myDataset.size() - 1)));
            notifyDataSetChanged();
        }else{
            Toast.makeText(context,R.string.nao_existe_mais_itens,Toast.LENGTH_SHORT).show();
        }
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

        } else if (viewType == TYPE_FOOTER) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mais_footer, parent, false);
            if(myDataset.size() < totalNotificacoes) {
                v.setVisibility(View.VISIBLE);
            }else{
                v.setVisibility(View.GONE);
            }

            return new VHFooter(v);

        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }


    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            Log.i("MAIS", "Posiiton:" + position + " - HEADER");
            return TYPE_HEADER;
        }else if(isPositionFooter(position)){
            Log.i("MAIS", "Posiiton:" + position + " - FOOTER");
            Log.i("Datasetsize", "Size:" + myDataset.size());
            Log.i("Datasetsize", "Total:" + totalNotificacoes);
            Log.i("Datasetsize", "Visible:" + (myDataset.size() < totalNotificacoes));

            return TYPE_FOOTER;
        }
        Log.i("MAIS", "Posiiton:" + position + " - ITEM");
        return TYPE_ITEM;
    }

    private boolean isPositionFooter(int position) {
        return position > myDataset.size();
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

            ((VHItem)holder).titulo.setText(notificacao.getTitulo(context));
            if(notificacao.isLido()){
                ((VHItem)holder).titulo.setTextColor(context.getResources().getColor(R.color.fonte_busca_rede));
            }else{
                ((VHItem)holder).titulo.setTextColor(context.getResources().getColor(R.color.titulo_rede));
            }
            notificacao.autoLido();

            ((VHItem)holder).texto.setText(notificacao.getTexto(context));
            ((VHItem)holder).data.setText(sdfData.format(notificacao.getData()));

             int icone = context.getResources().getIdentifier(notificacao.getIconResource(), "drawable", context.getPackageName());

            ((VHItem)holder).icone.setImageResource(icone);


            //((VHItem)holder).icone.setImageDrawable(notificacao.getIcon());
        }

    }



    @Override
    public int getItemCount() {
        return myDataset.size() + 2;
    }

    public Notificacao get(int position) {
        return myDataset.get(position);
    }

    public void clear() {
        myDataset.clear();
    }

    public int datasetSize() {
        return myDataset.size();
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

    class VHFooter extends ViewHolder {
        public VHFooter(View itemView) {
            super(itemView);
        }
    }



}
