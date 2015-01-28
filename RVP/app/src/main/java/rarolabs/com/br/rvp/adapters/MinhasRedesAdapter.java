package rarolabs.com.br.rvp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.rarolabs.rvp.api.rvpAPI.model.GeoqueryResponder;
import br.com.rarolabs.rvp.api.rvpAPI.model.Membro;
import br.com.rarolabs.rvp.api.rvpAPI.model.Rede;
import br.com.rarolabs.rvp.api.rvpAPI.model.RedeDetalhada;
import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.utils.Formarter;

/**
 * Created by rodrigosol on 1/14/15.
 */
public class MinhasRedesAdapter extends RecyclerView.Adapter<MinhasRedesAdapter.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private final List<RedeDetalhada> myDataset;
    private View.OnClickListener mOnClickListener;

    public MinhasRedesAdapter(List<RedeDetalhada> myDataset) {
        this.myDataset = myDataset;
    }

    @Override
     public MinhasRedesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
          View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rede_item, parent, false);
        v.setOnClickListener(mOnClickListener);
        VHItem vh = new VHItem(v);

        vh.icone = (ImageView) v.findViewById(R.id.icon);
        vh.nome = (TextView) v.findViewById(R.id.nome);
        vh.localizacao = (TextView) v.findViewById(R.id.localizacao);
        vh.status = (TextView) v.findViewById(R.id.status);

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
    public void onBindViewHolder(MinhasRedesAdapter.ViewHolder holder, int position) {
        if (holder instanceof VHItem) {
            RedeDetalhada membro = myDataset.get(position - 1);
            ((VHItem)holder).nome.setText(membro.getNomeRede());
            ((VHItem)holder).localizacao.setText("Mangabeiras, Belo Horizonte");

            if(membro.getStatus().equals("ATIVO")){
                ((VHItem)holder).icone.setImageResource(R.drawable.ic_redes_perfil_aprovada);
                ((VHItem)holder).status.setText("");
            }else{
                ((VHItem)holder).icone.setImageResource(R.drawable.ic_redes_perfil_pendente);
                ((VHItem)holder).status.setText("PENDENTE");
            }
        }

    }

    @Override
    public int getItemCount() {
        return myDataset.size() + 1;
    }

    public void addAll(List<RedeDetalhada> itens){
        myDataset.addAll(itens);
    }

    public RedeDetalhada get(int position) {
        return myDataset.get(position);
    }

    public void clear() {
        myDataset.clear();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class VHItem extends ViewHolder {
        public ImageView icone;
        public TextView nome;
        public TextView localizacao;
        public TextView status;


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
