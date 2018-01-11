package com.rbdsqrl.wikisearch.searcher;

import android.os.AsyncTask;
import android.util.Log;

import com.rbdsqrl.wikisearch.data.model.SearchResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Narendran on 11-01-2018.
 */

public class GetSearchResultTask extends AsyncTask<String, Void, Void> {
    List<SearchResult> searchResults = new ArrayList<>();
    public GetResultResponse response = null;
    private static final String TAG = "ASYNCTASK";
    private String url = "https://en.wikipedia.org/w/api.php?action=query&formatversion=2&generator=prefixsearch&gpssearch=SEARCHQUERY&gpslimit=10&prop=pageimages%7Cpageterms&piprop=thumbnail&pithumbsize=150&pilimit=10&redirects=&wbptterms=description&format=json";
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... arg0) {
        url = url.replace("SEARCHQUERY",arg0[0]).replace(" ","%20");
        HttpHandler handler = new HttpHandler();
        String responseString = handler.makeServiceCall(url);
        if (responseString != null) {
            try {
                JSONObject responseJsonObj = new JSONObject(responseString);
                JSONArray pages = responseJsonObj.getJSONObject("query").getJSONArray("pages");
                JSONObject selectedPage = null;
                SearchResult searchResult;
                for (int i = 0; i < pages.length(); i++) {
                    selectedPage = pages.getJSONObject(i);
                    String resultId = arg0[0].toLowerCase();
                    String resultTitle = selectedPage.getString("title");
                    String resultDescription = null;
                    try {
                        JSONObject terms = selectedPage.getJSONObject("terms");
                        if (terms != null) {
                            if (terms.getJSONArray("description") != null)
                                resultDescription = terms.getJSONArray("description").getString(0);
                        }
                    } catch (Exception e) {

                    }

                    String resultThumbnail = null;
                    try {
                        JSONObject thumbnail = selectedPage.getJSONObject("thumbnail");
                        if (thumbnail != null) {
                            resultThumbnail = thumbnail.getString("source");
                        }

                    } catch (Exception e) {

                    }
                    String resultUrl = selectedPage.getString("pageid");
                    searchResult = new SearchResult(resultId, resultTitle, resultDescription, resultThumbnail, resultUrl);
                    Log.e(TAG, "searchResult: " + searchResult.toString());
                    searchResults.add(searchResult);
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        response.processFinish(searchResults);
    }
}
