package com.jifisher.infohouses;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.jifisher.infohouses.Databases.DBHouses;
import com.jifisher.infohouses.Databases.DBCompanies;
import com.jifisher.infohouses.Fragments.CompaniesListFragment;
import com.jifisher.infohouses.Fragments.HousesListFragment;
import com.ncapdevi.fragnav.FragNavController;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FragNavController.RootFragmentListener {

    final int INDEX_RECENTS = 0;
    final int INDEX_FAVORITES = 1;
    final int INDEX_NEARBY = 2;
    final int INDEX_FRIENDS = 3;
    final int INDEX_FOOD = 4;
    public int tab = 0;
    public List<Fragment> fragments;
    FragNavController mFragNavController;
    public HousesListFragment housesListFragment;


    boolean flagAdd = false;
    String[][] houses;
    String[][] companies;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        houses = new String[][]{
                getResources().getStringArray(R.array.adress),
                getResources().getStringArray(R.array.space1),
                getResources().getStringArray(R.array.space2),
                getResources().getStringArray(R.array.space3),
                getResources().getStringArray(R.array.space4),
                getResources().getStringArray(R.array.space5),
                getResources().getStringArray(R.array.space6),
                getResources().getStringArray(R.array.spacestudio),
                getResources().getStringArray(R.array.discrit),
                getResources().getStringArray(R.array.room1),
                getResources().getStringArray(R.array.room2),
                getResources().getStringArray(R.array.room3),
                getResources().getStringArray(R.array.room4),
                getResources().getStringArray(R.array.room5),
                getResources().getStringArray(R.array.room6),
                getResources().getStringArray(R.array.roomstudio),
                getResources().getStringArray(R.array.price),
                getResources().getStringArray(R.array.finishing),
                getResources().getStringArray(R.array.company),
                getResources().getStringArray(R.array.date),
                getResources().getStringArray(R.array.finished),
                getResources().getStringArray(R.array.complex),
                getResources().getStringArray(R.array.type),
                getResources().getStringArray(R.array.playground),
                getResources().getStringArray(R.array.school),
                getResources().getStringArray(R.array.kindergarten),
                getResources().getStringArray(R.array.material),
                getResources().getStringArray(R.array.floors1),
                getResources().getStringArray(R.array.floors2),
                getResources().getStringArray(R.array.favourite),
                getResources().getStringArray(R.array.images_houses),
                getResources().getStringArray(R.array.imagesroom1),
                getResources().getStringArray(R.array.imagesroom2),
                getResources().getStringArray(R.array.imagesroom3),
                getResources().getStringArray(R.array.imagesroom4),
                getResources().getStringArray(R.array.imagesroom5),
                getResources().getStringArray(R.array.imagesroom6),
                getResources().getStringArray(R.array.imagesroomstudio),
                getResources().getStringArray(R.array.xlocal),
                getResources().getStringArray(R.array.ylocal),
                getResources().getStringArray(R.array.timeafterdeadline)
        };

        companies = new String[][]{
                getResources().getStringArray(R.array.company_name),
                getResources().getStringArray(R.array.href),
                getResources().getStringArray(R.array.adresscompany),
                getResources().getStringArray(R.array.number)
        };

        DBHouses dbHouses = new DBHouses(this);
        dbHouses.open(houses);


        DBCompanies dbCompanies = new DBCompanies(this);
        dbCompanies.open(companies);

        Cursor cursor=dbCompanies.getAll();
        if(cursor.moveToFirst())
        do{
            Cursor cursorHouses=dbHouses.getHouses(DBHouses.COLUMN_COMPANY+" = '"+cursor.getString(cursor.getColumnIndex(DBCompanies.COLUMN_NAME))+"'",false);
           int sum=0;
           int end=0;
            if(cursorHouses.moveToFirst()){
                do{
                    sum+=cursorHouses.getInt(cursorHouses.getColumnIndex(DBHouses.COLUMN_AFTER_DEDLINE));
                    if(cursorHouses.getInt(cursorHouses.getColumnIndex(DBHouses.COLUMN_FINISHED))==1)
                        end++;
                }while(cursorHouses.moveToNext());
            }
            dbCompanies.update(cursor.getInt(cursor.getColumnIndex(DBCompanies.COLUMN_ID)),cursorHouses.getCount(),sum,end);
        }while (cursor.moveToNext());

        final FragNavController.Builder builder = FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.container);

        fragments = new ArrayList<>(5);
        housesListFragment = new HousesListFragment();
        fragments.add(housesListFragment);
        fragments.add(HousesListFragment.newInstance(false));
        fragments.add(new CompaniesListFragment());

        builder.rootFragmentListener(MainActivity.this, 5);
        mFragNavController = builder.build();
        ((BottomBar) findViewById(R.id.bottomBar)).setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId) {
                    case R.id.bb_menu_houses:
                        tab = 0;
                        mFragNavController.switchTab(0);
                        break;
                    case R.id.bb_menu_favourite:
                        tab = 1;
                        mFragNavController.switchTab(1);
                        break;
                    case R.id.bb_menu_search:
                        tab = 2;
                        mFragNavController.switchTab(2);
                        break;
                }
            }
        });

        for (int i = 0; i < ((BottomBar) findViewById(R.id.bottomBar)).getTabCount(); i++) {
            BottomBarTab tab = ((BottomBar) findViewById(R.id.bottomBar)).getTabAtPosition(i);
            tab.setGravity(Gravity.CENTER);

            View icon = tab.findViewById(com.roughike.bottombar.R.id.bb_bottom_bar_icon);

            icon.setPadding(0, icon.getPaddingTop(), 0, icon.getPaddingTop());

            View title = tab.findViewById(com.roughike.bottombar.R.id.bb_bottom_bar_title);
            title.setVisibility(View.GONE);
        }



    }

    public void show(){
                findViewById(R.id.bottomBar).setVisibility(View.VISIBLE);

        }
        public void hide(){
                findViewById(R.id.bottomBar).setVisibility(View.GONE);

        }
    @Override
    public Fragment getRootFragment(int index) {
        switch (index) {
            case INDEX_RECENTS:
                return housesListFragment;
            case INDEX_FAVORITES:
                return HousesListFragment.newInstance(true);
            case INDEX_NEARBY:
                return new CompaniesListFragment();
        }
        throw new IllegalStateException("Need to send an index that we know");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("myLog", "onCreateOptionsMenu");
        /*if (tab == 1)
            getMenuInflater().inflate(R.menu.menu_manicure_fragment, menu);*/
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mFragNavController != null) {
            mFragNavController.onSaveInstanceState(outState);
        }
    }


}
