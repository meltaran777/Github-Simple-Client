package com.fil.github_client.base.presenter;

import android.content.Context;

import com.fil.github_client.helper.AppHelper;
import com.fil.github_client.base.ScreenView;
import com.fil.github_client.network.ErrorHandler;
import com.fil.github_client.repository.BaseInteraction;
import com.fil.github_client.repository.InteractionListener;

;

public abstract class BaseInteractionPresenter<V extends ScreenView, I extends BaseInteraction<L>, L extends InteractionListener>
        extends BasePresenter<V>
        implements ErrorHandler {

    protected I interaction;

    public BaseInteractionPresenter(Context context, I interaction, AppHelper appHelper) {
        super(context, appHelper);
        this.interaction = interaction;
        interaction.getErrorResponseHandler().subscribe(this);
    }

    @Override
    public void onDestroy() {
        interaction.getErrorResponseHandler().unsubscribe(this);
        interaction.destroy();
        super.onDestroy();
    }

    @Override
    public void handleError(int code, String errorMsg) {
        getViewState().hideProgress();
        getViewState().hideKeyboard();
        getViewState().showSnackbar(errorMsg, ScreenView.SNACK_DURATION);
    }
}
