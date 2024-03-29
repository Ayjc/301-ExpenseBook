package com.example.junchen5_expbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddExpenseFragment.AddOnFragmentListener, ModifyExpenseFragment.ModifyOnFragmentListener,
DeleteExpenseFragment.DeleteOnFragmentListener{

    private ArrayList<ExpenseInfo> dataList;

    private ListView expenseList;

    private ArrayAdapter<ExpenseInfo> expenseAdapter;

    private int expensePosition = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the data list for the expense information
        dataList = new ArrayList<ExpenseInfo>();

        // Getting the ListView of the layout
        expenseList = findViewById(R.id.expense_list);

        // Create and set the custom adapter for the LIstView
        expenseAdapter = new CustomList(this, dataList);

        // Setting the adapter
        expenseList.setAdapter(expenseAdapter);

        // Set an item click listener for the ListView
        expenseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                expensePosition = position;
            }
        });

        // Find and set click listeners for the buttons
        final FloatingActionButton addButton = findViewById(R.id.add_expense_button);
        addButton.setOnClickListener(v -> {
            // Show the "Add Expense" dialog when the "Add" button is clicked
            new AddExpenseFragment().show(getSupportFragmentManager(), "ADD_EXPENSE");
        });

        final FloatingActionButton editButton = findViewById(R.id.edit_expense_button);
        editButton.setOnClickListener(v -> {
            if (expensePosition == -1){
                // Show a Toast message if no expense is selected
                Toast.makeText(this, "Please select an expense first then using the edit button", Toast.LENGTH_SHORT).show();
                return;
            }
            // Get the selected expense and create a bundle with its data
            ExpenseInfo selectedExpense = dataList.get(expensePosition);
            Bundle bundle = new Bundle();
            bundle.putString("name", selectedExpense.getName());
            bundle.putString("monthStart", selectedExpense.getMonthStarted());
            bundle.putDouble("monthlyCharged", selectedExpense.getMonthlyCharged());
            bundle.putString("comments", selectedExpense.getComment());
            // Show the "Edit Expense" dialog with the selected expense's data
            ModifyExpenseFragment editionDialog = new ModifyExpenseFragment();
            editionDialog.setArguments(bundle);
            editionDialog.show(getSupportFragmentManager(), "EDIT_EXPENSE");
        });

        final FloatingActionButton deleteButton = findViewById(R.id.delete_expense_button);
        deleteButton.setOnClickListener(v ->{
            if (expensePosition == -1){
                // Show a Toast message if no expense is selected
                Toast.makeText(this, "Please select an expense first then using the delete button", Toast.LENGTH_SHORT).show();
                return;
            }
            new DeleteExpenseFragment().show(getSupportFragmentManager(), "DELETE_EXPENSE");
        });

    }

    // Call the method when a new expense is added
    @Override
    public void addonOKPressed(ExpenseInfo expenseInfo) {
        dataList.add(expenseInfo);
        expenseAdapter.notifyDataSetChanged();
        // Update total monthly charge here
        updateTotalMonthlyCharge();
    }

    // Call the method when an existing expense is being modified
    @Override
    public void modifyonOKPressed(ExpenseInfo expenseInfo) {
        if (expensePosition != -1) {
            ExpenseInfo newExpense = dataList.get(expensePosition);
            newExpense.setName(expenseInfo.getName());
            newExpense.setMonthStarted(expenseInfo.getMonthStarted());
            newExpense.setMonthlyCharged(expenseInfo.getMonthlyCharged());
            newExpense.setComment(expenseInfo.getComment());
            expenseAdapter.notifyDataSetChanged();
            expensePosition = -1;
            // Update total monthly charge here
            updateTotalMonthlyCharge();
        }
    }

    // Call the method when deleting an expense
    @Override
    public void deleteonOkPressed() {
        ExpenseInfo expense = dataList.get(expensePosition);
        dataList.remove(expense);
        expensePosition = -1;
        expenseAdapter.notifyDataSetChanged();
        // Update total monthly charge here
        updateTotalMonthlyCharge();
    }

    // Update the total monthly charge display
    private void updateTotalMonthlyCharge() {
        double totalMonthlyCharge = 0.0;
        for (ExpenseInfo expense : dataList) {
            totalMonthlyCharge += expense.getMonthlyCharged();
        }
        // Update the TextView displaying the total charge
        TextView totalTextView = findViewById(R.id.summary_text);
        totalTextView.setText("Total Monthly Charge:\n" + "$" + totalMonthlyCharge);
    }
}