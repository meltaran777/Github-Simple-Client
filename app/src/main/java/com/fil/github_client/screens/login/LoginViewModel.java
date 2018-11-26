package com.fil.github_client.screens.login;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

public class LoginViewModel extends AndroidViewModel {
    private static final String TAG = LoginViewModel.class.getName();

    private static final MutableLiveData ABSENT = new MutableLiveData();

    {
        ABSENT.setValue(null);
    }


    public LoginViewModel(@NonNull Application application) {
        super(application);
    }


}
