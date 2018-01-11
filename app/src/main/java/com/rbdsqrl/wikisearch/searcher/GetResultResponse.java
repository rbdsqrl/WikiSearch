package com.rbdsqrl.wikisearch.searcher;

import com.rbdsqrl.wikisearch.data.model.SearchResult;

import java.util.List;

/**
 * Created by Narendran on 11-01-2018.
 */

public interface GetResultResponse {
    void processFinish(List<SearchResult> output);
}
