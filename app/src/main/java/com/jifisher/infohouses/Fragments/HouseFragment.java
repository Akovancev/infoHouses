package com.jifisher.infohouses.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.jifisher.infohouses.Adapters.InfoHousesAdapter;
import com.jifisher.infohouses.Adapters.RoomsAdapter;
import com.jifisher.infohouses.CustomViews.AutoResizeTextView;
import com.jifisher.infohouses.CustomViews.RangeWithTextView;
import com.jifisher.infohouses.CustomViews.SwipeImagesView;
import com.jifisher.infohouses.Databases.DBHouses;
import com.jifisher.infohouses.MainActivity;
import com.jifisher.infohouses.R;
import com.jifisher.infohouses.SupportClasses.House;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class HouseFragment extends Fragment {

    View view;


    private static final String ARG_ID = "id";

    House house;

    @SuppressWarnings("unused")
    public static HouseFragment newInstance(int id) {
        HouseFragment fragment = new HouseFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            house = new DBHouses(getContext()).getHouse(getArguments().getInt(ARG_ID));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_house, container, false);
        initialize(inflater);
        return view;
    }


    public void initialize(final LayoutInflater inflater) {
        SwipeImagesView swipeImagesView = view.findViewById(R.id.swipeImages);
        ImageView[] imageViews = new ImageView[house.image.size()];
        for (int i = 0; i < imageViews.length; i++) {
            imageViews[i] = new ImageView(getContext());
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageViews[i].setLayoutParams(params);

            Picasso.get().load(house.image.get(i))
                    .placeholder(R.drawable.house)
                    .into(imageViews[i]);
            final int finalI = i;
            imageViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FullScreenFragment fullScreenFragment = FullScreenFragment.newInstance(house.image.get(finalI));
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        fullScreenFragment.setEnterTransition(new Fade());

                        fullScreenFragment.setExitTransition(new Fade());
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .add(R.id.container, fullScreenFragment)
                                .addToBackStack(null)
                                .commit();
                    } else {
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .add(R.id.container, fullScreenFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                }
            });
        }
        swipeImagesView.setPage(imageViews);
        view.findViewById(R.id.fullInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHouses dbHouses = new DBHouses(getContext());
                dbHouses.open();
                final House house = dbHouses.getFullInfo(HouseFragment.this.house);
                StringBuffer sb = new StringBuffer();
                sb.append("К продаже предлагается квартира в " + house.nameComplex + " от \"" + house.company + "\". ");
                if (house.allFinishing)
                    sb.append("В наличии имеются квартиры как с отделкой, так и без. ");
                else if (house.finishing)
                    sb.append("Все квартиры в жилом комплексе с отделкой. ");
                else
                    sb.append("Все квартиры в жилом комплексе с черновой отделкой. ");
                if (house.playground || house.school || house.kindergarten) {
                    sb.append("В шаговой доступности наход" + (((house.playground && house.school) || (house.kindergarten && house.school) || (house.playground && house.kindergarten)) ? "я" : "и") + "тся ");
                    if (house.kindergarten) {
                        sb.append("детский сад");
                        if (house.school && house.playground)
                            sb.append(", ");
                        else if (house.school || house.playground)
                            sb.append(" и ");
                    }
                    if (house.school) {
                        sb.append("школа");
                        if (house.playground)
                            sb.append(" и ");
                    }
                    if (house.playground)
                        sb.append("детская площадка");
                    sb.append(". ");
                }
                sb.append("Дом - " + house.material.toLowerCase() + ". ");
                if (house.floorMax.equals(house.floorMin))
                    sb.append("В домах " + house.floorMax + " эт. ");
                else
                    sb.append("Этажность варьируется от " + house.floorMin + " до " + house.floorMax + ". ");
                sb.append("Цена за квартиру - от " + house.price + ". ");
                if (house.finished)
                    sb.append("Заселение возможно сразу после оформления документов. ");
                else {
                    int i= Integer.parseInt(house.date);
                    int year=i/4+2015;
                    int qu=i%4+1;
                    sb.append("Заселение возможно с "+qu+" кв. "+year+" года.");
                }
                sb.append("Возможно приобритение ");
                for(int i=0;i<house.rooms.size();i++){
                    String space=house.spaceStudio;
                    boolean flag=false;
                    switch (house.rooms.get(i).name){
                        case "Однокомнотная":
                            space=house.space1;
                            break;
                        case "Двухкомнотная":
                            space=house.space2;
                            break;
                        case "Трехкомнотная":
                            space=house.space3;
                            break;
                        case "Четырехкомнотная":
                            space=house.space4;
                            break;
                        case "Пятикомнотная":
                            space=house.space5;
                            break;
                        case "Шестикомнотная":
                            space=house.space6;
                            break;
                            default:
                                flag=true;
                    }
                    if(flag)
                        sb.append(((i>0)?", ":"")+"квартиру-студию (от "+space+" кв.м.)");
                    else
                        sb.append(((i>0)?", ":"")+house.rooms.get(i).name.substring(0,house.rooms.get(i).name.length()-2)+"ую  (от "+space+" кв.м.)");
                }
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
                                // Create a Uri from an intent string. Use the result to create an Intent.
                                Uri gmmIntentUri = Uri.parse("geo:"+house.x+","+house.y+"");

// Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
// Make the Intent explicit by setting the Google Maps package
                                mapIntent.setPackage("com.google.android.apps.maps");

// Attempt to start an activity that can handle the Intent
                                startActivity(mapIntent);
                                break;
                            // нейтральная кнопка
                            case Dialog.BUTTON_NEUTRAL:
                                break;
                        }
                    }
                };
                adb.setPositiveButton("Ок", myClickListener);
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
        ((AutoResizeTextView) view.findViewById(R.id.name)).setText(house.nameComplex);
        ((AutoResizeTextView) view.findViewById(R.id.location)).setText(house.discrit + ", " + house.adress);
        final RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(mLayoutManager);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int height = display.getHeight();  // deprecated
        recyclerView.setAdapter(new RoomsAdapter(getContext(), (int) (height * 0.32f / 10f * 8f), (int) (height * 0.32f), house.rooms, HouseFragment.this, (MainActivity) getActivity(), house));
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
        recyclerView.setLayoutAnimation(animation);
        final ImageView imageLike=view.findViewById(R.id.like);

        if (!house.flagLike)
            Picasso.get().load(R.drawable.baseline_favorite_border_black_48dp)
                    .transform(new ColorTransformation(getContext().getResources()
                            .getColor(R.color.halfblack)))
                    .into(imageLike);
        else
            Picasso.get().load(R.drawable.baseline_favorite_black_48dp)
                    .transform(new ColorTransformation(getContext().getResources()
                            .getColor(android.R.color.holo_red_light)))
                    .into(imageLike);
        imageLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                house.flagLike = !house.flagLike;
                if (!house.flagLike)
                    Picasso.get().load(R.drawable.baseline_favorite_border_black_48dp)
                            .transform(new ColorTransformation(getContext().getResources()
                                    .getColor(R.color.halfblack))).fetch(new Callback() {
                        @Override
                        public void onSuccess() {
                            Picasso.get().load(R.drawable.baseline_favorite_border_black_48dp)
                                    .transform(new ColorTransformation(getContext().getResources()
                                            .getColor(R.color.halfblack))).into(imageLike);
                            Animation fadeIn = new ScaleAnimation(1, 0.8f, 1, 0.8f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                            fadeIn.setDuration(50);
                            Animation fadeOut = new ScaleAnimation(0.8f, 1f, 0.8f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                            fadeOut.setStartOffset(50);
                            fadeOut.setDuration(50);
                            AnimationSet animation = new AnimationSet(true);
                            animation.addAnimation(fadeIn);
                            animation.addAnimation(fadeOut);
                            imageLike.startAnimation(animation);
                        }

                        @Override
                        public void onError(Exception e) {

                        }

                    });
                else
                    Picasso.get().load(R.drawable.baseline_favorite_black_48dp)
                            .transform(new ColorTransformation(getContext().getResources()
                                    .getColor(R.color.holo_red_light))).fetch(new Callback() {
                        @Override
                        public void onSuccess() {
                            Picasso.get().load(R.drawable.baseline_favorite_black_48dp)
                                    .transform(new ColorTransformation(getContext().getResources()
                                            .getColor(R.color.holo_red_light))).into(imageLike);
                            Animation fadeIn = new ScaleAnimation(1, 0.8f, 1, 0.8f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                            fadeIn.setDuration(50);
                            Animation fadeOut = new ScaleAnimation(0.8f, 1f, 0.8f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                            fadeOut.setStartOffset(50);
                            fadeOut.setDuration(50);
                            AnimationSet animation = new AnimationSet(true);
                            animation.addAnimation(fadeIn);
                            animation.addAnimation(fadeOut);
                            imageLike.startAnimation(animation);
                        }

                        @Override
                        public void onError(Exception e) {

                        }

                    });
                new DBHouses(getContext()).updateLike(house.id, house.flagLike);
            }
        });
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

