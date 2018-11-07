package com.fil.github_client.helper.image_loader;

import android.widget.ImageView;

public interface ImageLoader {
    void loadImage(String url, ImageView imageView);

    void loadImage(String url, com.squareup.picasso.Target target);
}
