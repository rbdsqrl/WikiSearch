package com.rbdsqrl.wikisearch.data.model;

import android.graphics.Bitmap;

/**
 * Created by Narendran on 11-01-2018.
 */

public class SearchResult {
    private String id;
    private String resultId;
    private String resultTitle;
    private String resultDescription;
    private String resultThumbnail;
    private String resultUrl;
    private Bitmap bitmap;

    public SearchResult() {
    }

    public SearchResult(String resultId, String resultTitle, String resultDescription, String resultThumbnail, String resultUrl) {
        this.resultId = resultId;
        this.resultTitle = resultTitle;
        this.resultDescription = resultDescription;
        this.resultThumbnail = resultThumbnail;
        this.resultUrl = resultUrl;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public String getResultTitle() {
        return resultTitle;
    }

    public void setResultTitle(String resultTitle) {
        this.resultTitle = resultTitle;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }

    public String getResultThumbnail() {
        return resultThumbnail;
    }

    public void setResultThumbnail(String resultThumbnail) {
        this.resultThumbnail = resultThumbnail;
    }

    public String getResultUrl() {
        return resultUrl;
    }

    public void setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "resultId='" + resultId + '\'' +
                ", resultTitle='" + resultTitle + '\'' +
                ", resultDescription='" + resultDescription + '\'' +
                ", resultThumbnail='" + resultThumbnail + '\'' +
                ", resultUrl='" + resultUrl + '\'' +
                '}';
    }
}
