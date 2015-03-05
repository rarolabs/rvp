package rarolabs.com.br.rvp.adapters.notificacoes;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collection;

import de.hdodenhof.circleimageview.CircleImageView;
import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.models.Notificacao;

/**
 * Created by rodrigosol on 1/14/15.
 */
public class AlertasAdapter extends NotificacaoBaseAdapter {


    public AlertasAdapter(Context context) {
        super(context);
    }

    public void reflesh(){
        super.myDataset.clear();
        super.myDataset.addAll(load(null));
        notifyDataSetChanged();
    }

    @Override
    protected Collection<Notificacao> load(Notificacao n) {
        return Notificacao.getAlertas(currentPage * PAGE_SIZE, PAGE_SIZE, target, n);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.alerta_item, parent, false);
            v.setOnClickListener(mOnClickListener);
            VHItem vh = new VHItem(v);

            vh.icone = (CircleImageView) v.findViewById(R.id.icone);
            vh.titulo = (TextView) v.findViewById(R.id.titulo);
            vh.texto = (TextView) v.findViewById(R.id.texto);
            vh.secao = (TextView) v.findViewById(R.id.secao);
            vh.data = (TextView) v.findViewById(R.id.data);
            vh.nome = (TextView) v.findViewById(R.id.nome);

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
    public void onBindViewHolder(ViewHolder holder, int position) {
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
                ((VHItem)holder).titulo.setTypeface(Typeface.DEFAULT);
            }else{
                ((VHItem)holder).titulo.setTextColor(context.getResources().getColor(notificacao.getEsquema().getActionBarColor()));
                ((VHItem)holder).titulo.setTypeface(Typeface.DEFAULT_BOLD);
            }
            Spanned texto = notificacao.getTexto(context);
            if(texto.length() > 0){
                ((VHItem)holder).texto.setText(texto);
            }else{
                ((VHItem)holder).texto.setText("");
            }
            ((VHItem)holder).nome.setText(notificacao.getUltimoComentario().getNome());

            ((VHItem)holder).data.setText(sdfData.format(notificacao.getData()));

            String iconResource = notificacao.getIconResource(((VHItem)holder).icone);
            //Caso o icone seja estatico ele eh carregado automaticamente
            //Caso contrario, ele sera carregado de forma assincrona
            if(iconResource!=null){
                int icone = context.getResources().getIdentifier(notificacao.getIconResource(((VHItem) holder).icone), "drawable", context.getPackageName());
                ((VHItem)holder).icone.setImageResource(icone);
            }
            notificacao.autoLido();

            //((VHItem)holder).icone.setImageDrawable(notificacao.getIcon());
        }

    }



    public static class VHItem extends ViewHolder {
        public CircleImageView icone;
        public TextView secao;
        public TextView titulo;
        public TextView texto;
        public TextView data;
        public TextView nome;

        public VHItem(View itemView) {
            super(itemView);
        }
    }

    @Override
    public long total() {
        return Notificacao.totalNotificacoesNaoLidas(target);
    }


}
