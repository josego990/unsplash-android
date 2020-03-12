package com.marckclarck.pruebatecnicaeltaier;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import com.studioidan.httpagent.HttpAgent;
import com.studioidan.httpagent.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UnsplashActivity extends AppCompatActivity {

    String current_user = "";
    LinearLayout ll_photos_one = null;
    LinearLayout ll_photos_two = null;
    LinearLayout ll_photos_tree = null;
    String user_image_profile = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unsplash);

        current_user = getIntent().getStringExtra("current_user");

        Toast.makeText(UnsplashActivity.this,"USUARIO: " + current_user, Toast.LENGTH_LONG).show();

        ll_photos_one = findViewById(R.id.ll_photos_one);
        ll_photos_two = findViewById(R.id.ll_photos_two);
        ll_photos_tree = findViewById(R.id.ll_photos_tree);

        GetUnsplashUserInfo();

        FloatingActionButton fab = findViewById(R.id.fab_back);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void GetUnsplashUserInfo(){

        String url = "https://api.unsplash.com/users/" + current_user;

        HttpAgent.get(url).headers("Authorization","Client-ID 2ymXwgSZQbfwcvdhhXy2_wMriuzESeyhbdk9qQAyhVA").goString(new StringCallback() {
            @Override
            protected void onDone(boolean success, String stringResults) {

            try {

                CircularImageView iv_profile = findViewById(R.id.iv_unsplash_user_pic);
                TextView tv_name_user = findViewById(R.id.tv_unsplash_name_user);
                TextView tv_total_photos = findViewById(R.id.tv_photos);
                TextView tv_total_collections = findViewById(R.id.tv_collections);
                TextView tv_total_likes = findViewById(R.id.tv_likes);
                TextView tv_location = findViewById(R.id.tv_location);

                JSONObject json_res = new JSONObject(stringResults);
                JSONObject images_links = json_res.getJSONObject("profile_image");
                user_image_profile = images_links.getString("large");
                String name_user = json_res.getString("username");
                tv_name_user.setText(name_user);
                String total_photos = json_res.getString("total_photos");
                tv_total_photos.setText(total_photos);
                String total_colections = json_res.getString("total_collections");
                tv_total_collections.setText(total_colections);
                String total_likes = json_res.getString("total_likes");
                tv_total_likes.setText(total_likes);
                String location = json_res.getString("location");
                tv_location.setText(location);

                Picasso.get().load(user_image_profile).into(iv_profile);


                GetUnsplashUserPhotos();

                //Toast.makeText(UnsplashActivity.this,"images: " + images_links, Toast.LENGTH_LONG).show();

            } catch (JSONException e) {
                Toast.makeText(UnsplashActivity.this,"ERROR AL CONVERTIR A JSON: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            }
        });
    }

    private void GetUnsplashUserPhotos(){

        String url = "https://api.unsplash.com/users/"+current_user+"/photos";

        HttpAgent.get(url).headers("Authorization","Client-ID 2ymXwgSZQbfwcvdhhXy2_wMriuzESeyhbdk9qQAyhVA").goString(new StringCallback() {
            @Override
            protected void onDone(boolean success, String stringResults) {

                try {

                    JSONArray json_array = new JSONArray(stringResults);

                    int selector = 1;
                    for(int i=0;i<json_array.length();i++){

                        JSONObject json_object = json_array.getJSONObject(i);
                        JSONObject urls = json_object.getJSONObject("urls");
                        final String url_photo = urls.getString("thumb");
                        final String url_photo_regular = urls.getString("regular");
                        final String likes = json_object.getString("likes");

                        //LIKES
                        LinearLayout ll_likes = new LinearLayout(UnsplashActivity.this);
                        FrameLayout.LayoutParams lp_ll_likes = new FrameLayout.LayoutParams(
                                300,30);

                        lp_ll_likes.gravity = Gravity.BOTTOM;

                        ll_likes.setLayoutParams(lp_ll_likes);
                        ll_likes.setBackgroundColor(Color.parseColor("#80000000"));

                        ImageView iv_heart = new ImageView(UnsplashActivity.this);

                        iv_heart.setImageDrawable(getDrawable(R.drawable.ic_like));

                        LinearLayout.LayoutParams lp_heart = new LinearLayout.LayoutParams(
                                20,20);

                        lp_heart.leftMargin = 10;
                        lp_heart.topMargin = 5;

                        iv_heart.setLayoutParams(lp_heart);

                        TextView tv_likes = new TextView(UnsplashActivity.this);
                        tv_likes.setTextColor(Color.parseColor("#ffffff"));
                        tv_likes.setTextSize(10);
                        tv_likes.setText(" "+likes);

                        ll_likes.addView(iv_heart);
                        ll_likes.addView(tv_likes);

                        CircularImageView iv_user = new CircularImageView(UnsplashActivity.this);
                        FrameLayout.LayoutParams lp_img_user = new FrameLayout.LayoutParams(50,50);
                        lp_img_user.leftMargin = 5;
                        lp_img_user.topMargin = 5;

                        iv_user.setLayoutParams(lp_img_user);


                        CardView card = new CardView(UnsplashActivity.this);

                        ImageView iv_photo = new ImageView(UnsplashActivity.this);

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


                        TextView tv_user_name = new TextView(UnsplashActivity.this);

                        tv_user_name.setTextColor(Color.parseColor("#ffffff"));
                        tv_user_name.setTextSize(10.0f);


                        card.addView(ll_likes);



                        iv_photo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ImageDialog pauseDialog = new ImageDialog(
                                        UnsplashActivity.this, url_photo_regular,user_image_profile,current_user,likes);
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
                    Toast.makeText(UnsplashActivity.this,"ERROR AL CONVERTIR A JSON: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
