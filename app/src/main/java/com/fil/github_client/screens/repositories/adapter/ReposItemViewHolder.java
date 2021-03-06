package com.fil.github_client.screens.repositories.adapter;

import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fil.github_client.R;
import com.fil.github_client.model.GitRepository;
import com.fil.github_client.util.Utilities;

public class ReposItemViewHolder extends RecyclerView.ViewHolder {
    private static final long CLICK_TIME_INTERVAL = 1000;
    private static       long mLastClickTime      = System.currentTimeMillis();

    private View layout;

    private TextView nameTextView;
    private TextView descTextView;
    private TextView menuTextView;

    public ReposItemViewHolder(View v) {
        super(v);
        layout = v;
        nameTextView = v.findViewById(R.id.repo_name_text_view);
        descTextView = v.findViewById(R.id.repo_desc_text_view);
        menuTextView = v.findViewById(R.id.repo_menu_text_view);
    }

    public void bindViews(GitRepository gitRepository, RepositoryListItemInteractionListener listener) {
        nameTextView.setText(gitRepository.getName());

        String description = gitRepository.getDescription();

        if (Utilities.isValidString(description))
            descTextView.setText(description);
        else descTextView.setText(layout.getContext().getString(R.string.no_desc_text));


        layout.setOnClickListener(view -> {
            long now = System.currentTimeMillis();
            if (now - mLastClickTime < CLICK_TIME_INTERVAL) {
                return;
            }
            mLastClickTime = now;
            listener.onRepositoryItemClicked(gitRepository, getAdapterPosition());
        });

        menuTextView.setOnClickListener(view -> {
            PopupMenu popup = new PopupMenu(menuTextView.getContext(), menuTextView);
            popup.inflate(R.menu.repository_item_popup_menu);
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.delete_action:
                        listener.onRepositoryMenuDeleteItemClicked(gitRepository, getAdapterPosition());
                        return true;
                    default:
                        return false;
                }
            });
            popup.show();
        });
    }
}
