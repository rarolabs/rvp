package rarolabs.com.br.rvp.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import br.com.rarolabs.rvp.api.rvpAPI.model.GeoqueryResponder;
import rarolabs.com.br.rvp.R;

/**
 * Created by rodrigosol on 1/27/15.
 */
public class Loading extends LinearLayout {
    private final Drawable iconLoading;
    private final Drawable iconError;
    private final String textLoading;
    private final String textError;
    private final int target;
    private final View viewTarget;
    private TextView statusBusca;
    private ImageView spinner;

    public Loading(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IconEditText);

        try {
            iconLoading = a.getDrawable(R.styleable.Loading_iconLoading);
            iconError = a.getDrawable(R.styleable.Loading_iconError);
            textLoading = a.getString(R.styleable.Loading_textLoading);
            textError = a.getString(R.styleable.Loading_textError);
            target = a.getResourceId(R.styleable.Loading_viewTarget,0);

            statusBusca = new TextView(getContext());
            spinner = new ImageView(getContext());

            this.addView(statusBusca);
            this.addView(spinner);




//                    <ImageView
//
//            android:layout_width="wrap_content"
//            android:layout_height="wrap_content"
//            android:layout_gravity="center"
//            android:layout_marginTop="48dp"
//            android:id="@+id/loading_icon"
//
//                    />
//
//            <TextView
//            android:layout_width="wrap_content"
//            android:layout_height="wrap_content"
//            android:layout_gravity="center"
//            android:gravity="center"
//            android:layout_marginTop="20dp"
//            android:singleLine="false"
//            android:textAlignment="center"
//            android:id="@+id/loading_status"
//                    />
            viewTarget = ((Activity)getContext()).findViewById(target);

        } finally {
            a.recycle();
        }
    }
    //img = (ImageView) ((Activity)getContext()).findViewById(target);

    public void start(){
        if(viewTarget!=null) {
            viewTarget.setVisibility(View.GONE);
        }
        this.setVisibility(View.VISIBLE);
        spinner.startAnimation(
                AnimationUtils.loadAnimation(getContext(), R.anim.rotate_forever));

    }
    public void ok() {
//        this.setVisibility(View.GONE);
//        ((Activity)getContext()).findViewById(target).setVisibility(View.VISIBLE);
    }

    public void notFound() {
        spinner.clearAnimation();
        spinner.setImageDrawable(iconError);
        statusBusca.setText(textError);
        this.setVisibility(View.VISIBLE);
        statusBusca.startAnimation(
                AnimationUtils.loadAnimation(getContext(), R.anim.bouce));

        this.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //buscar(null);
            }
        });
    }
}
