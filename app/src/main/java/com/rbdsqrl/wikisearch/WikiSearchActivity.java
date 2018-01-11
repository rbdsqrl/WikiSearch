package com.rbdsqrl.wikisearch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rbdsqrl.wikisearch.data.DataManager;
import com.rbdsqrl.wikisearch.data.model.SearchResult;
import com.rbdsqrl.wikisearch.di.component.ActivityComponent;
import com.rbdsqrl.wikisearch.di.component.DaggerActivityComponent;
import com.rbdsqrl.wikisearch.di.module.ActivityModule;
import com.rbdsqrl.wikisearch.searcher.GetResultResponse;
import com.rbdsqrl.wikisearch.searcher.GetSearchResultTask;
import com.rbdsqrl.wikisearch.util.Methods;

import java.util.List;

import javax.inject.Inject;

public class WikiSearchActivity extends AppCompatActivity implements GetResultResponse {
    private static String urlLink = "https://en.wikipedia.org/wiki/";
    private static final String TAG = "WIKISEARCH";
    SearchView svWikiSearch;
    GetSearchResultTask getSearchResultTask;
    ProgressBar pbLoading;
    ListView lvResults;
    @Inject
    DataManager mDataManager;

    private ActivityComponent activityComponent;

    private String url = "https://en.wikipedia.org/w/api.php?action=query&formatversion=2&generator=prefixsearch&gpssearch=SEARCHQUERY&gpslimit=10&prop=pageimages%7Cpageterms&piprop=thumbnail&pithumbsize=50&pilimit=10&redirects=&wbptterms=description&format=json";
    TextView tvNoResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki_search);
        getActivityComponent().inject(this);
        initializeViews();
    }

    private void initializeViews() {
        lvResults = findViewById(R.id.lvResults);
        tvNoResult = findViewById(R.id.tvNoResult);
        svWikiSearch = findViewById(R.id.svWikiSearch);
        pbLoading = findViewById(R.id.pbLoading);
        pbLoading.setVisibility(View.GONE);
        tvNoResult.setVisibility(View.GONE);
        svWikiSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SearchView) v).onActionViewExpanded();
            }
        });
        svWikiSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                doSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void doSearch(String query) {
        tvNoResult.setVisibility(View.GONE);
        if (new Methods().isNetworkAvailable(getBaseContext())) {
            pbLoading.setVisibility(View.VISIBLE);
            getSearchResultTask = new GetSearchResultTask();
            getSearchResultTask.response = this;
            getSearchResultTask.execute(query);
        } else {
            List<SearchResult> output = mDataManager.getResults(query);
            if (output != null) {
                Toast.makeText(getBaseContext(), "No Internet Connection. Showing cache data.", Toast.LENGTH_SHORT).show();

                setAdapter(output);
            }else
                Toast.makeText(getBaseContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(WikiSearchApplication.get(this).getComponent())
                    .build();
        }
        return activityComponent;
    }

    @Override
    public void processFinish(final List<SearchResult> output) {
        Log.i("processFinish", "called");
        pbLoading.setVisibility(View.GONE);
        setAdapter(output);
        //set up adapter
        try {
            mDataManager.createResults(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAdapter(final List<SearchResult> output) {
        if(output==null)
            tvNoResult.setVisibility(View.VISIBLE);
        ResultAdapter resultAdapter = new ResultAdapter(this, output);
        lvResults.setAdapter(resultAdapter);
        lvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String finalUrl = urlLink + output.get(position).getResultTitle().replace(" ", "_");
                openPage(finalUrl);
            }
        });
    }

    private void openPage(String finalUrl) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalUrl));
        startActivity(browserIntent);
    }
}
