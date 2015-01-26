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
import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.utils.Formarter;

/**
 * Created by rodrigosol on 1/14/15.
 */
public class MinhasRedesAdapter extends RecyclerView.Adapter<MinhasRedesAdapter.ViewHolder> {

    private final List<Membro> myDataset;
    private View.OnClickListener mOnClickListener;

    public MinhasRedesAdapter(List<Membro> myDataset) {
        this.myDataset = myDataset;
    }

    @Override
    public MinhasRedesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rede_item, parent, false);
        v.setOnClickListener(mOnClickListener);
        ViewHolder vh = new ViewHolder(v);

        vh.icone = (ImageView) v.findViewById(R.id.icon);
        vh.nome = (TextView) v.findViewById(R.id.nome);
        vh.localizacao = (TextView) v.findViewById(R.id.localizacao);
        vh.status = (TextView) v.findViewById(R.id.status);

        return vh;
    }

    @Override
    public void onBindViewHolder(MinhasRedesAdapter.ViewHolder holder, int position) {
        Membro membro = myDataset.get(position);

        holder.nome.setText(membro.getNomeRede());
        holder.localizacao.setText("Mangabeiras, Belo Horizonte");
        if(membro.getStatus().equals("ATIVO")){
            holder.icone.setImageResource(R.drawable.ic_redes_perfil_aprovada);
        }else{
            holder.icone.setImageResource(R.drawable.ic_redes_perfil_pendente);
            holder.status.setText("PENDENTE");
        }


    }

    @Override
    public int getItemCount() {
        return myDataset.size();
    }

    public void addAll(List<Membro> itens){
        myDataset.addAll(itens);
    }

    public Membro get(int position) {
        return myDataset.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView icone;
        public TextView nome;
        public TextView localizacao;
        public TextView status;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
