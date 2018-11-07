package com.fil.github_client.helper;

import com.fil.github_client.helper.image_loader.ImageLoader;
import com.fil.github_client.network.ErrorResponseHandler;

public class AppHelper {

    private final RxHelper             rxHelper;
    private final NetworkHelper        networkHelper;
    private final ValidateHelper       validateHelper;
    private final StringHelper         stringHelper;
    private final ImageLoader          imageLoader;
    private final ErrorResponseHandler errorResponseHandler;

    public AppHelper(RxHelper rxHelper, NetworkHelper networkHelper,
                     ValidateHelper validateHelper, StringHelper stringHelper,
                     ImageLoader imageLoader, ErrorResponseHandler errorResponseHandler) {
        this.rxHelper = rxHelper;
        this.networkHelper = networkHelper;
        this.validateHelper = validateHelper;
        this.stringHelper = stringHelper;
        this.imageLoader = imageLoader;
        this.errorResponseHandler = errorResponseHandler;
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

    public ErrorResponseHandler getErrorResponseHandler() {
        return errorResponseHandler;
    }

}
