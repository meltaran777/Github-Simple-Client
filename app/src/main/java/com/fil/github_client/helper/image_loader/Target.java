package com.fil.github_client.helper.image_loader;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public interface Target {
    void onBitmapLoaded(Bitmap bitmap);

    void onBitmapFailed(Exception e, Drawable errorDrawable);

    void onPrepareLoad(Drawable placeHolderDrawable);
}
