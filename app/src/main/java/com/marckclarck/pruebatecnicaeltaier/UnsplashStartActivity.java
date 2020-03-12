package com.marckclarck.pruebatecnicaeltaier;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.squareup.picasso.Picasso;
import com.studioidan.httpagent.HttpAgent;
import com.studioidan.httpagent.StringCallback;

import org.json.JSONArray;
import org.json.JSONObject;

public class UnsplashStartActivity extends AppCompatActivity {

    EditText et_current_user = null;
    Button btn_go_to_activity = null;

    String current_user = "";
    LinearLayout ll_photos_one = null;
    LinearLayout ll_photos_two = null;
    LinearLayout ll_photos_tree = null;

    boolean is_first_request = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unsplash_start);

        btn_go_to_activity = findViewById(R.id.btn_go_to_activity);
        et_current_user = findViewById(R.id.et_current_user);

        ll_photos_one = findViewById(R.id.ll_photos_one);
        ll_photos_two = findViewById(R.id.ll_photos_two);
        ll_photos_tree = findViewById(R.id.ll_photos_tree);

        btn_go_to_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ll_photos_one.removeAllViews();
                ll_photos_two.removeAllViews();
                ll_photos_tree.removeAllViews();

                GetUnsplashImagesByFilter();

                /*
                Intent i = new Intent(UnsplashStartActivity.this, UnsplashActivity.class);

                i.putExtra("current_user", et_current_user.getText().toString());

                startActivity(i);
                */
            }
        });

        GetUnsplashImagesByFilter();

    }

    private void GetUnsplashImagesByFilter(){

        final String url;

        if(is_first_request) {
            url = "https://api.unsplash.com/photos?per_page=10&query=" +
                    this.et_current_user.getText().toString() + "&orientation=portrait";
        }
        else
        {
           url = "https://api.unsplash.com/search/photos?per_page=10&query=" +
                    this.et_current_user.getText().toString() + "&orientation=portrait";
        }
        HttpAgent.get(url).headers("Authorization","Client-ID 2ymXwgSZQbfwcvdhhXy2_wMriuzESeyhbdk9qQAyhVA")
                .goString(new StringCallback() {
            @Override
            protected void onDone(boolean success, String stringResults) {

                try {

                    JSONObject json_object_main = null;
                    JSONArray json_array = null;

                    if(is_first_request){
                        json_array = new JSONArray(stringResults);
                        is_first_request = false;
                    }
                    else {
                        json_object_main = new JSONObject(stringResults);
                        json_array = json_object_main.getJSONArray("results");
                    }

                    int selector = 1;
                    for(int i=0;i<json_array.length();i++){

                        JSONObject json_object = json_array.getJSONObject(i);
                        JSONObject urls = json_object.getJSONObject("urls");
                        final String url_photo = urls.getString("thumb");
                        final String url_photo_regular = urls.getString("regular");
                        final String likes = json_object.getString("likes");
                        //
                        JSONObject json_object_user = json_object.getJSONObject("user");
                        JSONObject json_object_user_image_profile = json_object_user.getJSONObject("profile_image");
                        final String user_name = json_object_user.getString("username");
                        String name = json_object_user.getString("name");

                        final String url_image = json_object_user_image_profile.getString("medium");

                        //USERINFO
                        LinearLayout ll_user_info = new LinearLayout(UnsplashStartActivity.this);
                        FrameLayout.LayoutParams lp_ll_user_info = new FrameLayout.LayoutParams(
                                300,60);

                        //LIKES
                        LinearLayout ll_likes = new LinearLayout(UnsplashStartActivity.this);
                        FrameLayout.LayoutParams lp_ll_likes = new FrameLayout.LayoutParams(
                                300,30);

                        lp_ll_likes.gravity = Gravity.BOTTOM;

                        ll_likes.setLayoutParams(lp_ll_likes);
                        ll_likes.setBackgroundColor(Color.parseColor("#80000000"));

                        ImageView iv_heart = new ImageView(UnsplashStartActivity.this);

                        iv_heart.setImageDrawable(getDrawable(R.drawable.ic_like));

                        LinearLayout.LayoutParams lp_heart = new LinearLayout.LayoutParams(
                                20,20);

                        lp_heart.leftMargin = 10;
                        lp_heart.topMargin = 5;

                        iv_heart.setLayoutParams(lp_heart);

                        TextView tv_likes = new TextView(UnsplashStartActivity.this);
                        tv_likes.setTextColor(Color.parseColor("#ffffff"));
                        tv_likes.setTextSize(10);
                        tv_likes.setText(" "+likes);

                        ll_likes.addView(iv_heart);
                        ll_likes.addView(tv_likes);

                        ll_user_info.setLayoutParams(lp_ll_user_info);
                        ll_user_info.setBackgroundColor(Color.parseColor("#80000000"));
                        CircularImageView iv_user = new CircularImageView(UnsplashStartActivity.this);
                        FrameLayout.LayoutParams lp_img_user = new FrameLayout.LayoutParams(50,50);
                        lp_img_user.leftMargin = 5;
                        lp_img_user.topMargin = 5;

                        iv_user.setLayoutParams(lp_img_user);

                        Picasso.get().load(url_image).into(iv_user);

                        CardView card = new CardView(UnsplashStartActivity.this);

                        ImageView iv_photo = new ImageView(UnsplashStartActivity.this);

                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);

                        FrameLayout.LayoutParams lp_img = new FrameLayout.LayoutParams(400,400);

                        lp_img.gravity = Gravity.CENTER;
                        lp_img.bottomMargin = -10;
                        lp_img.topMargin = -10;
                        lp_img.leftMargin = -10;
                        lp_img.rightMargin = -10;

                        iv_photo.setLayoutParams(lp_img);

                        lp.bottomMargin = 5;
                        card.setLayoutParams(lp);

                        card.setElevation(5.0f);
                        card.setRadius(20.0f);

                        card.addView(iv_photo);
                        ll_user_info.addView(iv_user);

                        TextView tv_user_name = new TextView(UnsplashStartActivity.this);
                        tv_user_name.setText(" @"+user_name);
                        tv_user_name.setTextColor(Color.parseColor("#ffffff"));
                        tv_user_name.setTextSize(10.0f);


                        ll_user_info.addView(tv_user_name);
                        card.addView(ll_user_info);
                        card.addView(ll_likes);


                        ll_user_info.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(UnsplashStartActivity.this, UnsplashActivity.class);

                                i.putExtra("current_user", user_name.toString());

                                startActivity(i);
                            }
                        });

                        iv_photo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ImageDialog pauseDialog = new ImageDialog(
                                        UnsplashStartActivity.this, url_photo_regular,url_image,user_name,likes);
                                pauseDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                pauseDialog.getWindow().getAttributes().windowAnimations = R.style.ImageDialogAnimation;
                                pauseDialog.show();
                            }
                        });

                        switch (selector){
                            case 1:
                                ll_photos_one.addView(card);
                                selector++;
                                break;
                            case 2:
                                ll_photos_two.addView(card);
                                selector++;
                                break;
                            case 3:
                                ll_photos_tree.addView(card);
                                selector = 1;
                                break;
                        }


                        Picasso.get().load(url_photo).fit().into(iv_photo);

                    }

                    //Toast.makeText(UnsplashActivity.this,"photos: " + stringResults, Toast.LENGTH_LONG).show();

                }
                catch (Exception e) {
                    Toast.makeText(UnsplashStartActivity.this,"ERROR AL CONVERTIR A JSON: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
