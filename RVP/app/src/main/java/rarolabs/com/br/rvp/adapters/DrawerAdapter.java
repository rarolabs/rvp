package rarolabs.com.br.rvp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.models.drawer.DrawerItem;

/**
 * Created by rodrigosol on 1/14/15.
 */
public class DrawerAdapter extends ArrayAdapter<DrawerItem> {

    private final Context context;
    private final DrawerItem[] values;

    private View.OnClickListener mOnClickListener;

    public DrawerAdapter(Context context, DrawerItem[] values) {
        super(context,R.layout.drawer_item,values);
        this.context = context;
        this.values = values;
        Log.d("Drawer", "Size:" + values.length);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.drawer_item, parent, false);


        ImageView icon = (ImageView) rowView.findViewById(R.id.drawer_item_icon);
        TextView label = (TextView) rowView.findViewById(R.id.drawer_item_text);
        String labelText = context.getResources().getString(values[position].getLabel());
        if(values[position].getSize() > 0){
            labelText+= " ("+values[position].getSize()+")";
        }
        label.setText(labelText);

        if(values[position].isChecked()) {
            icon.setImageResource(values[position].getSelectedIcon());
            label.setTextColor(context.getResources().getColor(R.color.drawer_item_color_selected));
            rowView.setBackgroundColor(context.getResources().getColor(R.color.drawer_item_bg));
        }else{
            icon.setImageResource(values[position].getIcon());
            label.setTextColor(context.getResources().getColor(R.color.drawer_item_color));
            rowView.setBackgroundColor(context.getResources().getColor(R.color.white));
        }


        return rowView;
    }




}
