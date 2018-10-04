package com.fil.githubapiexample.adapter.repository;

//import android.content.Context;
//
//import com.arellomobile.mvp.InjectViewState;
//import com.fil.githubapiexample.R;
//import com.fil.githubapiexample.helper.AppHelper;
//import com.fil.githubapiexample.model.Repository;
//import com.fil.githubapiexample.screens.base.presenter.BasePresenter;
//
//@InjectViewState
//public class RepositoryItemPresenter extends BasePresenter<RepositoryItemView> {
//
//    private final long CLICK_TIME_INTERVAL = 300;
//
//    private long mLastClickTime = System.currentTimeMillis();
//
//    public RepositoryItemPresenter(Context context, AppHelper appHelper) {
//        super(context, appHelper);
//    }
//
//    public void initUi(Repository repository) {
//        getViewState().showName(repository.getName());
//
//        String description = repository.getDescription();
//
//        if (appHelper.getStringHelper().isValidString(description))
//            getViewState().showDescription(description);
//        else getViewState().showDescription(context.getString(R.string.no_desc_text));
//    }
//
//    public void handleItemClick(Repository repository, int position, ReposItemInteractionListener listener) {
//        long now = System.currentTimeMillis();
//        if (now - mLastClickTime < CLICK_TIME_INTERVAL) {
//            return;
//        }
//        mLastClickTime = now;
//        listener.onReposItemClicked(repository, position);
//    }
//}
