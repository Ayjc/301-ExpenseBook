package com.example.junchen5_expbook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DeleteExpenseFragment extends DialogFragment {

    private DeleteOnFragmentListener listener;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof DeleteOnFragmentListener) {
            listener = (DeleteOnFragmentListener) context;
        }
        else {
            throw new RuntimeException(context.toString() + "DeleteOnFragmentListener is not implemented");
        }
    }


    public interface DeleteOnFragmentListener{
        void deleteonOkPressed();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Inflate the view from the specified layout resource.
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.delete_fragment_layout, null);

        // Create an AlertDialog builder.
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle("Delete Expense")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Call the listener's deleteonOkPressed method when the OK button is clicked.
                        listener.deleteonOkPressed();
                    }
                }).create();
    }
}
