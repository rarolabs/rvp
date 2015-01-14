package rarolabs.com.br.rvp.activities.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.rarolabs.rvp.api.rvpAPI.model.GeoqueryResponder;
import rarolabs.com.br.rvp.R;

/**
 * Created by rodrigosol on 1/14/15.
 */
public class BuscaRedeAdapter extends ArrayAdapter<GeoqueryResponder> {

    private final Context context;
    private final List<GeoqueryResponder> objects;

    public BuscaRedeAdapter(Context context, int resource, List<GeoqueryResponder> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.busca_rede_item, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.title);
        //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        textView.setText(objects.get(position).getNomeRede());
        // change the icon for Windows and iPhone
//        String s = values[position];
//        if (s.startsWith("iPhone")) {
//            imageView.setImageResource(R.drawable.no);
//        } else {
//            imageView.setImageResource(R.drawable.ok);
//        }

        return rowView;
    }
}
