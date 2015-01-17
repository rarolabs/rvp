package rarolabs.com.br.rvp.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import rarolabs.com.br.rvp.R;

/**
 * Created by rodrigosol on 1/17/15.
 */
public class IconEditText extends EditText {
    private final Drawable iconNormal;
    private final int target;
    private ImageView img;
    private final Drawable iconFocus;

    public IconEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.IconEditText);


        try {
            iconNormal = a.getDrawable(R.styleable.IconEditText_normalIcon);
            iconFocus = a.getDrawable(R.styleable.IconEditText_focusIcon);
            target = a.getResourceId(R.styleable.IconEditText_target,0);




        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        img = (ImageView) ((Activity)getContext()).findViewById(target);
        if(img != null) {
            if (focused) {
                img.setImageDrawable(iconFocus);
            } else {
                img.setImageDrawable(iconNormal);
            }
        }
    }
}
