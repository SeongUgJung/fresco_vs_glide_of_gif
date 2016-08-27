package com.nobrain.fresco_vs_glide;

import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    private static final String[] URL2 = {
            "https://docs.google.com/uc?id=0B3Dc_KI_InDOdFFaVlFZTlZxRlE&export=download",
            "https://docs.google.com/uc?id=0B3Dc_KI_InDOTFZaRV9UZDI0Rzg&export=download",
            "https://docs.google.com/uc?id=0B3Dc_KI_InDOUFBkbGU3Um1FWGc&export=download",
            "https://docs.google.com/uc?id=0B3Dc_KI_InDOTlI1MHVlcnNQLTg&export=download",
            "https://docs.google.com/uc?id=0B3Dc_KI_InDOMEFMaTNDT3FXcjg&export=download",
            "https://docs.google.com/uc?id=0B3Dc_KI_InDOa0RDOXpOUlpucjQ&export=download"};
    private static final String TAG = "MainActivity";
    private SimpleDraweeView[] frescos;
    private ImageView[] glides;
    private Button btnFresco;
    private Button btnGlide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this, OkHttpImagePipelineConfigFactory.newBuilder(this, new OkHttpClient.Builder().build()).build());
        setContentView(R.layout.activity_main);

        bindViews();

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

    private void bindViews() {
        frescos = new SimpleDraweeView[6];
        glides = new ImageView[6];

        frescos[0] = (SimpleDraweeView) findViewById(R.id.animated_gif_fresco_1);
        frescos[1] = (SimpleDraweeView) findViewById(R.id.animated_gif_fresco_2);
        frescos[2] = (SimpleDraweeView) findViewById(R.id.animated_gif_fresco_3);
        frescos[3] = (SimpleDraweeView) findViewById(R.id.animated_gif_fresco_4);
        frescos[4] = (SimpleDraweeView) findViewById(R.id.animated_gif_fresco_5);
        frescos[5] = (SimpleDraweeView) findViewById(R.id.animated_gif_fresco_6);

        glides[0] = (ImageView) findViewById(R.id.animated_gif_glide_1);
        glides[1] = (ImageView) findViewById(R.id.animated_gif_glide_2);
        glides[2] = (ImageView) findViewById(R.id.animated_gif_glide_3);
        glides[3] = (ImageView) findViewById(R.id.animated_gif_glide_4);
        glides[4] = (ImageView) findViewById(R.id.animated_gif_glide_5);
        glides[5] = (ImageView) findViewById(R.id.animated_gif_glide_6);


    }

    private void loadGlide() {

        int length = glides.length;
        for (int idx = 0; idx < length; idx++) {

            final long time = System.currentTimeMillis();
            Glide.with(MainActivity.this)
                    .load(URL2[idx])
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .error(new ColorDrawable(Color.CYAN))
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            Log.d(TAG, "onResourceReady: " + (System.currentTimeMillis() - time));
                            return false;
                        }
                    })
                    .into(glides[idx]);
        }
    }

    private void loadFresco() {

        int length = frescos.length;
        for (int idx = 0; idx < length; idx++) {

            final long time = System.currentTimeMillis();
            DraweeController animatedController = Fresco.newDraweeControllerBuilder()
                    .setAutoPlayAnimations(true)
                    .setUri(Uri.parse(URL2[idx]))
                    .setControllerListener(new BaseControllerListener<ImageInfo>() {
                        @Override
                        public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                            Log.d(TAG, "onFinalImageSet: " + (System.currentTimeMillis() - time));
                        }
                    })
                    .build();

            frescos[idx].setController(animatedController);
        }
    }
}
