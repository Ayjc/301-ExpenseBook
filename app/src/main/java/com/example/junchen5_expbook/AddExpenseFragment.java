package com.example.junchen5_expbook;

import java.util.Calendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddExpenseFragment extends DialogFragment {
    private EditText personName;

    private EditText monthStarted;

    private EditText monthlyCharged;

    private EditText comment;

    private AddOnFragmentListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddExpenseFragment.AddOnFragmentListener) {
            listener = (AddExpenseFragment.AddOnFragmentListener) context;
        }
        else {
            throw new RuntimeException(context.toString() + "AddOnFragmentListener is not implemented");
        }
    }


    public interface AddOnFragmentListener {
        void addonOKPressed(ExpenseInfo expenseInfo);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Inflate the view from the specified layout resource.
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_fragment_layout, null);

        // Finding the EditText by id
        EditText personName = view.findViewById(R.id.person_name_add_text);
        EditText monthStarted = view.findViewById(R.id.month_start_add_text);
        EditText monthlyCharged = view.findViewById(R.id.monthly_charge_add_text);
        EditText comment = view.findViewById(R.id.comment_add_text);

        // Create an AlertDialog builder.
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle("Add Expense")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Get the text from EditText views.
                        String name = personName.getText().toString();
                        String monthStart = monthStarted.getText().toString();
                        String textCharge = monthlyCharged.getText().toString();
                        String comments = comment.getText().toString();

                        // Getting the current time
                        Calendar calendar = Calendar.getInstance();
                        int currentYear = calendar.get(Calendar.YEAR);
                        int currentMonth = calendar.get(Calendar.MONTH) + 1;
                        // Getting the input of the monthStarted
                        String[] monthYear = monthStart.split("-");



                        // Check for empty fields and show a Toast message if any are empty.
                        if (name.isEmpty()) {
                            Toast.makeText(getContext(), "Name cannot be empty. Please try again", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (monthStart.isEmpty()) {
                            Toast.makeText(getContext(), "Month Start cannot be empty. Please try again", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        // Check if the month started input format correctly as yyyy-mm
                        if (!monthStart.matches("\\d{4}-\\d{2}")){
                            Toast.makeText(getContext(), "Month Start must be format as yyyy-mm. Please try again", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        int year = Integer.parseInt(monthYear[0]);
                        int month = Integer.parseInt(monthYear[1]);

                        // Check if the month input for Month Started is valid or not
                        if (month < 1 || month > 12) {
                            Toast.makeText(getContext(), "Month Start must be between 1 and 12. Please try again", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Checking if the input of the monthStarted is in the future time
                        if (year > currentYear || (year == currentYear && month > currentMonth)) {
                            Toast.makeText(getContext(), "Month Start cannot be in the future. Please try again", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (textCharge.isEmpty()) {
                            Toast.makeText(getContext(), "Monthly Charged cannot be empty. Pleas try again", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        // Parse the monthly charge as a Double and pass it to the listener.
                        Double charge = Double.parseDouble(textCharge);
                        listener.addonOKPressed(new ExpenseInfo(name, monthStart, charge, comments));
                    }
                }).create();

    }
}
