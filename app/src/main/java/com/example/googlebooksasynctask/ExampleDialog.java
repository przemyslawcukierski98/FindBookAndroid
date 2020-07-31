package com.example.googlebooksasynctask;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ExampleDialog extends AppCompatDialogFragment {

    private String tytulKsiazki;
    private String autorKsiazki;
    private String dataZamowienia;
    private String imieNazwisko;
    private String urlOrder;

    ExampleDialog(String tytulKsiazki, String autorKsiazki, String dataZamowienia, String imieNazwisko){
        this.tytulKsiazki = tytulKsiazki;
        this.autorKsiazki = autorKsiazki;
        this.dataZamowienia = dataZamowienia;
        this.imieNazwisko = imieNazwisko;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Potwierdź")
                .setMessage("Jesteś pewien, że zamawiasz?")
                .setNegativeButton("TAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendOrder();
                    }
                })
                .setPositiveButton("NIE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        return builder.create();
    }

    private void sendOrder(){
        urlOrder = "http://vlab.imei.uz.zgora.pl/mybooks/api/orders?title=" + tytulKsiazki + "&author=" +
                autorKsiazki + "autor&date=" + dataZamowienia + "&username=" + imieNazwisko;
        Intent browse = new Intent( Intent.ACTION_VIEW, Uri.parse(urlOrder));
        startActivity(browse);
    }
}
