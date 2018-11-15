package com.fil.github_client.screens.details;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.fil.github_client.MyApplication;
import com.fil.github_client.R;
import com.fil.github_client.base.RepositoryViewController;
import com.fil.github_client.base.FragmentScreenView;
import com.fil.github_client.base.back_stack.BackStackProvider;
import com.fil.github_client.model.GitRepository;
import com.fil.github_client.network.ErrorResponseHandler;
import com.fil.github_client.repository.github_repositories.GithubRepositoriesInteraction;
import com.fil.github_client.screens.edit.EditRepositoryFragment;
import com.fil.github_client.util.Const;

public class RepositoryDetailsFragment extends FragmentScreenView implements RepositoryDetailsView, RepositoryViewController {

    private ImageView   avatarImageView;
    private TextView    ownerNameTextView;
    private TextView    repoNameTextView;
    private TextView    repoDescTextView;
    private TextView    starsCountTextView;
    private ProgressBar progressBar;

    private GitRepository gitRepository;

    @InjectPresenter
    public RepositoryDetailsPresenter presenter;

    @ProvidePresenter
    RepositoryDetailsPresenter providePresenter() {
        GithubRepositoriesInteraction githubRepositoriesInteraction = new GithubRepositoriesInteraction(
                MyApplication.getGithubRepositoriesRepository(),
                new ErrorResponseHandler());

        return new RepositoryDetailsPresenter(
                getContext(),
                githubRepositoriesInteraction,
                MyApplication.getAppHelper());
    }

    public static RepositoryDetailsFragment newInstance(GitRepository repository) {
        RepositoryDetailsFragment fragment = new RepositoryDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(Const.REPOSITORY_EXTRA_KEY, repository);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.view_repository_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(Const.REPOSITORY_EXTRA_KEY)) {
            gitRepository = getArguments().getParcelable(Const.REPOSITORY_EXTRA_KEY);
        } else {
            throw new IllegalArgumentException("No Repository Extra!");
        }

        progressBar = view.findViewById(R.id.avatar_progress_bar);
        avatarImageView = view.findViewById(R.id.user_avatar_image_view);
        ownerNameTextView = view.findViewById(R.id.user_name_text_view);
        starsCountTextView = view.findViewById(R.id.stars_count_text_view);
        repoNameTextView = view.findViewById(R.id.repo_name_text_view);
        repoDescTextView = view.findViewById(R.id.desc_full_text_view);

        presenter.initUi(presenter.wrapRepositoryIntoIntent(gitRepository));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.repos_details_menu, menu);
        presenter.initToolbar(gitRepository);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_action:
                presenter.editRepository();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onDataChanged(Intent data) {
        presenter.changeData(data);
    }

    @Override
    public void showRepositoryEditView(GitRepository gitRepository) {
        EditRepositoryFragment fragment = EditRepositoryFragment.newInstance(gitRepository);
        if (getActivity() instanceof BackStackProvider) {
            ((BackStackProvider) getActivity()).getBackStackManager().addChildFragment(fragment, R.id.main_activity_content);
        }
    }

    @Override
    public void showOwnerAvatar(Bitmap avatarBitmap) {
        avatarImageView.setImageBitmap(avatarBitmap);
    }

    @Override
    public void showAvatarProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideAvatarProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showOwnerName(String ownerName) {
        ownerNameTextView.setText(ownerName);
    }

    @Override
    public void showRating(int starsCount) {
        starsCountTextView.setText(String.valueOf(starsCount));
    }

    @Override
    public void showRepositoryName(String repositoryName) {
        repoNameTextView.setText(repositoryName);
    }

    @Override
    public void showRepositoryDescription(String description) {
        repoDescTextView.setText(description);
    }
}
