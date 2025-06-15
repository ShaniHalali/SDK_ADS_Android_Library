package com.example.adslib;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class AdView extends FrameLayout {

    private TextView adview_title, adview_description, adview_location;
    private ImageView adview_img;
    private ExtendedFloatingActionButton adview_exit;
    private MaterialButton adview_adLink;
    private VideoView adview_video;
    private Ad currentAd;

    public AdView(Context context) {
        super(context);
        init(context);
    }

    public AdView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AdView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_ad, this, true);
        adview_title = findViewById(R.id.adview_title);
        adview_description = findViewById(R.id.adview_description);
        adview_location = findViewById(R.id.adview_location);
        adview_img = findViewById(R.id.adview_img);
        adview_exit = findViewById(R.id.adview_exit);
        adview_adLink = findViewById(R.id.adview_adLink);
        adview_video = findViewById(R.id.adview_video);

        //adview_exit.setOnClickListener(v -> setVisibility(GONE));
        adLinkButtonListener();
    }
    public void closeAdView(){
        this.setVisibility(GONE);
        if (currentAd != null && "video".equals(currentAd.getAd_type())){
            adview_video.stopPlayback();
            adview_video.seekTo(0);
        }
    }

    public void showExitButtonWithDelay(int seconds) {
        adview_exit.setVisibility(INVISIBLE);
        new android.os.Handler().postDelayed(() -> {
            adview_exit.setVisibility(VISIBLE);
            adview_exit.setOnClickListener(v -> closeAdView());
        }, seconds * 1000L);
    }




    private void adLinkButtonListener() {

        adview_adLink.setOnClickListener(v -> {
            if (currentAd != null) {
                moveToLink(currentAd);
            }
        });

    }

    public void loadAd(Ad ad) {
        this.currentAd = ad;

        adview_title.setText(ad.getName());
        adview_description.setText(ad.getDescription());
        adview_location.setText("Location: " + ad.getAd_location());
        loadAdImage(ad);

/*
        // Load image using Glide
        Glide.with(getContext())
                .load(ad.getAd_image_link())
                .into(adview_img);*/
    }

    private void loadAdImage(Ad ad){
        if ("photo".equals(ad.getAd_type())) {
            adview_img.setVisibility(View.VISIBLE);
            adview_video.setVisibility(View.GONE);

            Glide.with(getContext())
                    .load(ad.getAd_image_link())
                    .into(adview_img);
        } else if ("video".equals(ad.getAd_type())) {
            adview_img.setVisibility(View.GONE);
            adview_video.setVisibility(View.VISIBLE);

            adview_video.setVideoURI(Uri.parse(ad.getAd_image_link()));

            adview_video.setOnPreparedListener(mp -> {
                mp.setLooping(true);
                adview_video.start();
            });


        }

    }


    private void moveToLink(Ad ad) {
        String link = ad.getAd_link();
        if (link != null && !link.isEmpty()) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            getContext().startActivity(browserIntent);
        }
    }
}
