package com.jaspreet.lab2;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class SubmitDialog extends DialogFragment {
    SubmitDialogListener sdl;

    public void onPositiveClick(DialogInterface dialog){
        sdl.onPositiveClick(dialog);
    }

    public void onNegativeClick(DialogInterface dialog){
        sdl.onNegativeClick(dialog);
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Submitted")
                .setMessage("Do you want to Submit the order")
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onPositiveClick(dialogInterface);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onNegativeClick(dialogInterface);
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        sdl = (SubmitDialogListener) getActivity();
    }
}