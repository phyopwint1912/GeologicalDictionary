package com.dictionary.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dictionary.R;
import com.dictionary.ui.fragments.DetailsFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DetailsActivity extends AppCompatActivity {
    Toolbar toolbar;
    String word;
    String wordDesc;
    String wordType;
    String fav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        toolbar = (Toolbar) findViewById(R.id.toolbar_details);
        Intent intent = getIntent();
        if (getIntent() != null) {
            //Intent intent = getIntent();
            gettingData(intent);
            Bundle bundle = new Bundle();
            bundle.putString("word",word);
            bundle.putString("wordDesc",wordDesc);
            bundle.putString("wordType",wordType);
            bundle.putString("fav",fav);

            Fragment fragment = new DetailsFragment();
            fragment.setArguments(bundle);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.container, fragment);
            ft.commit();
        }

        toolbar.setTitle("Definition");
        //toolbar.setTitle(intent.getStringExtra("word"));
        toolbar.setTitleTextColor(Color.WHITE);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setHomeButtonEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }

        //making Navigation upArrow with White Color
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.md_divider_white), PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationIcon(upArrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Adding Font
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/RobotoCondensed.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

    void gettingData(Intent i) {
         word = i.getStringExtra("word");
         wordDesc = i.getStringExtra("wordDesc");
         wordType = i.getStringExtra("wordType");
         fav = i.getStringExtra("fav");

    }

    //OnBackPressed
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

