package com.advancedtimecontrol.motivateyourself;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MotivationListFragment extends Fragment implements
        AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    public List<MotivationStatement> getProducts() {
        return motivationStatements;
    }

    public static final String ARG_ITEM_ID = "product_list";

    Activity activity;
    ListView productListView;
    List<MotivationStatement> motivationStatements;
   MotivationStatementAdapter motivationStatementAdapter;

    SharedPreference sharedPreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        sharedPreference = new SharedPreference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_motivation_list, container,
                false);
        findViewsById(view);

        setMotivationStatements();

        motivationStatementAdapter = new MotivationStatementAdapter(activity, motivationStatements);
        productListView.setAdapter(motivationStatementAdapter);
        productListView.setOnItemClickListener(this);
        productListView.setOnItemLongClickListener(this);
        return view;
    }

    public void setMotivationStatements() {

        MotivationStatement motivationStatement1 = new MotivationStatement(0, "قد تكون أفضل الطرق أصعبها و لكن عليك دائما باتباعها ، إذ الإعتياد عليها سيجعل الأمور تبدو سهلة");

        MotivationStatement motivationStatement2 = new MotivationStatement(1, "الهروب هو السبب الوحيد في الفشل ، لذا فإنك تفشل طالما لم تتوقف عن المحاولة ");

        MotivationStatement motivationStatement3 = new MotivationStatement(2, "عندما أقوم ببناء فريق فأني أبحث دائما عن أناس يحبون الفوز ، وإذا لم أعثر على أي منهم فأنني ابحث عن أناس يكرهون الهزيمة");

        MotivationStatement motivationStatement4 = new MotivationStatement(3, "إن الاتجاه الذي يبدأ مع التعلم سوف يكون من شأنه أن يحدد حياه المرء في المستقبل");
      MotivationStatement motivationStatement5  = new MotivationStatement(4, "فى الحلبة كما فى خارجها، لا عيب فى أن تسقط أرضا. بل العيب فى أن تبقى على الأرض");
        MotivationStatement motivationStatement6 = new MotivationStatement(5, "إذا لم تحاول أن تفعل شيء أبعد مما قد أتقنته .. فأنك لا تتقدم أبدا");
        MotivationStatement motivationStatement7 = new MotivationStatement(6, "الحياة إما أن تكون مغامرة جريئه  أو لا شيء");
        MotivationStatement motivationStatement8 = new MotivationStatement(7, "ينقسم الفاشلون إلى نصفين: هؤلاء الذين يفكرون ولا يعملون، وهؤلاء الذين يعملون ولا يفكرون أبداً");
        MotivationStatement motivationStatement9 = new MotivationStatement(8, "غالبا ما يكون النجاح حليف هؤلاء الذين يعملون بجرأة، ونادراً ما يكون حليف أولئك المترددين الذي يتهيبون المواقف ونتائجها");
        MotivationStatement motivationStatement10 = new MotivationStatement(9, "لا يقاس النجاح بالموقع الذي يتبوأه المرء في حياته .. بقدر ما يقاس بالصعاب التي يتغلب عليها");
        MotivationStatement motivationStatement11 = new MotivationStatement(10, "الحكمة الحقيقية ليست في رؤيا ما هو أمام عينيك فحسب !! بل هو التكهن ماذا سيحدث بالمستقبل");
        MotivationStatement motivationStatement12 = new MotivationStatement(11, "في كل الأمور يتوقف النجاح على تحضير سابق وبدون مثل هذا التحضير لابد أن يكون هناك فشل");
        MotivationStatement motivationStatement13 = new MotivationStatement(12, "السعادة تكمن في متعه الإنجاز ونشوه المجهود المبدع");
        MotivationStatement motivationStatement14 = new MotivationStatement(13, "إن العالم يفسح الطريق للمرء الذي يعرف إلى أين هو ذاهب");
        MotivationStatement motivationStatement15 = new MotivationStatement(14, "نحن نسقط لكي ننهض، ونهزم في المعارك لنحقق نصراً أروع. تماما كما ننام لكي نصحوا أكثر قوةً ونشاطاً");
        MotivationStatement motivationStatement16 = new MotivationStatement(15, "المنسحب لا يفوز أبدا و الفائز لا ينسحب مهمها كلفه الأمر");
        MotivationStatement motivationStatement17 = new MotivationStatement(16, "ليس خطؤك أن تٌُولد فقيرا و لكنه بالتأكيد خطؤك أن تموت فقيرا معدما");

       motivationStatements = new ArrayList<MotivationStatement>();
       motivationStatements.add(motivationStatement1);
        motivationStatements.add(motivationStatement2);
        motivationStatements.add(motivationStatement3);
        motivationStatements.add(motivationStatement4);
        motivationStatements.add(motivationStatement5);
        motivationStatements.add(motivationStatement6);
        motivationStatements.add(motivationStatement7);
        motivationStatements.add(motivationStatement8);
        motivationStatements.add(motivationStatement9);
        motivationStatements.add(motivationStatement10);
        motivationStatements.add(motivationStatement11);
        motivationStatements.add(motivationStatement12);
        motivationStatements.add(motivationStatement13);
        motivationStatements.add(motivationStatement14);
        motivationStatements.add(motivationStatement15);
        motivationStatements.add(motivationStatement16);
        motivationStatements.add(motivationStatement17);
    }

    private void findViewsById(View view) {
        productListView = (ListView) view.findViewById(R.id.list_product);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        MotivationStatement product = (MotivationStatement) parent.getItemAtPosition(position);

        //TextView Title = (TextView) view.findViewById(R.id.txt_pdt_name);
        int PageNum = 0;
        PageNum = position;

        Intent intent = new Intent(activity, Webhtml.class);
        intent.putExtra("pageNum", PageNum);

        // intent.putExtra("Title", Title.getText());
        startActivity(intent);

        //Toast.makeText(activity, product.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> arg0, View view,
                                   int position, long arg3) {
        ImageView button = (ImageView) view.findViewById(R.id.imgbtn_favorite);

        String tag = button.getTag().toString();
        if (tag.equalsIgnoreCase("grey")) {
            sharedPreference.addFavorite(activity, motivationStatements.get(position));
            Toast.makeText(activity,
                    activity.getResources().getString(R.string.add_favr),
                    Toast.LENGTH_SHORT).show();

            button.setTag("red");
            button.setImageResource(R.drawable.blue_heart);
        } else {
            sharedPreference.removeFavorite(activity, motivationStatements.get(position));
            button.setTag("grey");
            button.setImageResource(R.drawable.white_heart);
            Toast.makeText(activity,
                    activity.getResources().getString(R.string.remove_favr),
                    Toast.LENGTH_SHORT).show();
        }


        return true;
    }

    @Override
    public void onResume() {
       motivationStatementAdapter.notifyDataSetChanged();
        getActivity().setTitle(R.string.app_name);
        // getActivity().getActionBar().setTitle(R.string.app_name);
        super.onResume();
    }

}