package com.advancedtimecontrol.motivateyourself;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Mohamed on 02/02/2017.
 */

public class Webhtml extends AppCompatActivity {
    int pageNum;
    WebView webView;
    String Title;
    SharedPreference sharedPreference;
    List<MotivationStatement> favorites2;
    MotivationListFragment motivationListFragment;
    TextView titleText;

   ImageButton nextButton;
    ImageButton backButton;

    int nextButtonPressedTwice = 0;
    int backButtonPressedTwice = 0;

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Intent data = getIntent();
        pageNum = data.getExtras().getInt("pageNum");
        Title = data.getExtras().getString("Title");
        webView = (WebView) findViewById(R.id.webView1);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.loadUrl("file:///android_asset/html/page" + pageNum + ".html");

        nextButton = findViewById(R.id.btn_next);
        backButton = findViewById(R.id.btn_back);

        sharedPreference = new SharedPreference();
        titleText = (TextView) findViewById(R.id.web_text);
        webView.setBackgroundColor(Color.TRANSPARENT);

        // Interstitial ad to be displayed when user clicks next buttons
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(this.getResources().getString(R.string.admob_interstitial_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        motivationListFragment = new MotivationListFragment();
        motivationListFragment.setMotivationStatements();
        webImage();
        showTitle();

    }

    // add or remove item to or from favorites from web activity
    public void btn_favorite(View view) {
        ImageView button = (ImageView) findViewById(R.id.web_favorite);

        favorites2 = sharedPreference.getFavorites(Webhtml.this);
        int x = 0;
        if (favorites2 != null)
            for (int i = 0; i < favorites2.size(); i++) {
                int id = favorites2.get(i).getId();
                if (pageNum == id) {
                    sharedPreference.removeFavorite(Webhtml.this, favorites2.get(i));
                    button.setImageResource(R.drawable.white_heart);
                    Toast.makeText(Webhtml.this, "تمت الإزالة من المفضلة", Toast.LENGTH_SHORT).show();
                    x = 1;
                }
            }


        if (x == 0) {

            sharedPreference.addFavorite(Webhtml.this, motivationListFragment.motivationStatements.get(pageNum));
            button.setImageResource(R.drawable.blue_heart);
            Toast.makeText(Webhtml.this, "تمت الإضافة للمفضلة ", Toast.LENGTH_SHORT).show();
        }
    }

    public void webImage() {

        ImageButton button = (ImageButton) findViewById(R.id.web_favorite);

        favorites2 = sharedPreference.getFavorites(Webhtml.this);
        int x = 0;
        if (favorites2 != null) {
            for (int i = 0; i < favorites2.size(); i++) {
                int id = favorites2.get(i).getId();
                if (pageNum == id) {
                    button.setImageResource(R.drawable.blue_heart);
                    x = 1;
                }
            }
            if (x == 0) {
                button.setImageResource(R.drawable.white_heart);
            }

        }else{
            button.setImageResource(R.drawable.white_heart);
        }


    }


    // back to home screen ( main screen)
    public void btn_Share(View view) {

        // btn_share.startAnimation(click);
        share();
    }


    public void share() {
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            InputStream inputStream = getAssets().open("html/page_" + pageNum + ".html");
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


    //  next statement
    // pageNum is the variable for html file numbers, they are 125 pages now
    public void btn_next(View view) {

        if (pageNum < 19) {
            nextButtonPressedTwice = nextButtonPressedTwice+1;

            // check to see if the user hits the next
            //button twice to display interestial ad
            if (nextButtonPressedTwice % 2 == 0){

                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();

                           // Toast.makeText(this, " inside ad method", Toast.LENGTH_LONG).show();
                        } else {
                            Log.d("TAG", "The interstitial wasn't loaded yet.  next button");
                        }

                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        // Load the next interstitial.
                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
                    }
                });

               // Toast.makeText(this, "even number " + nextButtonPressedTwice,Toast.LENGTH_LONG).show();
            }
                pageNum++;
                webView.loadUrl("file:///android_asset/html/page" + pageNum + ".html");
            } else {
                    // back to home, you have reached to the last statement
                    Toast.makeText(this, " لا توجد صفحات أخرى ", Toast.LENGTH_SHORT).show();
                finish();
            }
        webImage();
        showTitle();
    }

    //  Previous statement
    // pageNum is the variable for html file numbers, they are 125 pages now
    public void btn_back(View view) {

        if (pageNum > 0) {

            backButtonPressedTwice = backButtonPressedTwice + 1;

            // check to see if the user hits the next
            //button twice to display interestial ad
            if (backButtonPressedTwice % 2 == 0){

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();

                  //  Toast.makeText(this, " inside ad method", Toast.LENGTH_LONG).show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.  back button");
                }

                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        // Load the next interstitial.
                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
                    }
                });


               // Toast.makeText(this, "even number " + backButtonPressedTwice,Toast.LENGTH_LONG).show();
            }
            pageNum--;
            webView.loadUrl("file:///android_asset/html/page" + pageNum + ".html");
        } else {
            // back to home, you have reached to the first statement
            Toast.makeText(this, " لا توجد صفحات أخرى ", Toast.LENGTH_SHORT).show();
            finish();
        }
        webImage();
        showTitle();
    }

    public void showTitle() {
        if (pageNum == 0) {
            titleText.setText(" قد تكون أفضل الطرق أصعبها و لكن عليك دائما باتباعها ، إذ الإعتياد عليها سيجعل الأمور تبدو سهلة ");
        }
        if (pageNum == 1) {
            titleText.setText("الهروب هو السبب الوحيد في الفشل ، لذا فإنك تفشل طالما لم تتوقف عن المحاولة");
        }
        if (pageNum == 2) {
            titleText.setText("عندما أقوم ببناء فريق فإنى أبحث دائما عن أناس يحبون الفوز، و إذا لم أعثر على أى منهم فإننى أبحث عن أناس يكرهون الهزيمة - روبرت بروس");
        }
        if (pageNum == 3) {
            titleText.setText("  إن الاتجاه الذي يبدأ مع التعلم سوف يكون من شأنه أن يحدد حياه المرء في المستقبل");
        }
        if (pageNum == 4) {
            titleText.setText(" فى الحلبة كما فى خارجها، لا عيب فى أن تسقط أرضا. بل العيب فى أن تبقى على الأرض");
        }
        if (pageNum == 5) {
            titleText.setText(" إذا لم تحاول أن تفعل شيء أبعد مما قد أتقنته .. فأنك لا تتقدم أبدا ");
        }
        if (pageNum == 6) {
            titleText.setText(" الحياة إما أن تكون مغامرة جريئه  أو لا شيء");
        }
        if (pageNum == 7) {
            titleText.setText("ينقسم الفاشلون إلى نصفين: هؤلاء الذين يفكرون ولا يعملون، وهؤلاء الذين يعملون ولا يفكرون أبداً ");
        }
        if (pageNum == 8) {
            titleText.setText(" غالبا ما يكون النجاح حليف هؤلاء الذين يعملون بجرأة، ونادراً ما يكون حليف أولئك المترددين الذي يتهيبون المواقف ونتائجها ");
        }
        if (pageNum == 9) {
            titleText.setText(" لا يقاس النجاح بالموقع الذي يتبوأه المرء في حياته .. بقدر ما يقاس بالصعاب التي يتغلب عليها");
        }
        if (pageNum == 10) {
            titleText.setText("الحكمة الحقيقية ليست في رؤيا ما هو أمام عينيك فحسب !! بل هو التكهن ماذا سيحدث بالمستقبل ");
        }
        if (pageNum == 11) {
            titleText.setText(" في كل الأمور يتوقف النجاح على تحضير سابق وبدون مثل هذا التحضير لابد أن يكون هناك فشل");
        }
        if (pageNum == 12) {
            titleText.setText(" السعادة تكمن في متعه الإنجاز ونشوه المجهود المبدع");
        }
        if (pageNum == 13) {
            titleText.setText(" إن العالم يفسح الطريق للمرء الذي يعرف إلى أين هو ذاهب ");
        }
        if (pageNum == 14) {
            titleText.setText("نحن نسقط لكى ننهض، و نُهزم فى المعارك لنُحقق نصرا أروع. تمام كما ننام لكى نصحو أكثر قوة و نشاطا");
        }
        if (pageNum == 15) {
            titleText.setText("المنسحب لا يفوز أبدا و الفائز لا ينسحب مهمها كلفه الأمر ");
        }
        if (pageNum == 16) {
            titleText.setText(" ليس خطؤك أن تٌُولد فقيرا و لكنه بالتأكيد خطؤك أن تموت فقيرا معدما ");
        }
        if (pageNum == 17) {
            titleText.setText("عش حياتك كل يوم كما لو كنت ستصعد جبلا . و انظر بين الفينة والاخرى إلى القمة حتى لا تنس هدفك ، و لكن دون إضاعة الفرصة لرؤية المناظر الرائعة في كل مرحلة ");
        }
        if (pageNum == 18) {
            titleText.setText("خطوة واحدة لن تعبد طريقا على الارض , كما ان فكرة واحدة لن تغير شيئا في العقل , تتابع الخطوات ستعبد الطريق كما أن الكثير من التفكير سيصنع المستحيل الذي سيغير حياتنا ");
        }
        if (pageNum == 19) {
            titleText.setText("الحياه مليئة بالحجارة فلا تتعثر بها ، بل اجمعها و ابن بها سلماً تصعد به نحو النجاح ");
        }


    }

}
