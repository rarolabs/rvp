package rarolabs.com.br.rvp.services.tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import br.com.rarolabs.rvp.api.rvpAPI.model.GeoqueryResponder;
import br.com.rarolabs.rvp.api.rvpAPI.model.Rede;
import rarolabs.com.br.rvp.activities.RedeActivity;
import rarolabs.com.br.rvp.activities.WelcomeActivity;
import rarolabs.com.br.rvp.config.Constants;

public class GoogleMapsThumbAsyncTask extends AsyncTask<Double, Void, Bitmap> {

    private final RedeActivity activity;
    private Context context;

    public GoogleMapsThumbAsyncTask(Context context) {
        this.context = context;
        this.activity = (RedeActivity) context;
    }


    @Override
    protected Bitmap doInBackground(Double... location) {
        //360x240
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics ();
        display.getMetrics(outMetrics);

        Float density  = activity.getResources().getDisplayMetrics().density;


        String URL = "http://maps.google.com/maps/api/staticmap?center=" + location[0] +
                "," + location[1] +
                "&zoom=15&size=360x270&scale="+ density.intValue() +"&sensor=false&";

        URL += addMarkers(location);
        Log.i("URL", URL);

        Bitmap bmp = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet request = new HttpGet(URL);

        InputStream in = null;
        try {
            in = httpclient.execute(request).getEntity().getContent();
            bmp = BitmapFactory.decodeStream(in);
            in.close();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return bmp;

    }

    private String addMarkers(Double[] location) {
        StringBuffer buffer = new StringBuffer();
        Log.d("MARKER","Pontos:" + location.length);
        for(int i = 0; i < location.length; i+=2){
            buffer.append("&markers=color:red%7C"+ location[i]+ ","+ location[i+1]);
        }

        return buffer.toString();
    }

    @Override
    protected void onPostExecute(final Bitmap result) {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                activity.setThumb(result);
            }
        });
    }

}
