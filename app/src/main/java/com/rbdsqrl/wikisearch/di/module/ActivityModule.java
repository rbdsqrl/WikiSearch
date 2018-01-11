package com.rbdsqrl.wikisearch.di.module;

import android.app.Activity;
import android.content.Context;

import com.rbdsqrl.wikisearch.di.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Narendran on 11-01-2018.
 */
@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }
}
