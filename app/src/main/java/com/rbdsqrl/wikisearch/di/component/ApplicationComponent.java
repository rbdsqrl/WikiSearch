package com.rbdsqrl.wikisearch.di.component;

import android.app.Application;
import android.content.Context;

import com.rbdsqrl.wikisearch.WikiSearchApplication;
import com.rbdsqrl.wikisearch.data.DataManager;
import com.rbdsqrl.wikisearch.data.DbHelper;
import com.rbdsqrl.wikisearch.di.ApplicationContext;
import com.rbdsqrl.wikisearch.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Narendran on 11-01-2018.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(WikiSearchApplication wikiSearchApplication);

    @ApplicationContext
    Context getContext();

    Application getApplication();

    DataManager getDataManager();

    DbHelper getDbHelper();
}
