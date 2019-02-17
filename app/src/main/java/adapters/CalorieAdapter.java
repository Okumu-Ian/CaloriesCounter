package adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itha.caloriescounter.R;

import java.util.List;

import models.CalorieModel;

public class CalorieAdapter extends RecyclerView.Adapter<CalorieAdapter.CalorieView>{

    private Context mContext;
    private Activity mActivity;
    private List<CalorieModel> models;

    public CalorieAdapter(Activity mActivity, List<CalorieModel> models) {
        this.mContext = mActivity;
        this.mActivity = mActivity;
        this.models = models;
    }

    @NonNull
    @Override
    public CalorieView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_card,parent,false);
        CalorieView calorieView = new CalorieView(view);
        return calorieView;
    }

    @Override
    public void onBindViewHolder(@NonNull CalorieView holder, int position) {
        CalorieModel model = models.get(position);
        holder.foodName.setText("Food Name: "+model.getFoodName());
        holder.calorieIntake.setText("Calorie Amount: "+model.getCalorieAmount()+"kCal");
        holder.dateInput.setText("Date: "+model.getDate());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class CalorieView extends RecyclerView.ViewHolder
    {
        private TextView foodName;
        private TextView calorieIntake;
        private TextView dateInput;
        public CalorieView(View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.txt_list_name);
            calorieIntake = itemView.findViewById(R.id.txt_list_calories);
            dateInput = itemView.findViewById(R.id.txt_list_date);
        }
    }

}
