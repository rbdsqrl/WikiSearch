package com.rbdsqrl.wikisearch.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rbdsqrl.wikisearch.data.model.SearchResult;
import com.rbdsqrl.wikisearch.di.ApplicationContext;
import com.rbdsqrl.wikisearch.di.DatabaseInfo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Narendran on 11-01-2018.
 */

public class DbHelper extends SQLiteOpenHelper {
    public static final String WIKI_TABLE_NAME = "wikiResults";
    public static final String WIKI_COLUMN_WIKI_ID = "id";
    public static final String WIKI_COLUMN_RESULTID = "resultId";
    public static final String WIKI_COLUMN_TITLE = "resultTitle";
    public static final String WIKI_COLUMN_DESC = "resultDescription";
    public static final String WIKI_COLUMN_THUMBNAIL = "resultThumbnail";
    public static final String WIKI_COLUMN_URL = "resultUrl";

    @Inject
    public DbHelper(@ApplicationContext Context context,
                    @DatabaseInfo String dbName,
                    @DatabaseInfo Integer version) {
        super(context, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        tableCreateStatements(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WIKI_TABLE_NAME);
        onCreate(db);
    }

    private void tableCreateStatements(SQLiteDatabase db) {
        try {
            db.execSQL(
                    "CREATE TABLE IF NOT EXISTS "
                            + WIKI_TABLE_NAME + "("
                            + WIKI_COLUMN_WIKI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + WIKI_COLUMN_RESULTID + " TEXT, "
                            + WIKI_COLUMN_TITLE + " TEXT, "
                            + WIKI_COLUMN_DESC + " TEXT, "
                            + WIKI_COLUMN_THUMBNAIL + " TEXT, "
                            + WIKI_COLUMN_URL + " TEXT "
                            + ")"
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   protected List<SearchResult> getResults(String resultId) throws Exception {
        Cursor cursor = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String selectQuery = "SELECT * FROM " + WIKI_TABLE_NAME
                    + " WHERE "
                    + WIKI_COLUMN_RESULTID
                    + " LIKE \'"+ resultId +"\'";
            cursor = db.rawQuery(selectQuery,null);

            if (cursor.getCount() > 0) {
                List<SearchResult> searchResults = new ArrayList<>();
                if(cursor.moveToFirst()){
                    do {
                        SearchResult searchResult  = new SearchResult();
                        searchResult.setId(cursor.getString(0));
                        searchResult.setResultId(cursor.getString(1));
                        searchResult.setResultTitle(cursor.getString(2));
                        searchResult.setResultDescription(cursor.getString(3));
                        searchResult.setResultThumbnail(cursor.getString(4));
                        searchResult.setResultUrl(cursor.getString(5));
                        //add to list
                        searchResults.add(searchResult);
                    } while (cursor.moveToNext());
                }else{
                    throw new Resources.NotFoundException("ResultId " + resultId + " does not exists");
                }
                return searchResults;
            } else {
                throw new Resources.NotFoundException("ResultId " + resultId + " does not exists");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    protected void insertResult(SearchResult result) throws Exception {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(WIKI_COLUMN_RESULTID, result.getResultId());
            contentValues.put(WIKI_COLUMN_DESC, result.getResultDescription());
            contentValues.put(WIKI_COLUMN_THUMBNAIL, result.getResultThumbnail());
            contentValues.put(WIKI_COLUMN_URL, result.getResultUrl());
            contentValues.put(WIKI_COLUMN_TITLE, result.getResultTitle());
            db.insert(WIKI_TABLE_NAME, null, contentValues);
            Log.i("added",result.getResultTitle()+", " + result.getResultId());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
