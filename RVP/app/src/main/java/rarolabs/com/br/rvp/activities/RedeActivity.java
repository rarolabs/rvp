package rarolabs.com.br.rvp.activities;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;

import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.services.tasks.GoogleMapsThumbAsyncTask;

public class RedeActivity extends Activity {


    private ImageView thumb;
    private FloatingActionButton entrarNaRede;
    private SharedPreferences settings;
    private String email;
    private long redeId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rede);

        if(getActionBar()!=null) {
            getActionBar().hide();
        }
        Intent i = getIntent();
        Bundle extras = i.getExtras();


        Log.d("Rede", "NomeRede:" + extras.getString(WelcomeActivity.EXTRA_NOME_REDE) );
        Log.d("Rede", "Endereco:" + extras.getString(WelcomeActivity.EXTRA_ENDERECO_REDE) );
        Log.d("Rede", "NomeAdmin:" + extras.getString(WelcomeActivity.EXTRA_NOME_ADMIN) );
        Log.d("Rede", "UltimaAtividade:" + extras.getString(WelcomeActivity.EXTRA_ULTIMA_ATIVIDADE) );
        Log.d("Rede", "Quantidade:" + extras.getInt(WelcomeActivity.EXTRA_QUANTIDADE_MEMBROS) );
        Log.d("Rede", "Latitude:" + extras.getDouble(WelcomeActivity.EXTRA_LATITUDE) );
        Log.d("Rede", "Longitude:" + extras.getDouble(WelcomeActivity.EXTRA_LONGITUDE) );

        ((TextView) findViewById(R.id.nome_rede)).setText(extras.getString(WelcomeActivity.EXTRA_NOME_REDE));
        ((TextView) findViewById(R.id.endereco_rede)).setText(extras.getString(WelcomeActivity.EXTRA_ENDERECO_REDE));
        ((TextView) findViewById(R.id.nome_administrador)).setText(extras.getString(WelcomeActivity.EXTRA_NOME_ADMIN));
        ((TextView) findViewById(R.id.ultima_atividade)).setText(extras.getString(WelcomeActivity.EXTRA_ULTIMA_ATIVIDADE));
        ((TextView) findViewById(R.id.quantidade_membros)).setText(extras.getInt(WelcomeActivity.EXTRA_QUANTIDADE_MEMBROS) + " membros");

        redeId = extras.getLong(WelcomeActivity.EXTRA_ID_REDE,0l);



        entrarNaRede  = (FloatingActionButton) findViewById(R.id.entrar_na_rede);
        entrarNaRede.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entrar();
            }
        });

        thumb = (ImageView) findViewById(R.id.thumb);

        Double[] location = new Double[2];
        location[0] = extras.getDouble(WelcomeActivity.EXTRA_LATITUDE);
        location[1] = extras.getDouble(WelcomeActivity.EXTRA_LONGITUDE);
        new GoogleMapsThumbAsyncTask(RedeActivity.this).execute(location);

        settings = getSharedPreferences("RVP", 0);






    }

    private void entrar() {
        Intent i = new Intent(RedeActivity.this,CadastroActivity.class);
        PendingIntent pendingIntent =
                TaskStackBuilder.create(this)
                        // add all of DetailsActivity's parents to the stack,
                        // followed by DetailsActivity itself
                        .addNextIntentWithParentStack(i)
                        .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentIntent(pendingIntent);
        i.putExtra(WelcomeActivity.EXTRA_ID_REDE,redeId);
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
