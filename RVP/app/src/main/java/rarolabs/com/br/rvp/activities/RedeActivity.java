package rarolabs.com.br.rvp.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;

import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.services.BuscaRedesAsyncTask;
import rarolabs.com.br.rvp.services.GoogleMapsThumbAsyncTask;

public class RedeActivity extends Activity {

    private ImageView thumb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rede);
        thumb = (ImageView) findViewById(R.id.thumb);
        Object[] params = {new Double("1.0"),new Double("1.0")};
        new GoogleMapsThumbAsyncTask(RedeActivity.this).execute(params);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rede, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setThumb(Bitmap result) {
        thumb.setImageBitmap(result);
    }
}
