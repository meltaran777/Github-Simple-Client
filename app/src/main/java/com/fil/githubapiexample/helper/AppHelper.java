package com.fil.githubapiexample.helper;

import android.content.Context;

public class AppHelper {

    private NetworkHelper  networkHelper;
    private ValidateHelper validateHelper;
    private StringHelper   stringHelper;

    public AppHelper(NetworkHelper networkHelper, ValidateHelper validateHelper, StringHelper stringHelper) {
        this.networkHelper = networkHelper;
        this.validateHelper = validateHelper;
        this.stringHelper = stringHelper;
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
}
