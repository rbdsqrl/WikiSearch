package com.rbdsqrl.wikisearch;

import android.app.Application;
import android.content.Context;

import com.rbdsqrl.wikisearch.data.DataManager;
import com.rbdsqrl.wikisearch.di.component.ApplicationComponent;
import com.rbdsqrl.wikisearch.di.component.DaggerApplicationComponent;
import com.rbdsqrl.wikisearch.di.module.ApplicationModule;

import javax.inject.Inject;

/**
 * Created by Narendran on 11-01-2018.
 */

public class WikiSearchApplication extends Application {
    protected ApplicationComponent applicationComponent;

    @Inject
    DataManager dataManager;
    public static WikiSearchApplication get(Context context) {
        return (WikiSearchApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        applicationComponent.inject(this);
    }

    public ApplicationComponent getComponent(){
        return applicationComponent;
    }
}
