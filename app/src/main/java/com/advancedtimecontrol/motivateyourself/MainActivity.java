package com.advancedtimecontrol.motivateyourself;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.onesignal.OneSignal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Fragment contentFragment;
   MotivationListFragment motivationListFragment;
    FavoriteListFragment  favListFragment;
    Button button_main, button_fav;
    ImageView buttonFavorite;
    ImageView buttonMain;
    List<MotivationStatement> favorites2;
    SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OneSignal.startInit(this).init();
        setContentView(R.layout.activity_main);
        button_main = (Button) findViewById(R.id.btn_main);
        button_fav = (Button) findViewById(R.id.btn_favorite);
        buttonFavorite = (ImageView) findViewById(R.id.favorite_image);
        buttonMain = (ImageView) findViewById(R.id.main_image);

        sharedPreference = new SharedPreference();
        FragmentManager fragmentManager = getSupportFragmentManager();
		/*
		 * This is called when orientation is changed.
		 *
		 */
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("content")) {
                String content = savedInstanceState.getString("content");
                if (content.equals(FavoriteListFragment.ARG_ITEM_ID)) {
                    if (fragmentManager.findFragmentByTag(FavoriteListFragment.ARG_ITEM_ID) != null) {
                        setFragmentTitle(R.string.app_name);

                        contentFragment = fragmentManager
                                .findFragmentByTag(FavoriteListFragment.ARG_ITEM_ID);
                    }
                }
            }
            if (fragmentManager.findFragmentByTag(MotivationListFragment.ARG_ITEM_ID) != null) {
                motivationListFragment = (MotivationListFragment) fragmentManager
                        .findFragmentByTag(MotivationListFragment.ARG_ITEM_ID);
                contentFragment = motivationListFragment;
            }
        } else {
            motivationListFragment = new MotivationListFragment();
            setFragmentTitle(R.string.app_name);

            switchContent(motivationListFragment, MotivationListFragment.ARG_ITEM_ID);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (contentFragment instanceof FavoriteListFragment) {
            outState.putString("content", FavoriteListFragment.ARG_ITEM_ID);
        } else {
            outState.putString("content", MotivationListFragment.ARG_ITEM_ID);
        }
        super.onSaveInstanceState(outState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_favorites:
                setFragmentTitle(R.string.favorites);

                favListFragment = new FavoriteListFragment();
                switchContent(favListFragment, FavoriteListFragment.ARG_ITEM_ID);

                return true;
        }
        switch (item.getItemId()) {
            case R.id.action_settings:
                share();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void share() {
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            InputStream inputStream = getAssets().open("html/page_" + ".html");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line + " ");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String finlcode = "" + sb;
        String fullTXT = finlcode;
        // textView_show_Text.setText(Html.fromHtml(fullTXT));
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, Uri.parse("https://play.google.com/store/apps/details?id=com.advancedtimecontrol.motivateyourself")+"  " + getString(R.string.share_message));
        if (share.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(share, "مشاركة "));
        }
    }

    public void open_mainList(View view){

        button_main.setTextColor(Color.parseColor("#ffffff"));
        button_fav.setTextColor(Color.parseColor("#80B2BF"));

        button_main.setBackgroundColor(Color.parseColor("#096d8f"));
        button_fav.setBackgroundColor(Color.parseColor("#04667A"));

        buttonFavorite.setImageResource(R.drawable.white_heart_off_white);
        buttonMain.setImageResource(R.drawable.exclamation_mark);

        FragmentManager fm = getSupportFragmentManager();
        setFragmentTitle(R.string.app_name);

        if (fm.getBackStackEntryCount() > 0) {
            super.onBackPressed();
        } else if (contentFragment instanceof MotivationListFragment
                || fm.getBackStackEntryCount() == 0) {
            Toast.makeText(this, "أنت فى القائمة الرئيسية", Toast.LENGTH_SHORT).show();
        }
    }

    public void open_favoriteList (View view){


        try {
            favorites2 = sharedPreference.getFavorites(this);

            button_fav.setTextColor(Color.parseColor("#ffffff"));
            button_main.setTextColor(Color.parseColor("#80B2BF"));

            button_fav.setBackgroundColor(Color.parseColor("#096d8f"));
            button_main.setBackgroundColor(Color.parseColor("#04667A"));

            buttonMain.setImageResource(R.drawable.exclamation_mark_off_white);
            buttonFavorite.setImageResource(R.drawable.blue_heart);

            setFragmentTitle(R.string.app_name);
            favListFragment = new FavoriteListFragment();
            switchContent(favListFragment, FavoriteListFragment.ARG_ITEM_ID);

        } catch (Exception e) {
            Toast.makeText(this,  "المفضلة فارغة " , Toast.LENGTH_SHORT).show();

        }


    }

    public void switchContent(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.popBackStackImmediate());

        if (fragment != null) {
            FragmentTransaction transaction = fragmentManager
                    .beginTransaction();
            transaction.replace(R.id.content_frame, fragment, tag);
            //Only FavoriteListFragment is added to the back stack.
            if (!(fragment instanceof MotivationListFragment)) {
                transaction.addToBackStack(tag);
            }
            transaction.commit();
            contentFragment = fragment;
        }
    }

    protected void setFragmentTitle(int resourseId) {
        setTitle(resourseId);
        getSupportActionBar().setTitle(resourseId);

    }

    /*
     * We call super.onBackPressed(); when the stack entry count is > 0. if it
     * is instanceof ProductListFragment or if the stack entry count is == 0, then
     * we finish the activity.
     * In other words, from ProductListFragment on back press it quits the app.
     */
    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            super.onBackPressed();
        } else if (contentFragment instanceof MotivationListFragment
                || fm.getBackStackEntryCount() == 0) {
            finish();
        }
    }
}
