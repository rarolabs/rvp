package rarolabs.com.br.rvp.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import rarolabs.com.br.rvp.R;

/**
 * Created by thauanz on 4/28/15.
 */
public class SobreFragment extends Fragment {

    ImageView imgRaro;
    ImageView imgInovapp;
    ImageView imgMc;

    public static SobreFragment newInstance() {
        SobreFragment fragment = new SobreFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public SobreFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sobre, container, false);


        imgRaro = (ImageView) view.findViewById(R.id.icon_raro);
        imgInovapp = (ImageView) view.findViewById(R.id.icon_inovapps);
        imgMc = (ImageView) view.findViewById(R.id.icon_MC);

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


        return view;

    }
}
