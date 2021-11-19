package com.example.mvp;

public class MyUtil {

    public static String concatenar(String... palabras) {
        StringBuilder sb = new StringBuilder(palabras.length);
        for (String palabra : palabras) {
            sb.append(palabra);
        }
        return sb.toString();
    }

    public static String concatenarConEspacios(String... palabras) {
        StringBuilder sb = new StringBuilder(palabras.length);
        for (String palabra : palabras) {
            sb.append(palabra).append(" ");
        }
        return sb.toString();
    }


    public static String primeraLetraToUpperCase(String str) {
        StringBuilder s = new StringBuilder();


        char ch = ' ';
        for (int i = 0; i < str.length(); i++) {


            if (ch == ' ' && str.charAt(i) != ' ')
                s.append(Character.toUpperCase(str.charAt(i)));
            else
                s.append(str.charAt(i));
                ch = str.charAt(i);

        }

        return s.toString().trim();

    }

}
