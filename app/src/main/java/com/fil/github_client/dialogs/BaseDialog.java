package com.fil.github_client.dialogs;

import android.app.DialogFragment;
import android.content.DialogInterface;

public abstract class BaseDialog extends DialogFragment {

    private boolean canShow = true;

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        canShow = true;
    }

    public boolean isCanShow() {
        return canShow;
    }

    public void setCanShow(boolean canShow) {
        this.canShow = canShow;
    }
}
