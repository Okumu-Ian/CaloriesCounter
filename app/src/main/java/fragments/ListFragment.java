package fragments;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.itha.caloriescounter.R;

import java.util.ArrayList;
import java.util.List;

import adapters.CalorieAdapter;
import models.CalorieModel;

public class ListFragment extends Fragment {
    public ListFragment() {
    }

    View view;
    private Activity activity = getActivity();
    private List<CalorieModel> calorieModelList;
    private RecyclerView recyclerView;
    private SQLiteDatabase sqLiteDatabase;
    private CalorieAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.list_fragment,container,false);
        initUI();
        return view;
    }

    private void initUI()
    {
        sqLiteDatabase = getContext().openOrCreateDatabase(InputFragment.DATABASE_NAME, Context.MODE_PRIVATE,null);
        calorieModelList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.calories_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        loadData();
        recyclerView.setAdapter(adapter);
    }

    public boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(InputFragment.DATABASE_NAME, null,
                    SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            // database doesn't exist yet.
        }
        return checkDB != null;
    }

    private void loadData()
    {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM calories_table",null);
        if (cursor.moveToFirst())
        {
            do
                {
                    calorieModelList.add(new CalorieModel
                                   (String.valueOf(cursor.getInt(0)),
                                    cursor.getString(1),
                                    cursor.getString(2),
                                    cursor.getString(4)));
                }while (cursor.moveToNext());
        }
        cursor.close();
        adapter = new CalorieAdapter(getActivity(),calorieModelList);
    }
}
