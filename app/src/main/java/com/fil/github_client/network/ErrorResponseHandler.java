package com.fil.github_client.network;

import com.fil.github_client.util.Const;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.HttpException;
import timber.log.Timber;

public class ErrorResponseHandler {
    private static final String LOG_TAG = ErrorResponseHandler.class.getSimpleName();

    private List<ErrorHandler> errorHandlers;

    public ErrorResponseHandler() {
        errorHandlers = new ArrayList<>();
    }

    public void handlerError(Throwable throwable) {
        try {
            if (throwable instanceof HttpException) {
                throwable.printStackTrace();

                ResponseBody body = ((HttpException) throwable).response().errorBody();
                int httpErrorCode = ((HttpException) throwable).code();

                JSONObject responseJsonObject = null;
                if (body != null) {
                    responseJsonObject = new JSONObject(body.string());
                }
                String messageString = "";
                if (responseJsonObject != null) {
                    messageString = responseJsonObject.getString("message");
                }

                notifyError(httpErrorCode, messageString);

                Timber.d("HTTP error code: %s", httpErrorCode);
            } else if (throwable != null) {
                notifyError(Const.DEFAULT_ERROR_CODE, throwable.getMessage());
                Timber.e(throwable);
                throwable.printStackTrace();
            } else {
                String message = "Error: throwable == NULL!";
                notifyError(Const.DEFAULT_ERROR_CODE, message);
                Timber.e(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void notifyError(int code, String msg) {
        for (ErrorHandler errorHandler : errorHandlers) {
            errorHandler.handleError(code, msg);
        }
    }

    public void subscribe(ErrorHandler errorHandler) {
        errorHandlers.add(errorHandler);
    }

    public void unsubscribe(ErrorHandler errorHandler) {
        errorHandlers.remove(errorHandler);
    }
}
