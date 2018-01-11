package com.rbdsqrl.wikisearch.di.module;

import android.app.Application;
import android.content.Context;

import com.rbdsqrl.wikisearch.di.ApplicationContext;
import com.rbdsqrl.wikisearch.di.DatabaseInfo;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Narendran on 11-01-2018.
 */

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application app) {
        mApplication = app;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return "wikiSearchResultsDB";
    }

    @Provides
    @DatabaseInfo
    Integer provideDatabaseVersion() {
        return 1;
        //return 2;
    }
}
