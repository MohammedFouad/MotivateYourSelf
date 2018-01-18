package com.advancedtimecontrol.motivateyourself;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;



public class MotivationStatementAdapter extends ArrayAdapter {

    private Context context;
    List<MotivationStatement> motivationStatements;
    SharedPreference sharedPreference;

    public MotivationStatementAdapter(Context context, List<MotivationStatement> motivationStatements) {
        super(context, R.layout.motivation_list_statement, motivationStatements);
        this.context = context;
        this.motivationStatements = motivationStatements;
        sharedPreference = new SharedPreference();
    }

    private class ViewHolder {
        TextView motivationStatementText;

        ImageView favoriteImg;
    }

    public void refresh(List<MotivationStatement> items) {
        this.motivationStatements = items;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return motivationStatements.size();
    }

    @Override
    public MotivationStatement getItem(int position) {
        return motivationStatements.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.motivation_list_statement, null);
            holder = new ViewHolder();
            holder.motivationStatementText = (TextView) convertView
                    .findViewById(R.id.txt_pdt_name);


            holder.favoriteImg = (ImageView) convertView
                    .findViewById(R.id.imgbtn_favorite);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MotivationStatement motivationStatement = (MotivationStatement) getItem(position);
        holder.motivationStatementText.setText(motivationStatement.getName());

        if (checkFavoriteItem(motivationStatement)) {
            holder.favoriteImg.setImageResource(R.drawable.blue_heart);
            holder.favoriteImg.setTag("red");
        } else {
            holder.favoriteImg.setImageResource(R.drawable.white_heart);
            holder.favoriteImg.setTag("grey");

        }

        return convertView;
    }

    /*Checks whether a particular product exists in SharedPreferences*/
    public boolean checkFavoriteItem(MotivationStatement checkProduct) {
        boolean check = false;
        List<MotivationStatement> favorites = sharedPreference.getFavorites(context);
        if (favorites != null) {
            for (MotivationStatement product : favorites) {
                if (product.equals(checkProduct)) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }


    public void add(MotivationStatement motivationStatement) {
        super.add(motivationStatement);
        motivationStatements.add(motivationStatement);
        notifyDataSetChanged();
    }


    public void remove(MotivationStatement motivationStatement) {
        super.remove(motivationStatement);
        motivationStatements.remove(motivationStatement);
        notifyDataSetChanged();
    }

}
