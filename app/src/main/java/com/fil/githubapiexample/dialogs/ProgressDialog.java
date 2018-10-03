package com.fil.githubapiexample.dialogs;

import android.app.Dialog;
import android.os.Bundle;

import com.brouding.simpledialog.SimpleDialog;
import com.fil.githubapiexample.R;


public class ProgressDialog extends BaseDialog {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new SimpleDialog.Builder(getActivity())
                .setContent(getString(R.string.progress_dialog_text), 7)
                .showProgress(true)
                .setBtnCancelText(getString(android.R.string.no))
                .setCancelable(false)
                .show();
    }
}
