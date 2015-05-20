package rarolabs.com.br.rvp.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.models.Membro;
import rarolabs.com.br.rvp.models.Rede;

/**
 * Created by thauanz on 4/30/15.
 */
public class ListRedesAdapter extends ArrayAdapter<Rede> {
    private Activity context;
    ArrayList<Rede> data = null;
    public enum Status {ATIVO, INATIVO,AGUARDANDO_APROVACAO,REPROVADO}

    public ListRedesAdapter(Activity context, int resource, ArrayList<Rede> data){
        super(context, resource, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //return super.getView(position, convertView, parent);
        TextView lbl = (TextView) super.getView(position, convertView, parent);
        lbl.setText(getItem(position).getNomeRede());
        return lbl;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        if(row == null){
            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(R.layout.spinner_item, parent, false);
        }
        Rede item = data.get(position);

        if(item != null){   // Parse the data from each object and set it.
            //(item.getStatus() == "ATIVO")
            TextView myCountry = (TextView) row.findViewById(R.id.spinner_item_text);
            if(myCountry != null)
                myCountry.setText(item.getNomeRede());
                Log.d("ListRede", "Nome da rede " + item.getNomeRede() + " " + item.getStatus());
        }
        return row;
    }
}
