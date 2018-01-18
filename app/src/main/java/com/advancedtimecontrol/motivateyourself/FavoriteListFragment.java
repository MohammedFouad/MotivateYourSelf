package com.advancedtimecontrol.motivateyourself;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteListFragment extends Fragment {

    public static final String ARG_ITEM_ID = "favorite_list";
    String tag;
    ListView favoriteList;
    SharedPreference sharedPreference;
    List<MotivationStatement> favorites;

    Activity activity;
    MotivationStatementAdapter motivationStatementAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_motivation_list, container,
                false);
        // Get favorite items from SharedPreferences.
        sharedPreference = new SharedPreference();
        favorites = sharedPreference.getFavorites(activity);


        if (favorites == null) {
            showAlert(getResources().getString(R.string.no_favorites_items),
                    getResources().getString(R.string.no_favorites_msg));
        } else {

            if (favorites.size() == 0) {
                showAlert(
                        getResources().getString(R.string.no_favorites_items),
                        getResources().getString(R.string.no_favorites_msg));
            }

            favoriteList = (ListView) view.findViewById(R.id.list_product);
            if (favorites != null) {
                motivationStatementAdapter = new MotivationStatementAdapter(activity, favorites);
                favoriteList.setAdapter(motivationStatementAdapter);

                favoriteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView<?> parent, View arg1,
                                            int position, long arg3) {

                        //  Product product = (Product) parent.getItemAtPosition(position);

                        TextView Title = (TextView) view.findViewById(R.id.txt_pdt_name);
                        int pageNum;
                        pageNum = favorites.get(position).getId();

                        // Toast.makeText(activity, "page Number " + pageNum + "  position " + position, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(activity, Webhtml.class);
                        intent.putExtra("pageNum", pageNum);

                        intent.putExtra("Title", Title.getText());
                        startActivity(intent);

                        // Toast.makeText(activity, product.toString(), Toast.LENGTH_LONG).show();

                    }
                });

                favoriteList
                        .setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                            @Override
                            public boolean onItemLongClick(
                                    AdapterView<?> parent, View view,
                                    int position, long id) {

                                ImageView button = (ImageView) view
                                        .findViewById(R.id.imgbtn_favorite);

                                tag = button.getTag().toString();
                                if (tag.equalsIgnoreCase("grey")) {
                                    sharedPreference.addFavorite(activity,
                                            favorites.get(position));
                                    Toast.makeText(
                                            activity,
                                            activity.getResources().getString(
                                                    R.string.add_favr),
                                            Toast.LENGTH_SHORT).show();

                                    button.setTag("red");
                                    button.setImageResource(R.drawable.blue_heart);
                                } else {
                                    sharedPreference.removeFavorite(activity,
                                            favorites.get(position));
                                    button.setTag("grey");
                                    button.setImageResource(R.drawable.white_heart);
                                    motivationStatementAdapter.remove(favorites
                                            .get(position));
                                    Toast.makeText(
                                            activity,
                                            activity.getResources().getString(
                                                    R.string.remove_favr),
                                            Toast.LENGTH_SHORT).show();
                                }
                                return true;
                            }
                        });
            }
        }
        return view;
    }

    public void showAlert(String title, String message) {
        if (activity != null && !activity.isFinishing()) {
            AlertDialog alertDialog = new AlertDialog.Builder(activity)
                    .create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setCancelable(false);

            // setting OK Button
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            // activity.finish();
                            getFragmentManager().popBackStackImmediate();
                        }
                    });
            alertDialog.show();
        }
    }

    @Override
    public void onResume() {
        if (favorites != null){
            favorites.clear();
            favorites= sharedPreference.getFavorites(activity);
            motivationStatementAdapter.refresh(favorites);
        }
        getActivity().setTitle(R.string.favorites);
        super.onResume();
    }



}
