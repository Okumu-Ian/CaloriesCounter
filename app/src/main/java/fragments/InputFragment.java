package fragments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.itha.caloriescounter.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class InputFragment extends Fragment {

    private View view;
    private EditText foodName,calorieIntake,type;
    private AppCompatButton btn;
    int id = 1;
    public static final String DATABASE_NAME = "mycalorie_counter";
    SQLiteDatabase database;
    public InputFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.input_fragment,container,false);
        initUI();
        return view;
    }

    private void initUI()
    {
        //all root methods require Activity level access
        getActivity();
        database = Objects.requireNonNull(getActivity()).openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE,null);
        createEmployeeTable();
        foodName = view.findViewById(R.id.edt_food_name);
        calorieIntake = view.findViewById(R.id.edt_calorie_amount);
        type = view.findViewById(R.id.edt_food_type);
        btn = view.findViewById(R.id.btn_submit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmEmptyFields();
            }
        });
    }

    //method to add a single entry
    private void addCounter(String name,String typed, String count)
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String currentTimeStamp = sdf.format(calendar.getTime());
        String insertQuery = "INSERT into calories_table(food_name,calorie_count,food_type,date_added)VALUES(?,?,?,?)";
                //"(id"+","+name+","+Integer.parseInt(count)+","+typed+","+currentTimeStamp+");";
        String[] params = {name,count,typed,currentTimeStamp};
        database.execSQL(insertQuery,params);
        Toast.makeText(getActivity(), "Added Successfully", Toast.LENGTH_SHORT).show();
        foodName.getText().clear();
        calorieIntake.getText().clear();
        type.getText().clear();
    }

    //method for creating table. Should be called only once. But query will not allow for multiple table creation
    private void createEmployeeTable()
    {
        String sqlQuery = "CREATE TABLE IF NOT EXISTS calories_table(\n"+
                "id INTEGER NOT NULL  PRIMARY KEY AUTOINCREMENT,\n"+
                "food_name TEXT NOT NULL,\n"+
                "calorie_count INTEGER not null,\n"+
                "food_type VARCHAR(30) NOT NULL,\n"+
                "date_added datetime NOT NULL);";
        database.execSQL(sqlQuery);

    }

    //checking for empty fields
    private void confirmEmptyFields()
    {
        if (foodName.getText().toString().length()==0 || calorieIntake.getText().toString().length()==0 || type.getText().toString().length()==0)
        {
            //show error message if any view is empty
            Snackbar.make(view,"Kindly Fill all fields",Snackbar.LENGTH_SHORT).show();
        }else
            {
                String food_name = foodName.getText().toString();
                String calorie_intake = calorieIntake.getText().toString();
                String food_type = type.getText().toString();
                addCounter(food_name,food_type,calorie_intake);
            }
    }
}
