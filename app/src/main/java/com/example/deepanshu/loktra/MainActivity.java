package com.example.deepanshu.loktra;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.deepanshu.loktra.adapter.DividerItemDecoration;
import com.example.deepanshu.loktra.adapter.SwipeListAdapter;
import com.example.deepanshu.loktra.model.GitService;
import com.example.deepanshu.loktra.model.Github;
import com.example.deepanshu.loktra.rest.GitClient;
import com.example.deepanshu.loktra.rest.GitTask;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private SwipeListAdapter mSwipeListAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<GitService> mGithubList = new ArrayList<>();

    // initially offset will be 0, later will be updated while parsing the json
    private int offSet = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rvItems);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        mSwipeListAdapter = new SwipeListAdapter(this, mGithubList);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mSwipeListAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                fetchGithub();
            }
        });
    }

    /**
     * This method is called when swipe refresh is pulled down
     */
    @Override
    public void onRefresh() {
        fetchGithub();
    }

    /**
     * Fetching github json by making http call using retrofit
     */
    private void fetchGithub() {
        // showing refresh animation before making http call
        mSwipeRefreshLayout.setRefreshing(true);

        // Create an instance of our GitHub API interface.
        GitTask service = GitClient.getClient().create(GitTask.class);

        // Create a call instance for looking up Retrofit contributors.
        Call<List<Github>> call = service.getCommits();

        call.enqueue(new Callback<List<Github>>() {
            @Override
            public void onResponse(Call<List<Github>> call, Response<List<Github>> response) {
                if (response.isSuccessful()) {
                    // tasks available

                    mGithubList.clear();

                    for (int i = 0; i < response.body().size(); i++) {
                        GitService gitservice = new GitService();

                        gitservice.setSome_person(response.body().get(i).getCommit().getAuthor().getName());
                        gitservice.setCommit_time(response.body().get(i).getCommit().getAuthor().getDate());
                        gitservice.setCommit_message(response.body().get(i).getCommit().getMessage());

                        mGithubList.add(gitservice);
                    }
                    mSwipeListAdapter.notifyDataSetChanged();

                } else {
                    // error response, no access to resource?
                    Toast.makeText(getApplicationContext(), response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
                // stopping swipe refresh
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Github>> call, Throwable throwable) {
                // something went completely south (like no internet connection)
                Log.d("Error", throwable.getMessage());
                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                // stopping swipe refresh
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

    }
}
