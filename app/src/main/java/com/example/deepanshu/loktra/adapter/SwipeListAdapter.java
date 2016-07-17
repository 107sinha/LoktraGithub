package com.example.deepanshu.loktra.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.deepanshu.loktra.R;
import com.example.deepanshu.loktra.model.GitService;

import java.util.List;

/**
 * Created by deepanshu on 14/7/16.
 */
public class SwipeListAdapter extends RecyclerView.Adapter<SwipeListAdapter.ViewHolder> {

    Context mContext;
    private List<GitService> mGithubList;


    public SwipeListAdapter(Context context, List<GitService> mGithubList) {
        this.mContext = context;
        this.mGithubList = mGithubList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.github_list_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        GitService github = mGithubList.get(position);

        holder.someperson.setText(github.getSome_person());
        holder.commit.setText(github.getCommit_time());
        holder.commitmessage.setText(github.getCommit_message());

    }

    @Override
    public int getItemCount() {
        return mGithubList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView someperson, commit, commitmessage;

        public ViewHolder(View itemView) {
            super(itemView);

            someperson = (TextView) itemView.findViewById(R.id.some_person);
            commit = (TextView) itemView.findViewById(R.id.commit_time);
            commitmessage = (TextView) itemView.findViewById(R.id.commit_message);

        }
    }
}
