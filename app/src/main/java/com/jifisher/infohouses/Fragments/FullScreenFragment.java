package com.jifisher.infohouses.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.jifisher.infohouses.MainActivity;
import com.jifisher.infohouses.R;
import com.jifisher.infohouses.SupportClasses.House;
import com.jifisher.infohouses.SupportClasses.Room;
import com.lespinside.simplepanorama.view.SphericalView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;

public class FullScreenFragment extends Fragment {

    View view;

    Context context;

    private static final String ARG_HREF = "href";

    House house;
    String href;
    int pos=0;
    ArrayList<Room> rooms;
    private SphericalView sphericalView;

    @SuppressWarnings("unused")
    public static FullScreenFragment newInstance(String href) {
        FullScreenFragment fragment = new FullScreenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_HREF, href);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            href = getArguments().getString(ARG_HREF);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_image_fullscrean, container, false);
        initialize();
        return view;
    }

    public void initialize() {
        Picasso.get().load(href).fetch(new Callback() {
            @Override
            public void onSuccess() {
                PhotoView imageView=view.findViewById(R.id.image);
                Picasso.get().load(href).into(imageView);
                imageView.setAlpha(0f);
                imageView.animate().alphaBy(1f).setDuration(500).start();
            }

            @Override
            public void onError(Exception e) {

            }

        });
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        ((MainActivity)getActivity()).hide();

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;
        ((MainActivity)getActivity()).show();
        onDestroyOptionsMenu();
        onDestroy();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyOptionsMenu() {
    }


}

