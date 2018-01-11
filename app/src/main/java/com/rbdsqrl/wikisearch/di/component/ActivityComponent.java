package com.rbdsqrl.wikisearch.di.component;

import com.rbdsqrl.wikisearch.WikiSearchActivity;
import com.rbdsqrl.wikisearch.di.PerActivity;
import com.rbdsqrl.wikisearch.di.module.ActivityModule;

import dagger.Component;

/**
 * Created by Narendran on 11-01-2018.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(WikiSearchActivity wikiSearchActivity);

}
