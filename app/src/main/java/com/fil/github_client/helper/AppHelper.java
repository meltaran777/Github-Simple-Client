package com.fil.github_client.helper;

import com.fil.github_client.helper.image_loader.ImageLoader;
import com.fil.github_client.network.ErrorResponseHandler;

public class AppHelper {

    private final RxHelper             rxHelper;
    private final NetworkHelper        networkHelper;
    private final ValidateHelper       validateHelper;
    private final StringHelper         stringHelper;
    private final ImageLoader          imageLoader;

    public AppHelper(RxHelper rxHelper, NetworkHelper networkHelper,
                     ValidateHelper validateHelper, StringHelper stringHelper,
                     ImageLoader imageLoader) {
        this.rxHelper = rxHelper;
        this.networkHelper = networkHelper;
        this.validateHelper = validateHelper;
        this.stringHelper = stringHelper;
        this.imageLoader = imageLoader;
    }

    public NetworkHelper getNetworkHelper() {
        return networkHelper;
    }

    public ValidateHelper getValidateHelper() {
        return validateHelper;
    }

    public StringHelper getStringHelper() {
        return stringHelper;
    }

    public RxHelper getRxHelper() {
        return rxHelper;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}
