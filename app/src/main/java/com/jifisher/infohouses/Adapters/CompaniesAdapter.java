package com.jifisher.infohouses.Adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.os.Build;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.util.Log;
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
import com.jifisher.infohouses.Databases.DBCompanies;
import com.jifisher.infohouses.Fragments.CompaniesListFragment;
import com.jifisher.infohouses.Fragments.CompanyFragment;
import com.jifisher.infohouses.Fragments.HousesListFragment;
import com.jifisher.infohouses.MainActivity;
import com.jifisher.infohouses.R;
import com.jifisher.infohouses.SupportClasses.Company;
import com.jifisher.infohouses.SupportClasses.House;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CompaniesAdapter extends CursorRecyclerViewAdapter<CompaniesAdapter.ViewHolder> {

    Context mContext;
    int width;
    int heigth;
    DBCompanies companies;
    CompaniesListFragment companiesListFragment;
    MainActivity mainActivity;

    public CompaniesAdapter(Context context, Cursor cursor, int width, int heigth, DBCompanies companies, CompaniesListFragment companiesListFragment, MainActivity mainActivity) {
        super(context, cursor);
        mContext = context;
        this.heigth = heigth;
        this.width = width;
        this.companies = companies;
        this.companiesListFragment = companiesListFragment;
        this.mainActivity = mainActivity;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //public TextView mTextView;
        public ImageView imageView;
        public ImageView imageLike;
        public ImageView imageAdd;
        public AutoResizeTextView complex;
        public AutoResizeTextView adress;

        public ViewHolder(View view) {
            super(view);
            ((CardView)view.findViewById(R.id.card_view)).setPreventCornerOverlap(false);

            imageView = view.findViewById(R.id.houseImage);
            imageLike = view.findViewById(R.id.like);
            imageAdd = view.findViewById(R.id.add);
            adress = view.findViewById(R.id.adress);
            adress.setMinTextSize(0.1f);
            complex = view.findViewById(R.id.complex);
            complex.setMinTextSize(0.1f);

        }
    }

    @Override
    public CompaniesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_info_house, parent, false);
        CompaniesAdapter.ViewHolder vh = new CompaniesAdapter.ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(final CompaniesAdapter.ViewHolder viewHolder, Cursor cursor) {
        final Company company = Company.fromCursor(cursor);
        viewHolder.complex.setText((company.name.length()>25)?(company.name.substring(0,22)+"..."):company.name);
        //viewHolder.adress.setText((company.adress.length()>25)?(company.adress.substring(0,22)+"..."):company.adress);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params2.height = (int) (0.12f * heigth); //height recycleviewer
        params2.width = width -70; //width recycleviewer
        params2.leftMargin = 0; //width recycleviewer
        params2.topMargin = 0; //width recycleviewer
        viewHolder.itemView.findViewById(R.id.bottomCard).setLayoutParams(params2);

        RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params3.height = width - 60; //height recycleviewer
        params3.width = width - 60; //width recycleviewer
        params3.leftMargin = 0; //width recycleviewer
        params3.topMargin = 0; //width recycleviewer
        viewHolder.imageView.setLayoutParams(params3);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        /*params1.height = (int) (params2.height+params3.height+90/density); //height recycleviewer
        params1.width =width-20; //width recycleviewer*/
        params1.leftMargin = 0; //width recycleviewer
        params1.topMargin = 0; //width recycleviewer
        params1.bottomMargin = heigth / 16; //width recycleviewer
        viewHolder.itemView.findViewById(R.id.card_view).setLayoutParams(params1);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        /*params.height = (int) (params1.height+20); //height recycleviewer
        params.width =width; //width recycleviewer*/

        viewHolder.itemView.setLayoutParams(params);
        Picasso.get().load(company.image.get(0))
                .placeholder(R.drawable.house)
                .transform(new CropSquareTransformation())
                .into(viewHolder.imageView);
            (viewHolder.imageLike).setVisibility(View.GONE);
            (viewHolder.imageAdd).setVisibility(View.GONE);

        /*if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            viewHolder.itemView.setTransitionName(house.image.get(0));
        }*/
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompanyFragment manicureInfoFragment = CompanyFragment.newInstance(company.id);
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    manicureInfoFragment.setEnterTransition(new Fade());
                    companiesListFragment.setExitTransition(new Fade());
                    mainActivity.getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.container, manicureInfoFragment)
                            .addToBackStack(null)
                            .commit();
                } else {
                    mainActivity.getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.container, manicureInfoFragment)
                            .addToBackStack(null)
                            .commit();
                }


            }
        });
    }



    public class CropSquareTransformation implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            Log.d("myLog",size+"");
            int size1 = Math.min(size, 200);
            Log.d("myLog",size1+"");
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Log.d("myLog",x+"");
            Log.d("myLog",y+"");
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

    public class ColorTransformation implements Transformation {

        private int color = 0;

        public ColorTransformation() {

        }

        public ColorTransformation(int color) {
            setColor(color);
        }

        public void setColor(int color) {
            this.color = color;
        }

        public void setColorFromRes(Context context, int colorResId) {
            setColor(context.getResources().getColor(colorResId));
        }

        public int getColor() {
            return color;
        }

        @Override
        public Bitmap transform(Bitmap source) {
            if (color == 0) {
                return source;
            }

            BitmapDrawable drawable = new BitmapDrawable(Resources.getSystem(), source);
            Bitmap result = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            drawable.draw(canvas);
            drawable.setColorFilter(null);
            drawable.setCallback(null);

            if (result != source) {
                source.recycle();
            }

            return result;
        }

        @Override
        public String key() {
            return "DrawableColor:" + color;
        }
    }
}


