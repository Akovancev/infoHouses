package com.jifisher.infohouses.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jifisher.infohouses.CursorRecyclerViewAdapter.CursorRecyclerViewAdapter;
import com.jifisher.infohouses.CustomViews.AutoResizeTextView;
import com.jifisher.infohouses.Databases.DBHouses;
import com.jifisher.infohouses.Fragments.HouseFragment;
import com.jifisher.infohouses.Fragments.HousesListFragment;
import com.jifisher.infohouses.Fragments.PanoramaFragment;
import com.jifisher.infohouses.MainActivity;
import com.jifisher.infohouses.R;
import com.jifisher.infohouses.SupportClasses.House;
import com.jifisher.infohouses.SupportClasses.Room;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.ViewHolder> {

    Context mContext;
    int width;
    int heigth;
    ArrayList<Room> rooms;
    HouseFragment houseFragment;
    PanoramaFragment panoramaFragment;
    MainActivity mainActivity;
    House house;

    public RoomsAdapter(Context context, int width, int heigth, ArrayList<Room> rooms, HouseFragment houseFragment, MainActivity mainActivity, House house) {

        mContext = context;
        this.heigth = heigth;
        this.width = width;
        this.house = house;
        this.rooms = rooms;
        this.houseFragment = houseFragment ;
        this.mainActivity = mainActivity;
    }

    public RoomsAdapter(Context context, int width, int heigth, ArrayList<Room> rooms, PanoramaFragment panoramaFragment, MainActivity mainActivity) {

        mContext = context;
        this.heigth = heigth;
        this.width = width;
        this.rooms = rooms;
        this.panoramaFragment = panoramaFragment;
        this.mainActivity = mainActivity;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //public TextView mTextView;
        public ImageView imageView;
        public ImageView imageLike;
        public ImageView imageAdd;
        public AutoResizeTextView adress;

        public ViewHolder(View view) {
            super(view);
            ((CardView)view.findViewById(R.id.card_view)).setPreventCornerOverlap(false);
            imageView = view.findViewById(R.id.houseImage);
            imageLike = view.findViewById(R.id.like);
            imageAdd = view.findViewById(R.id.add);
            adress = view.findViewById(R.id.adress);
            adress.setMinTextSize(0.1f);

        }
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    @Override
    public RoomsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_info_room, parent, false);
        RoomsAdapter.ViewHolder vh = new RoomsAdapter.ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(final RoomsAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.adress.setText(rooms.get(position).name);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params2.height = (int) (0.12f * heigth); //height recycleviewer
        params2.width = width -70; //width recycleviewer
        params2.leftMargin = 0; //width recycleviewer
        params2.topMargin = 0; //width recycleviewer
        viewHolder.itemView.findViewById(R.id.bottomCard).setLayoutParams(params2);

        (viewHolder.imageLike).setVisibility(View.GONE);
        (viewHolder.imageAdd).setVisibility(View.GONE);

        RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params3.height = width - 60; //height recycleviewer
        params3.width = width - 60; //width recycleviewer
        params3.leftMargin = 0; //width recycleviewer
        params3.topMargin = -10; //width recycleviewer
        viewHolder.imageView.setLayoutParams(params3);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        /*params1.height = (int) (params2.height+params3.height+90/density); //height recycleviewer
        params1.width =width-20; //width recycleviewer*/
        params1.leftMargin =  (int) (width / 16f); //width recycleviewer
        params1.topMargin =  (int) (heigth / 16f); //width recycleviewer
        params1.bottomMargin = heigth / 16; //width recycleviewer
        viewHolder.itemView.findViewById(R.id.card_view).setLayoutParams(params1);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        /*params.height = (int) (params1.height+20); //height recycleviewer
        params.width =width; //width recycleviewer*/

        viewHolder.itemView.setLayoutParams(params);
        Picasso.get().load(rooms.get(position).image.get(0))
                .transform(new CropSquareTransformation())
                .placeholder(R.drawable.house)
                .into(viewHolder.imageView);

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {if(panoramaFragment!=null)
                panoramaFragment.switchRoom(position);
            else{
                PanoramaFragment houseFragment = PanoramaFragment.newInstance(house.id, rooms.get(position).name);
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    houseFragment.setEnterTransition(new Fade());

                        houseFragment .setExitTransition(new Fade());
                    mainActivity.getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.container, houseFragment)
                            .addToBackStack(null)
                            .commit();
                } else {
                    mainActivity.getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.container, houseFragment)
                            .addToBackStack(null)
                            .commit();
                }
                }


            }
        });
        viewHolder.itemView.findViewById(R.id.card_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //viewHolder.mTextView.setText(manicure.getName());
    }


    public class CropSquareTransformation implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int size1 = Math.min(size, 200);
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result1 = Bitmap.createBitmap(source, x, y, size, size);
            Bitmap result = Bitmap.createScaledBitmap(result1, size1, size1, true);
            /*Canvas canvas=new Canvas(result);
            canvas.drawBitmap(source,0,0,null);*/
            if (result != source) {
                source.recycle();
            }
            if (result != result1 && result1 != source) {
                result1.recycle();
            }
            return result;
        }

        @Override
        public String key() {
            return "square()";
        }
    }
}


