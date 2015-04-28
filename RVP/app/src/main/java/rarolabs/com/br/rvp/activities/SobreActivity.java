package rarolabs.com.br.rvp.activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.activities.alertas.AlertaBaseActivity;

/**
 * Created by thauanz on 4/28/15.
 */
public class SobreActivity extends AlertaBaseActivity {

    ImageView imgRaro;
    ImageView imgInovapp;
    ImageView imgMc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        imgRaro = (ImageView)findViewById(R.id.icon_raro);
        imgInovapp = (ImageView)findViewById(R.id.icon_inovapps);
        imgMc = (ImageView)findViewById(R.id.icon_MC);

        imgRaro.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://rarolabs.com.br/"));
                startActivity(intent);
            }
        });

        imgMc.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.mc.gov.br/"));
                startActivity(intent);
            }
        });

        imgInovapp.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.comunicacoes.gov.br/concurso-inovapps"));
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
