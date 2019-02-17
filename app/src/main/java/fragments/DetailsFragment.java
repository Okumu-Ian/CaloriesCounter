package fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itha.caloriescounter.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

import models.CalorieModel;
import models.UserModel;

public class DetailsFragment extends Fragment {
    public DetailsFragment() {
    }

    private TextView name,age,weight,height,bmi,bmr,tweight,theight;
    private GraphView graphView;
    private View view;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
    private SQLiteDatabase database;
    private List<CalorieModel> calorieModelList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.details_fragment,container,false);
        initUI();
        return view;
    }

    private void initUI()
    {
        graphView = view.findViewById(R.id.graphView);
        name = view.findViewById(R.id.textView2);
        age = view.findViewById(R.id.textView3);
        weight = view.findViewById(R.id.textView4);
        height = view.findViewById(R.id.textView5);
        bmi = view.findViewById(R.id.textView6);
        bmr = view.findViewById(R.id.textView7);
        tweight = view.findViewById(R.id.textView8);
        theight = view.findViewById(R.id.textView9);
        setTexts();
        /*if (checkDataBase())
        {*/
            loadData();


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

    private List<CalorieModel> fetchGraphData()
    {
        calorieModelList = new ArrayList<>();
        database = getActivity().openOrCreateDatabase(InputFragment.DATABASE_NAME,Context.MODE_PRIVATE,null);
        Cursor cursor = database.rawQuery("SELECT * from calories_table",null);
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
        return calorieModelList;
    }



    private void setTexts()
    {
        SharedPreferences preferences = getActivity().getSharedPreferences("FIREBASE_KEY", Context.MODE_PRIVATE);
        String uid = preferences.getString("UserId","");
        reference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel model = dataSnapshot.getValue(UserModel.class);
                name.setText("Name: "+model.getName());
                age.setText("Age: "+model.getAge()+" Years");
                weight.setText("Weight: "+model.getWeight()+" KGs");
                height.setText("Height: "+model.getHeight()+" CM");
                String BMI;
                double weight = Double.parseDouble(model.getWeight());
                double height = Double.parseDouble(model.getHeight());
                double heightInM = height/100;
                double dBMI = weight/(heightInM*heightInM);
                BMI = String.valueOf(dBMI);
                bmi.setText("BMI: "+BMI);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        SharedPreferences preferences1 = getActivity().getSharedPreferences("MY_TARGETS",getActivity().MODE_PRIVATE);
        String targetWeight = preferences1.getString("WEIGHT_TARGET","Kindly Set");
        String targetHeight = preferences1.getString("HEIGHT_TARGET","Kindly Set");
        theight.setText("Target Height: "+targetHeight);
        tweight.setText("Target Weight: "+targetWeight);

    }

    private void loadData()
    {
        List<CalorieModel> models = fetchGraphData();
        DataPoint[] mypoints = new DataPoint[models.size()];
        DataPoint singlePoint;
        for (int c = 0; c<models.size();c++)
        {
            CalorieModel model = models.get(c);
            singlePoint = new DataPoint(c,Integer.parseInt(model.getCalorieAmount()));
            mypoints[c] = singlePoint;
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(mypoints);
        graphView.addSeries(series);
    }
}
