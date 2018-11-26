package com.fil.github_client.screens.edit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.fil.github_client.R;
import com.fil.github_client.base.FragmentScreenView;
import com.fil.github_client.base.RepositoryViewController;
import com.fil.github_client.model.GitRepository;
import com.fil.github_client.util.Const;

public class EditRepositoryFragment extends FragmentScreenView implements RepositoryEditView {

    EditText repoNameEditText;
    EditText repoDescEditText;

    private GitRepository gitRepository;

    @InjectPresenter
    RepositoryEditPresenter presenter;

    public static EditRepositoryFragment newInstance(GitRepository repository) {
        EditRepositoryFragment fragment = new EditRepositoryFragment();
        Bundle args = new Bundle();
        args.putParcelable(Const.REPOSITORY_EXTRA_KEY, repository);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.view_edit_repository, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(Const.REPOSITORY_EXTRA_KEY)) {
            gitRepository = getArguments().getParcelable(Const.REPOSITORY_EXTRA_KEY);
        } else {
            throw new IllegalArgumentException("No Repository Extra!");
        }

        repoNameEditText = view.findViewById(R.id.repo_edit_text);
        repoDescEditText = view.findViewById(R.id.desc_full_edit);

        presenter.init(gitRepository);
    }

    @Override
    public void setViewResult(int resultCode, Intent data) {
        if (resultCode == Const.REPOSITORY_EDITED_RESULT_CODE) {
            if (getActivity() instanceof RepositoryViewController) {
                ((RepositoryViewController) getActivity()).onRepositoryEdited(data);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit_details_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_action:
                presenter.editRepository(getName(), getDescription());
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void showRepositoryName(String repositoryName) {
        repoNameEditText.setText(repositoryName);
    }

    @Override
    public void showRepositoryDescription(String description) {
        repoDescEditText.setText(description);
    }

    private String getName() {
        return String.valueOf(repoNameEditText.getText());
    }

    private String getDescription() {
        return String.valueOf(repoDescEditText.getText());
    }
}
