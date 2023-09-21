package com.valesoft.exploradordefarmacias;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

public class Dialogo {
    public static void mostrarDialogo(Activity activity){
        new AlertDialog.Builder(activity)
                .setTitle("Cerrar Sesión")
                .setMessage("¿Desea cerrar la aplicación?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(activity, "Continue trabajando...", Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }
}
