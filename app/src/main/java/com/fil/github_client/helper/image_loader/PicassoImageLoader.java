package com.fil.github_client.helper.image_loader;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

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
                .into(target);
    }
}
