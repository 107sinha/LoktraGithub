package com.example.deepanshu.loktra.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by deepanshu on 14/7/16.
 */
public class Github {

    @SerializedName("commit")
    @Expose
    private Commit commit;

    /**
     *
     * @return
     * The commit
     */
    public Commit getCommit() {
        return commit;
    }

    /**
     *
     * @param commit
     * The commit
     */
    public void setCommit(Commit commit) {
        this.commit = commit;
    }

}
