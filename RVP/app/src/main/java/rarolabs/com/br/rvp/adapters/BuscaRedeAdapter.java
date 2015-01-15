package rarolabs.com.br.rvp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.rarolabs.rvp.api.rvpAPI.model.GeoqueryResponder;
import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.utils.Formarter;

/**
 * Created by rodrigosol on 1/14/15.
 */
public class BuscaRedeAdapter extends RecyclerView.Adapter<BuscaRedeAdapter.ViewHolder> {

    private final List<GeoqueryResponder> myDataset;
    private View.OnClickListener mOnClickListener;

    public BuscaRedeAdapter(List<GeoqueryResponder> myDataset) {
        this.myDataset = myDataset;
    }

    @Override
    public BuscaRedeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.busca_rede_item, parent, false);
        v.setOnClickListener(mOnClickListener);
        ViewHolder vh = new ViewHolder(v);

        vh.distancia = (TextView) v.findViewById(R.id.distancia);
        vh.nome = (TextView) v.findViewById(R.id.nome);
        vh.localizacao = (TextView) v.findViewById(R.id.localizacao);

        return vh;
    }

    @Override
    public void onBindViewHolder(BuscaRedeAdapter.ViewHolder holder, int position) {
        GeoqueryResponder geo = myDataset.get(position);
        holder.distancia.setText(Formarter.distanciaHumana(geo.getDistance()));
        holder.nome.setText(geo.getNomeRede());
        holder.localizacao.setText("Mangabeiras, Belo Horizonte");
    }

    @Override
    public int getItemCount() {
        return myDataset.size();
    }

    public void addAll(List<GeoqueryResponder> itens){
        myDataset.addAll(itens);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView distancia;
        public TextView nome;
        public TextView localizacao;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
