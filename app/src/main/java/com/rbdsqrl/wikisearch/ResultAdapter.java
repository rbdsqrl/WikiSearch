package com.rbdsqrl.wikisearch;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rbdsqrl.wikisearch.data.DataManager;
import com.rbdsqrl.wikisearch.data.model.SearchResult;

import java.net.URL;
import java.util.List;

/**
 * Created by Narendran on 11-01-2018.
 */

public class ResultAdapter extends BaseAdapter {
    public static LayoutInflater layoutInflater = null;
    Activity activity;
    List<SearchResult> searchResults;
    List<Bitmap> bitmaps;
    DataManager dataManager;

    public ResultAdapter(Activity activity, List<SearchResult> searchResults) {
        this.activity = activity;
        this.searchResults = searchResults;
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.dataManager = dataManager;
    }

    @Override
    public int getCount() {
        return searchResults.size();
    }

    @Override
    public Object getItem(int position) {
        return searchResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View customView = convertView;
        InfoHolder infoHolder = null;
        if (customView == null) {
            customView = layoutInflater.inflate(R.layout.template_results, null);
            infoHolder = new InfoHolder();
            infoHolder.tvDescription = customView.findViewById(R.id.tvDescription);
            infoHolder.ivThumbnail = customView.findViewById(R.id.ivThumbnail);
            infoHolder.tvTitle = customView.findViewById(R.id.tvTitle);

            customView.setTag(infoHolder);
        } else {
            infoHolder = (InfoHolder) customView.getTag();
        }
        infoHolder.ivThumbnail.setImageResource(R.drawable.ic_none);
        infoHolder.position = position;
        infoHolder.tvDescription.setText(searchResults.get(position).getResultDescription());
        infoHolder.tvTitle.setText(searchResults.get(position).getResultTitle());
        if(searchResults.get(position).getResultThumbnail()!=null) {
            if (searchResults.get(position).getBitmap() != null) {
                infoHolder.ivThumbnail.setImageBitmap(searchResults.get(position).getBitmap());
            } else {
                downloadPhoto(infoHolder, position);
            }
        }else{
            infoHolder.ivThumbnail.setImageResource(R.drawable.ic_none);
        }
        return customView;
    }

    static class InfoHolder {
        TextView tvTitle;
        TextView tvDescription;
        ImageView ivThumbnail;
        int position;
    }

    public void downloadPhoto(final InfoHolder infoHolder, final int position) {
        class SetPhotoTask extends AsyncTask<Void, Void, Void> {
            boolean checker = true;
            Bitmap bitmap;
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    URL imageURL = new URL(searchResults.get(position).getResultThumbnail());
                    bitmap = BitmapFactory.decodeStream(imageURL.openStream());
                } catch (Exception e) {
                    e.printStackTrace();
                    checker = false;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (checker) {
                    infoHolder.ivThumbnail.setImageBitmap(searchResults.get(position).getBitmap());
                    searchResults.get(position).setBitmap(bitmap);
                    notifyDataSetChanged();
                } else {
                    infoHolder.ivThumbnail.setImageResource(R.drawable.ic_none);
                }
            }
        }
        new SetPhotoTask().execute();
    }
}
