package com.marckclarck.pruebatecnicaeltaier;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * Created by JuanJose on 28/08/2018.
 */

public class ImageDialog extends Dialog implements
        View.OnClickListener {

    public Activity c;
    public String url;
    public String url_profile;
    public Dialog d;
    public Button btn_continue, btn_exit, btn_restart, btn_settings;
    public ImageView iv_photo = null;
    public ImageView iv_photo_prof = null;
    public TextView et_name = null;
    public String user_name = null;
    public LinearLayout ll_user_info = null;
    public String likes = "";
    public TextView tv_likes = null;

    public ImageDialog(Activity a, String url, String url_profile, String user_name, String likes) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.url = url;
        this.url_profile = url_profile;
        this.user_name = user_name;
        this.likes = likes;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_image);

        iv_photo = findViewById(R.id.iv_photo);
        iv_photo_prof = findViewById(R.id.iv_rounded_photo);
        et_name = findViewById(R.id.et_current_user_name);
        ll_user_info = findViewById(R.id.ll_user_info);
        tv_likes = findViewById(R.id.likes);

        tv_likes.setText(" "+likes);

        et_name.setText(" @"+user_name);

        ll_user_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(c.getApplicationContext(), UnsplashActivity.class);

                i.putExtra("current_user", user_name.toString());

                c.startActivity(i);
            }
        });

        //


        Picasso.get().load(url).fit().into(iv_photo);
        try {
            Picasso.get().load(url_profile).into(iv_photo_prof);
        }
        catch (Exception ex){}

        //Toast.makeText(getContext(),url,Toast.LENGTH_LONG).show();



    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getContext(),"", Toast.LENGTH_LONG).show();
        /*
        switch (v.getId()) {
            case R.id.btn_exit:
                Intent i = new Intent(c,StartActivity.class);
                c.startActivity(i);
                c.finish();
                dismiss();
                break;
            case R.id.btn_continue:
                PuzzleActivity.ResumeGame();
                dismiss();
                break;
            case R.id.btn_restart:
                c.recreate();
                dismiss();
                break;
            case R.id.btn_settings:
                SettingsDialog sDialog = new SettingsDialog(c,false);
                sDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                sDialog.getWindow().getAttributes().windowAnimations = R.style.DialogPauseAnimation;
                sDialog.show();
                break;
        }
*/
    }
}
