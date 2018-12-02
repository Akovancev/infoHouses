package com.jifisher.infohouses.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.jifisher.infohouses.Adapters.InfoHousesAdapter;
import com.jifisher.infohouses.CustomViews.RangeWithTextView;
import com.jifisher.infohouses.Databases.DBHouses;
import com.jifisher.infohouses.MainActivity;
import com.jifisher.infohouses.R;

public class HousesListFragment extends Fragment  {

    private static final String ARG_FAVOURITE = "favourite";

    private boolean favouriteFlag = false;

    View filters;
    Dialog dialogFilters;
    InfoHousesAdapter infoHousesAdapter;
    Cursor cursor;



    public HousesListFragment() {
    }

    @SuppressWarnings("unused")
    public static HousesListFragment newInstance(boolean flagFavourite) {
        HousesListFragment fragment = new HousesListFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_FAVOURITE, flagFavourite);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            favouriteFlag = getArguments().getBoolean(ARG_FAVOURITE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_houses, container, false);
        filters = inflater.inflate(R.layout.dialog_filters, null);
        ((Spinner) filters.findViewById(R.id.companySpinner)).setAdapter(getAdapter(R.array.company_name));
        ((Spinner) filters.findViewById(R.id.districtSpinner)).setAdapter(getAdapter(R.array.district_filters));
        ((Spinner) filters.findViewById(R.id.finishingSpinner)).setAdapter(getAdapter(R.array.finishing_filters));
        ((RangeWithTextView) filters.findViewById(R.id.roomsRange)).setMin(0);
        ((RangeWithTextView) filters.findViewById(R.id.roomsRange)).setMax(6);
        ((RangeWithTextView) filters.findViewById(R.id.priceRange)).setMin(0);
        ((RangeWithTextView) filters.findViewById(R.id.priceRange)).setMax(990);
        ((RangeWithTextView) filters.findViewById(R.id.endBuildRange)).setMin(13);
        ((RangeWithTextView) filters.findViewById(R.id.endBuildRange)).setMax(40);

        final DBHouses dbHouses = new DBHouses(getContext());
        dbHouses.open();
        cursor = dbHouses.getHouses(favouriteFlag);
        // Set the adapter
        Context context = view.getContext();
        final RecyclerView recyclerView = view.findViewById(R.id.list);
        AlertDialog.Builder adb = new AlertDialog.Builder(getContext());
        // сообщение
        adb.setView(filters);
        //recyclerView.addItemDecoration(itemDecoration);
        ((MainActivity)getActivity()).show();

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();  // deprecated
        int height = display.getHeight();  // deprecated
        width -= 20;
        final int mNoOfColumns = Utility.calculateNoOfColumns(getActivity().getApplicationContext());

        final int finalWidth = width;
        DialogInterface.OnClickListener myClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    // положительная кнопка
                    case Dialog.BUTTON_POSITIVE:
                        String selection = "";
                        int sum = 0;

                        if (((Spinner) filters.findViewById(R.id.companySpinner)).getSelectedItemPosition() != 0) {
                            sum++;
                            if (sum > 1) {
                                selection += " AND ";
                            }
                            selection += "company = '" + getResources().getStringArray(R.array.company_name)[((Spinner) filters.findViewById(R.id.companySpinner)).getSelectedItemPosition()] + "'";
                        }
                        if (((Spinner) filters.findViewById(R.id.districtSpinner)).getSelectedItemPosition() != 0) {
                            sum++;
                            if (sum > 1) {
                                selection += " AND ";
                            }
                            selection += "discrit = '" + getResources().getStringArray(R.array.district_filters)[((Spinner) filters.findViewById(R.id.districtSpinner)).getSelectedItemPosition()] + "'";
                        }
                        if (((Spinner) filters.findViewById(R.id.companySpinner)).getSelectedItemPosition() != 0) {
                            sum++;
                            if (sum > 1) {
                                selection += " AND ";
                            }
                            selection += "finishing  = '" + (((Spinner) filters.findViewById(R.id.companySpinner)).getSelectedItemPosition()-1) + "' OR " +"finishing  = '2'  ";
                        }  sum++;
                        if (sum > 1) {
                            selection += " AND ";
                        }
                        for(int i = Integer.parseInt(((RangeWithTextView) filters.findViewById(R.id.roomsRange)).rangebar1.getLeftPinValue()); i<= Integer.parseInt(((RangeWithTextView) filters.findViewById(R.id.roomsRange)).rangebar1.getRightPinValue()); i++){
                          if(i!=Integer.parseInt(((RangeWithTextView) filters.findViewById(R.id.roomsRange)).rangebar1.getLeftPinValue()))
                              selection += " OR ";
                            if(i==0)
                            selection += "roomstudio  = '1'";
                            else
                            selection += "room"+i+"  = '1'";

                        }
                            selection += " AND ";

                            selection += "price  >= '"+((RangeWithTextView) filters.findViewById(R.id.priceRange)).rangebar1.getLeftPinValue()+"' AND price  <= '"+((RangeWithTextView) filters.findViewById(R.id.priceRange)).rangebar1.getRightPinValue()+"'  ";

                            selection += " AND ";

                            selection += "date  >= '"+((RangeWithTextView) filters.findViewById(R.id.endBuildRange)).rangebar1.getLeftPinValue()+"' AND date  <= '"+((RangeWithTextView) filters.findViewById(R.id.endBuildRange)).rangebar1.getRightPinValue()+"'  ";
                        Log.d("myLog", selection);
                        cursor.close();
                        cursor = dbHouses.getHouses(selection,favouriteFlag);
                        recyclerView.setAdapter(infoHousesAdapter = new InfoHousesAdapter(getContext(), cursor, finalWidth / mNoOfColumns, (int) (finalWidth / mNoOfColumns * 10f / 7f), dbHouses, HousesListFragment.this, (MainActivity) getActivity()));
                        int resId = R.anim.layout_animation_fall_down;
                        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
                        recyclerView.setLayoutAnimation(animation);
                        break;

                    // негативная кнопка
                    case Dialog.BUTTON_NEGATIVE:
                        break;
                    // нейтральная кнопка
                    case Dialog.BUTTON_NEUTRAL:
                        break;
                }
            }
        };
        // кнопка положительного ответа
        adb.setPositiveButton("Ок", myClickListener);
        // кнопка отрицательного ответа
        adb.setNegativeButton("Отмена", myClickListener);
        adb.setTitle("Фильтры");
        dialogFilters = adb.create();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialogFilters.getWindow().setBackgroundDrawableResource(R.drawable.back_dialog);
        }
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        if (mNoOfColumns <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mNoOfColumns));
        }
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(context, R.dimen.item_offset);
        //width-=(mNoOfColumns-1)*10;
        recyclerView.setAdapter(infoHousesAdapter = new InfoHousesAdapter(getContext(), cursor, width / mNoOfColumns, (int) (width / mNoOfColumns * 10f / 7f), dbHouses, HousesListFragment.this, (MainActivity) getActivity()));
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
        recyclerView.setLayoutAnimation(animation);
        final FloatingActionButton fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                dialogFilters.show();
                // создаем диалог
            }
        });

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy < 0) {
                    if (!fab.isShown())
                        fab.show();
                    //showAppBar(toolbar);
                } else if (dy > 0) {
                    if (fab.isShown())
                        fab.hide();
                    //hideAppBar(toolbar);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        return view;
    }


    public ArrayAdapter<String> getAdapter(int res) {
        ArrayAdapter<String> result = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(res)) {

            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);


                ((TextView) v).setGravity(Gravity.RIGHT);
                return v;

            }

        };
        result.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return result;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }*/
    }

    public static class Utility {
        public static int calculateNoOfColumns(Context context) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
            int noOfColumns = (int) (dpWidth / 180);
            noOfColumns = Math.max(noOfColumns, 2);
            return noOfColumns;
        }
    }

    public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

        private int mItemOffset;

        public ItemOffsetDecoration(int itemOffset) {
            mItemOffset = itemOffset;
        }

        public ItemOffsetDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
            this(context.getResources().getDimensionPixelSize(itemOffsetId));
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
