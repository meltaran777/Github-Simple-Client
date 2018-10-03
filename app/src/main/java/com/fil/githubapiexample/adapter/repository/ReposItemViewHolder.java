package com.fil.githubapiexample.adapter.repository;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fil.githubapiexample.R;
import com.fil.githubapiexample.model.Repository;
import com.fil.githubapiexample.util.Utilities;

public class ReposItemViewHolder extends RecyclerView.ViewHolder {

    private View layout;

    private TextView  nameTextView;
    private TextView  descTextView;
    private ImageView menuImageView;

    public ReposItemViewHolder(View v) {
        super(v);
        layout = v;
        nameTextView = v.findViewById(R.id.repo_name_text_view);
        descTextView = v.findViewById(R.id.repo_desc_text_view);
        menuImageView = v.findViewById(R.id.repo_menu_image_view);
    }

    public void bindViews(Repository repository, ReposItemInteractionListener listener) {
        nameTextView.setText(repository.getName());

        String description = repository.getDescription();

        if (Utilities.isValidString(description))
            descTextView.setText(description);
        else descTextView.setText(layout.getContext().getString(R.string.no_desc_text));

        layout.setOnClickListener(view ->
                listener.onReposItemClicked(repository, getAdapterPosition()));
    }
}
