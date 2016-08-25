package com.nobrain.fresco_vs_glide;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.image.ImageInfo;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    private static final String URL = "https://docs.google.com/uc?authuser=0&id=0B3Dc_KI_InDOdFFaVlFZTlZxRlE&export=download";
    private SimpleDraweeView fresco;
    private ImageView glide;
    private Button btnFresco;
    private Button btnGlide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this, OkHttpImagePipelineConfigFactory.newBuilder(this, new OkHttpClient.Builder().build()).build());
        setContentView(R.layout.activity_main);


        fresco = (SimpleDraweeView) findViewById(R.id.animated_gif_fresco);

        glide = (ImageView) findViewById(R.id.animated_gif_glide);


        btnFresco = (Button) findViewById(R.id.btn_fresco);
        btnFresco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                loadFresco();

            }
        });

        btnGlide = (Button) findViewById(R.id.btn_glide);
        btnGlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                loadGlide();

            }
        });

        findViewById(R.id.btn_both).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFresco();
                loadGlide();
            }
        });

    }

    private void loadGlide() {
        final long time = System.currentTimeMillis();

        Glide.with(MainActivity.this)
                .load(URL)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        long last = System.currentTimeMillis();

                        float diff = (last - time) / 1000f;
                        btnGlide.setText(String.format("%.2f", diff));
                        return false;
                    }
                })
                .into(glide);
    }

    private void loadFresco() {
        final long time = System.currentTimeMillis();

        DraweeController animatedController = Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(true)
                .setUri(Uri.parse(URL))
                .setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        long last = System.currentTimeMillis();

                        float diff = (last - time) / 1000f;
                        btnFresco.setText(String.format("%.2f", diff));
                    }
                })
                .build();

        fresco.setController(animatedController);
    }
}
