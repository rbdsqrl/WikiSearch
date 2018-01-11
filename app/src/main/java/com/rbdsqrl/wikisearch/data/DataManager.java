package com.rbdsqrl.wikisearch.data;

import android.content.Context;
import android.content.res.Resources;

import com.rbdsqrl.wikisearch.data.model.SearchResult;
import com.rbdsqrl.wikisearch.di.ApplicationContext;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Narendran on 11-01-2018.
 */

public class DataManager {
    private Context mContext;
    private DbHelper mDbHelper;

    @Inject
    public DataManager(@ApplicationContext Context context,
                       DbHelper dbHelper) {
        mContext = context;
        mDbHelper = dbHelper;
    }

    public void createResults(List<SearchResult> searchResults) throws Exception {
        for(SearchResult searchResult:searchResults)
         mDbHelper.insertResult(searchResult);
    }

    public List<SearchResult> getResults(String resultId) throws Resources.NotFoundException, NullPointerException {
        List<SearchResult> results;
        try {
            results =  mDbHelper.getResults(resultId.toLowerCase());
        }catch (Exception e){
            e.printStackTrace();
           results = null;
        }
        return results;
    }

}
