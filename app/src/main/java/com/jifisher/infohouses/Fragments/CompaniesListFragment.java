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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.jifisher.infohouses.Adapters.CompaniesAdapter;
import com.jifisher.infohouses.CustomViews.RangeWithTextView;
import com.jifisher.infohouses.Databases.DBCompanies;
import com.jifisher.infohouses.MainActivity;
import com.jifisher.infohouses.R;
import com.squareup.picasso.Picasso;

public class CompaniesListFragment extends Fragment {

    private static final String ARG_FAVOURITE = "favourite";

    private boolean favouriteFlag = false;

    CompaniesAdapter companiesAdapter;
    Cursor cursor;



    public CompaniesListFragment() {
    }

    @SuppressWarnings("unused")
    public static CompaniesListFragment newInstance(boolean flagFavourite) {
        CompaniesListFragment fragment = new CompaniesListFragment();
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
        View view = inflater.inflate(R.layout.fragment_info_companies, container, false);
        final DBCompanies dbCompanies = new DBCompanies(getContext());
        dbCompanies.open();
        cursor = dbCompanies.getAll();
        // Set the adapter
        Context context = view.getContext();
        final RecyclerView recyclerView = view.findViewById(R.id.list);
        //recyclerView.addItemDecoration(itemDecoration);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();  // deprecated
        int height = display.getHeight();  // deprecated
        width -= 20;
        final int mNoOfColumns = CompaniesListFragment.Utility.calculateNoOfColumns(getActivity().getApplicationContext());

        final int finalWidth = width;

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
        recyclerView.setAdapter(companiesAdapter = new CompaniesAdapter(getContext(), cursor, width / mNoOfColumns, (int) (width / mNoOfColumns * 10f / 7f), dbCompanies, CompaniesListFragment.this, (MainActivity) getActivity()));
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
        recyclerView.setLayoutAnimation(animation);


        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

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
