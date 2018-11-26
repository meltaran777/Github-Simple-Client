package com.fil.github_client.di.modules;

import com.fil.github_client.helper.AppHelper;
import com.fil.github_client.helper.NetworkHelper;
import com.fil.github_client.helper.RxHelper;
import com.fil.github_client.helper.StringHelper;
import com.fil.github_client.helper.ValidateHelper;
import com.fil.github_client.helper.image_loader.ImageLoader;
import com.fil.github_client.helper.image_loader.PicassoImageLoader;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public class AppHelperModule {
    @Provides
    @Singleton
    public AppHelper provideAppHelper(RxHelper rxHelper,
                                      NetworkHelper networkHelper,
                                      ValidateHelper validateHelper,
                                      StringHelper stringHelper,
                                      ImageLoader imageLoader) {
        return new AppHelper(rxHelper, networkHelper, validateHelper, stringHelper, imageLoader);
    }

    @Provides
    @Singleton
    public RxHelper provideRxHelper() {
        return new RxHelper(Schedulers.io(), AndroidSchedulers.mainThread());
    }

    @Provides
    @Singleton
    public NetworkHelper provideNetworkHelper() {
        return new NetworkHelper();
    }

    @Provides
    @Singleton
    public ValidateHelper provideValidateHelper() {
        return new ValidateHelper();
    }

    @Provides
    @Singleton
    public StringHelper provideStringHelper() {
        return new StringHelper();
    }

    @Provides
    @Singleton
    public ImageLoader provideImageLoader() {
        return new PicassoImageLoader();
    }
}
