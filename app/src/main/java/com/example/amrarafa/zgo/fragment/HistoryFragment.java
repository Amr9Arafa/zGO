package com.example.amrarafa.zgo.fragment;


import android.app.Fragment;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.amrarafa.zgo.DBHelper;
import com.example.amrarafa.zgo.HistoryCursorAdapter;
import com.example.amrarafa.zgo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    private DBHelper dbHelper;
    ListView listView;

    HistoryCursorAdapter historyCursorAdapter;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dbHelper = new DBHelper(getActivity());
        View view =inflater.inflate(R.layout.fragment_history, container, false);

        Cursor cursor =getImages();
        historyCursorAdapter = new HistoryCursorAdapter(getActivity(),cursor,0);
        listView=(ListView) view.findViewById(R.id.listview);
        listView.setAdapter(historyCursorAdapter);

        return view;
    }

    @Nullable
    private Cursor getImages() {
        Cursor c;
        try {

            dbHelper.open();
            c= dbHelper.retreiveImagesFromDB();

            return c;
        }catch (Exception e){

            return null;
        }


    }


    public class QueryingTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }


}
