package rarolabs.com.br.rvp.adapters.notificacoes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.models.Notificacao;

/**
 * Created by rodrigosol on 1/14/15.
 */
public abstract class NotificacaoBaseAdapter extends RecyclerView.Adapter<NotificacaoBaseAdapter.ViewHolder> {

    protected static final int TYPE_HEADER = 0;
    protected static final int TYPE_ITEM = 1;
    protected static final int TYPE_FOOTER = 2;

    protected static final int PAGE_SIZE = 20;
    protected int currentPage;


    protected final List<Notificacao> myDataset;
    protected final SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy, hh:mm");
    protected final Context context;
    protected View.OnClickListener mOnClickListener;
    protected long totalNotificacoes;
    protected String target;


    public NotificacaoBaseAdapter(Context context) {
        this.target = context.getSharedPreferences("RVP",0).getString(Constants.ACCOUNT,"");
        this.currentPage = 0;
        this.context = context;
        this.myDataset = new ArrayList<Notificacao>();
        this.totalNotificacoes = total();
    }

    public abstract long total();
    public abstract void reflesh();
    protected abstract Collection<Notificacao> load(Notificacao n);

    public void carregarMais() {
        if(myDataset.size() < totalNotificacoes){
            this.currentPage++;
            this.myDataset.addAll(load(myDataset.get(myDataset.size() - 1)));
            notifyDataSetChanged();
        }else{
            Toast.makeText(context,R.string.nao_existe_mais_itens,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public abstract NotificacoesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

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
    public abstract void onBindViewHolder(NotificacoesAdapter.ViewHolder holder, int position);

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
