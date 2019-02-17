package fragments;

import android.content.SharedPreferences;
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

import com.itha.caloriescounter.R;

public class TargetsFragment extends Fragment {
    public TargetsFragment() {
    }

    private EditText weight_target;
    private EditText height_target;
    private AppCompatButton submit_target;
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.targets_fragment,container,false);
        initUI();
        return view;
    }

    private void initUI()
    {
        weight_target = view.findViewById(R.id.edt_weight_kg);
        height_target = view.findViewById(R.id.edt_height_cm);
        submit_target = view.findViewById(R.id.btn_submit_targets);
        submit_target.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkFields();
            }
        });

    }

    private void checkFields(){
        if (weight_target.getText().toString().length()==0 || height_target.getText().toString().length()==0){
            Snackbar.make(view,"Kindly fill in all the details",Snackbar.LENGTH_SHORT).show();
        }else {
        String weight = weight_target.getText().toString();
        String height = height_target.getText().toString();
        //save values in shared preference

            SharedPreferences.Editor  editor = getActivity().getSharedPreferences("MY_TARGETS",getActivity().MODE_PRIVATE).edit();
            editor.putString("WEIGHT_TARGET",weight);
            editor.putString("HEIGHT_TARGET",height);
            editor.apply();
            weight_target.getText().clear();
            height_target.getText().clear();
        }
    }
}
