package com.fil.github_client.helper.image_loader;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PicassoImageLoader implements ImageLoader {
    @Override
    public void loadImage(String url, ImageView imageView) {
        Picasso.get()
                .load(url)
                .into(imageView);
    }

    @Override
    public void loadImage(String url, Target target) {
        Picasso.get()
                .load(url)
                .into(new com.squareup.picasso.Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        target.onBitmapLoaded(bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        target.onBitmapFailed(e,errorDrawable);
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        target.onPrepareLoad(placeHolderDrawable);
                    }
                });
    }
}
