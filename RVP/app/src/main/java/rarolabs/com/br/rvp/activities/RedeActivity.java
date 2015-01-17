package rarolabs.com.br.rvp.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.melnykov.fab.FloatingActionButton;

import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.services.BuscaRedesAsyncTask;
import rarolabs.com.br.rvp.services.GoogleMapsThumbAsyncTask;

public class RedeActivity extends Activity {

    private ImageView thumb;
    private FloatingActionButton entrarNaRede;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getActionBar()!=null) {
            getActionBar().hide();
        }

        setContentView(R.layout.activity_rede);
        entrarNaRede  = (FloatingActionButton) findViewById(R.id.entrar_na_rede);
        entrarNaRede.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entrar();
            }
        });

        thumb = (ImageView) findViewById(R.id.thumb);
        Object[] params = {new Double("1.0"),new Double("1.0")};
        new GoogleMapsThumbAsyncTask(RedeActivity.this).execute(params);




    }

    private void entrar() {
        Intent i = new Intent(RedeActivity.this,CadastroActivity.class);
        startActivity(i);
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
