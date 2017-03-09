package com.example.amrarafa.zgo;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

/**
 * Created by amr arafa on 3/9/2017.
 */
public class HistoryCursorAdapter extends CursorAdapter {
    public HistoryCursorAdapter(Context context, Cursor c,int flags) {
        super(context,c,flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        View view= LayoutInflater.from(context).inflate(R.layout.historyimage,viewGroup,false);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ImageView imageView=(ImageView)view;

            byte[] blob = cursor.getBlob(cursor.getColumnIndex("image"));
            Bitmap bitmap= Utility.getImage(blob);
            imageView.setImageBitmap(bitmap);

    }
}
