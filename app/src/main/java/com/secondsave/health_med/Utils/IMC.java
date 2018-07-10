package com.secondsave.health_med.Utils;

import java.text.DecimalFormat;

public class IMC {
    public static float Calculate(float weight, float height) {

        return round(weight / (height * height));
    }

    public static String Clasification(float imc) {
        String cad;
        if (imc < 16.00) {
            cad = "Infrapeso: Delgadez Severa";
        } else if (imc <= 16.00 || imc <= 16.99) {
            cad = "Infrapeso: Delgadez moderada";
        } else if (imc <= 17.00 || imc <= 18.49) {
            cad = "Infrapeso: Delgadez aceptable";
        } else if (imc <= 18.50 || imc <= 24.99) {
            cad = "Peso Normal";
        } else if (imc <= 25.00 || imc <= 29.99) {
            cad = "Sobrepeso";
        } else if (imc <= 30.00 || imc <= 34.99) {
            cad = "Obeso: Tipo I";
        } else if (imc <= 35.00 || imc == 40.00) {
            cad = "Obeso: Tipo III";
        } else {
            cad = "No existe clasificacion";
        }
        return cad;
    }

    public static float round(float d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Float.valueOf(twoDForm.format(d));
    }
}
