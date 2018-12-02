package com.jifisher.infohouses.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;

import com.jifisher.infohouses.Adapters.InfoHousesAdapter;
import com.jifisher.infohouses.Adapters.RoomsAdapter;
import com.jifisher.infohouses.CustomViews.AutoResizeTextView;
import com.jifisher.infohouses.CustomViews.SwipeImagesView;
import com.jifisher.infohouses.Databases.DBCompanies;
import com.jifisher.infohouses.Databases.DBHouses;
import com.jifisher.infohouses.MainActivity;
import com.jifisher.infohouses.R;
import com.jifisher.infohouses.SupportClasses.Company;
import com.jifisher.infohouses.SupportClasses.House;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.net.URLEncoder;

public class CompanyFragment extends Fragment {

    View view;


    private static final String ARG_ID = "id";

    Company company;

    @SuppressWarnings("unused")
    public static CompanyFragment newInstance(int id) {
        CompanyFragment fragment = new CompanyFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            company = new DBCompanies(getContext()).getcompany(getArguments().getInt(ARG_ID));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_company, container, false);
        initialize(inflater);
        return view;
    }


    public void initialize(final LayoutInflater inflater) {


        ((AutoResizeTextView) view.findViewById(R.id.nameCompany)).setText(company.name);
        ((AutoResizeTextView) view.findViewById(R.id.reliabilityСompany)).setText(company.reliability);
        final RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);

        final DBHouses dbHouses = new DBHouses(getContext());
        dbHouses.open();
        Cursor cursor = dbHouses.getHouses(DBHouses.COLUMN_COMPANY+" = '"+company.name+"'",false);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();  // deprecated
        width -= 20;
        final int mNoOfColumns = HousesListFragment.Utility.calculateNoOfColumns(getActivity().getApplicationContext());

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), mNoOfColumns));
        recyclerView.setAdapter( new InfoHousesAdapter(getContext(), cursor, width / mNoOfColumns, (int) (width / mNoOfColumns * 10f / 7f), dbHouses, this, (MainActivity) getActivity()));
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
        recyclerView.setLayoutAnimation(animation);

        final FloatingActionButton fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        StringBuffer sb = new StringBuffer();

                        sb.append("Всего проектов: "+company.sum+"\n");
                        sb.append("Сдано проектов: "+company.end+"\n");
                        sb.append("Проектов в процессе: "+(Integer.parseInt(company.sum)-Integer.parseInt(company.end))+"\n");
                sb.append("Адрес: "+company.adress+"\n");
                sb.append("Телефон: "+company.number+"\n");
                sb.append(company.reliability+"\n");

                        AlertDialog.Builder adb = new AlertDialog.Builder(getContext());

                        View text = inflater.inflate(R.layout.dialog_text, null);
                        ((TextView)text.findViewById(R.id.textFullInfo)).setText(sb.toString());
                        // сообщение
                        adb.setView(text);
                        // кнопка положительного ответа
                        DialogInterface.OnClickListener myClickListener = new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    // положительная кнопка
                                    case Dialog.BUTTON_POSITIVE:

                                        break;

                                    // негативная кнопка
                                    case Dialog.BUTTON_NEGATIVE:

// Attempt to start an activity that can handle the Intent
                                        startActivity(new Intent(Intent.ACTION_VIEW,
                                            Uri.parse(String.format("geo:0,0?q=%s",
                                                    URLEncoder.encode(company.adress)))));
                                        break;
                                    // нейтральная кнопка
                                    case Dialog.BUTTON_NEUTRAL:
                                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + company.number));
                                        startActivity(intent);
                                        break;
                                }
                            }
                        };
                        adb.setPositiveButton("Ок", myClickListener);
                        adb.setNeutralButton("Позвонить", myClickListener);
                        // кнопка отрицательного ответа
                        adb.setNegativeButton("Показать на карте", myClickListener);
                        adb.setTitle("Информация о жилом комлексе");
                        Dialog dialogFullInfo = adb.create();
                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            dialogFullInfo.getWindow().setBackgroundDrawableResource(R.drawable.back_dialog);
                        }
                        dialogFullInfo.show();

            }
        });
        Picasso.get().load(company.image.get(0))
                .placeholder(R.drawable.house)
                .into((ImageView)view.findViewById(R.id.logo));
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
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


