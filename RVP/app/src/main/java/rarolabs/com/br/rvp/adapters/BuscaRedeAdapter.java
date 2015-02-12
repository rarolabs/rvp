package rarolabs.com.br.rvp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.rarolabs.rvp.api.rvpAPI.model.GeoqueryResponder;
import br.com.rarolabs.rvp.api.rvpAPI.model.RedeDetalhada;
import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.activities.WelcomeActivity;
import rarolabs.com.br.rvp.config.RVPApp;
import rarolabs.com.br.rvp.utils.Formarter;

/**
 * Created by rodrigosol on 1/14/15.
 */
public class BuscaRedeAdapter extends RecyclerView.Adapter<BuscaRedeAdapter.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private final List<GeoqueryResponder> myDataset;
    private View.OnClickListener mOnClickListener;

    public BuscaRedeAdapter(List<GeoqueryResponder> myDataset) {
        this.myDataset = myDataset;
    }

    @Override
    public BuscaRedeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {

            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.busca_rede_item, parent, false);
            v.setOnClickListener(mOnClickListener);
            VHItem vh = new VHItem(v);

            vh.distancia = (TextView) v.findViewById(R.id.distancia);
            vh.nome = (TextView) v.findViewById(R.id.nome);
            vh.localizacao = (TextView) v.findViewById(R.id.localizacao);

            return vh;

        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.busca_rede_header, parent, false);
            if(v.getContext() instanceof WelcomeActivity){
                v.setPadding(0, RVPApp.getDesinty().intValue() * 26,0,0);
            }else{
                v.setPadding(0, RVPApp.getDesinty().intValue() * 116,0,0);
            }

            return new VHHeader(v);
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }


    public void addAll(List<GeoqueryResponder> itens)
    {
        if(itens!=null) {
            myDataset.addAll(itens);
        }
    }

    public GeoqueryResponder get(int position) {
        return myDataset.get(position);
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
    public void onBindViewHolder(BuscaRedeAdapter.ViewHolder holder, int position) {
        if (holder instanceof VHItem) {
            GeoqueryResponder geo = myDataset.get(position - 1);
            ((VHItem)holder).distancia.setText(Formarter.distanciaHumana(geo.getDistance()));
            ((VHItem)holder).nome.setText(geo.getNomeRede());
            ((VHItem)holder).localizacao.setText("Mangabeiras, Belo Horizonte");

        }

    }

    @Override
    public int getItemCount() {
        return myDataset.size() + 1;
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
        public TextView distancia;
        public TextView nome;
        public TextView localizacao;

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
