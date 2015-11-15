package com.example.manuelseguranavarro.sunshineproject.Detalle;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.manuelseguranavarro.sunshineproject.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetalleActivityFragment extends Fragment {

    public DetalleActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detalle, container, false);

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)){
            String detalle_extra = intent.getStringExtra(Intent.EXTRA_TEXT);
            ((TextView)rootView.findViewById(R.id.textodetalle)).setText(detalle_extra);

        }


        return rootView;
    }
}
