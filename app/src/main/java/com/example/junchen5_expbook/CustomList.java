package com.example.junchen5_expbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomList extends ArrayAdapter<ExpenseInfo> {

    private ArrayList<ExpenseInfo> expenses;

    private Context context;

    public CustomList(Context context, ArrayList<ExpenseInfo> expenses){
        super(context, 0, expenses);
        this.context = context;
        this.expenses = expenses;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null){
            // If the recycled view is null, inflate a new view from the specified layout.
            view = LayoutInflater.from(context).inflate(R.layout.content, parent, false);
        }

        ExpenseInfo expenseInfo = expenses.get(position);

        // Find TextView using their respective id
        TextView personName = view.findViewById(R.id.person_name);
        TextView monthStarted = view.findViewById(R.id.month_start);
        TextView monthlyCharged = view.findViewById(R.id.monthly_charged);
        TextView comment = view.findViewById(R.id.comment);

        // Set the text of each TextView to display information from the ExpenseInfo object.
        personName.setText("Name: " + expenseInfo.getName());
        monthStarted.setText("MonthStart: " + expenseInfo.getMonthStarted());
        monthlyCharged.setText("MonthlyCharged: " + String.valueOf(expenseInfo.getMonthlyCharged()));
        comment.setText("Comment: " + expenseInfo.getComment());

        // Return the custom view
        return view;
    }
}
