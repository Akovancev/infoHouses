package com.jifisher.infohouses.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.jifisher.infohouses.Adapters.RoomsAdapter;
import com.jifisher.infohouses.Databases.DBHouses;
import com.jifisher.infohouses.MainActivity;
import com.jifisher.infohouses.R;
import com.jifisher.infohouses.SupportClasses.House;
import com.jifisher.infohouses.SupportClasses.Room;
import com.lespinside.simplepanorama.view.SphericalView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class PanoramaFragment extends Fragment {

    View view;

    Context context;
    private static final String ARG_ID = "id";
    private static final String ARG_ROOM = "room";

    House house;
    String room;
    int pos=0;
    ArrayList<Room> rooms;
    private SphericalView sphericalView;

    @SuppressWarnings("unused")
    public static PanoramaFragment newInstance(int id, String room) {
        PanoramaFragment fragment = new PanoramaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        args.putString(ARG_ROOM, room);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            house = new DBHouses(getContext()).getHouse(getArguments().getInt(ARG_ID));
            room = getArguments().getString(ARG_ROOM);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_panorama, container, false);
        initialize();
        return view;
    }

    public void initialize() {
        sphericalView = view.findViewById(R.id.spherical_view);

        int i = 0;
        while (!house.rooms.get(i).name.equals(room))
            i++;
         rooms=new ArrayList<>();
        for(int j=0;j<house.rooms.get(i).image.size();j++){
            String str;
            switch (j){
                case 0:
                    str="Кухня";
                    break;
                case 1:
                    str="Коридор";
                    break;
                    default:
                        str=j-1+ " комната";
            }
            rooms.add(new Room(str,house.rooms.get(i).image.get(j)));
        }
        new LoadPanorama().execute(new String());
        final RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(mLayoutManager);
        ((MainActivity)getActivity()).hide();

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int height = display.getHeight();  // deprecated
        recyclerView.setAdapter(new RoomsAdapter(getContext(), (int)(height*0.2f/10f*8f), (int) (height*0.2f), rooms, PanoramaFragment.this, (MainActivity) getActivity()));
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
        recyclerView.setLayoutAnimation(animation);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

    }

    public void switchRoom(int pos){
        this.pos=pos;
        new LoadPanorama().execute(new String());
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

    class LoadPanorama extends AsyncTask<String, Void, Void> {

        private Exception exception;

        protected Void doInBackground(String... urls) {
            Bitmap bt;

            int i = 0;
            while (!house.rooms.get(i).name.equals(room))
                i++;
            try {
                bt = BitmapFactory.decodeStream((InputStream) new URL(rooms.get(pos).image.get(0)).getContent());
                sphericalView.setPanorama(bt, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void feed) {
            // TODO: check this.exception
            // TODO: do something with the feed
        }
    }

}

