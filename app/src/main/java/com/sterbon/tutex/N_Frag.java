package com.sterbon.tutex;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Utsav on 7/30/2017.
 */

public class N_Frag extends Fragment implements View.OnClickListener {
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle s) {

        View v= inflater.inflate(
                R.layout.notes_frag,
                container,
                false
        );



        CardView maths = (CardView)v.findViewById(R.id.nt1);
        maths.setOnClickListener(this);

        CardView comp = (CardView)v.findViewById(R.id.nt2);
        comp.setOnClickListener(this);

        CardView std = (CardView)v.findViewById(R.id.nt3);
        std.setOnClickListener(this);

        CardView cs = (CardView)v.findViewById(R.id.nt4);
        cs.setOnClickListener(this);

        CardView data = (CardView)v.findViewById(R.id.nt5);
        data.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nt1:
                Intent i = new Intent(getActivity(),GDrive.class);
                i.putExtra("message", "https://drive.google.com/open?id=0B3kKT0tVc-RPSHhjYWJEZEl1X28");
                startActivity(i);
                break;

            case R.id.nt2:
                Intent j = new Intent(getActivity(),GDrive.class);
                j.putExtra("message", "https://drive.google.com/open?id=0B3kKT0tVc-RPYjlDZnVHRXRQcUU");
                startActivity(j);
                break;

            case R.id.nt3:
                Intent k = new Intent(getActivity(),GDrive.class);
                k.putExtra("message", "https://drive.google.com/open?id=0B3kKT0tVc-RPaVlnbTJERnN6b2M");
                startActivity(k);
                break;

            case R.id.nt4:
                Intent l = new Intent(getActivity(),GDrive.class);
                l.putExtra("message", "https://drive.google.com/open?id=0B3kKT0tVc-RPZWk2SjZEcFVYRTQ");
                startActivity(l);
                break;

            case R.id.nt5:
                Intent m = new Intent(getActivity(),GDrive.class);
                m.putExtra("message", "https://drive.google.com/open?id=0B3kKT0tVc-RPSDNWNjdieHJuRFE");
                startActivity(m);
                break;
        }
    }
}


