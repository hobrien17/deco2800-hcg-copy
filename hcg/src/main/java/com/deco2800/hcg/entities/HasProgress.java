package com.deco2800.hcg.entities;

/**
 * Created by timhadwen on 29/7/17.
 */
public interface HasProgress {

    /**
     * Returns the current progress out of 100
     */
    int getProgress();

    /**
     * Should i show the progress
     */
    boolean showProgress();
}
