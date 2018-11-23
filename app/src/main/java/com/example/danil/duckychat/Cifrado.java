package com.example.danil.duckychat;

import java.util.ArrayList;

public class Cifrado {
    private String[][] ola;
    public String cadenaCifrada ="";
    int Nivel;
    int TamanoCadena;


    public Cifrado()
    {}

    public Cifrado(String cadena,int nivel)
    {
        this.Nivel = nivel;
        int columnas = cadena.length();
        this.TamanoCadena = columnas;
        ola = new String [nivel][columnas];
        int k =0;
        boolean arribaAbajo =true;
        for (int i=0; i<nivel;)
        {
            for (int j=0;j<columnas;j++)
            {
                if (j!=(columnas))
                {
                    if (arribaAbajo==true)
                    {
                        ola[i][j] = String.valueOf(cadena.charAt(k));
                        k++;
                        i++;
                    }
                    if (arribaAbajo==false)
                    {
                        ola[i][j] = String.valueOf(cadena.charAt(k));
                        k++;
                        i--;
                    }
                    if (i==(nivel-1)&&arribaAbajo==true)
                    {
                        j++;
                        ola[i][j] = String.valueOf(cadena.charAt(k));
                        i--;

                        arribaAbajo = false;
                        k++;

                    }
                    if (i==0&&arribaAbajo==false)
                    {
                        if (k!=columnas) {
                            ola[i][j] = String.valueOf(cadena.charAt(k));
                            i++;
                            k++;
                            j++;
                            arribaAbajo = true;
                        }
                        else
                        {
                            i=nivel;
                        }

                    }
                }
                else
                {
                    i=nivel;
                }

            }
        }
        int numero = 0;
    }

    public String getCadenaCifrada()
    {
        for (int i =0;i<Nivel;i++)
        {
            for (int j=0;j<TamanoCadena;j++)
            {
                if (ola[i][j]!=null)
                    cadenaCifrada = cadenaCifrada+ ola[i][j];
            }
        }
        return cadenaCifrada;
    }


    public String Descifrar(String textoCifrado, int nivel){
        int TamañoOla = (nivel * 2) - 2;
        int NumOlas = (textoCifrado.length()/TamañoOla);
        NumOlas = NumOlas==0?1:NumOlas;
        int TamañoBloque = NumOlas*2;
        String texto = "";
        String Valles = "";
        String Crestas = "";
        ArrayList<String> Bloques = new ArrayList<>();

        //CRESTAS Y VALLES
        for (int i = 0; i < NumOlas; i++) {
            Crestas += textoCifrado.charAt(i);
        }
        for (int i = textoCifrado.length()-1; i > (textoCifrado.length()-1-NumOlas); i--) {
            Valles = textoCifrado.charAt(i) + Valles;
        }

        //LLENAR BLOQUES
        int contadorBloque = 0;
        for (int i = NumOlas; i < textoCifrado.length()-NumOlas; i++) {
            contadorBloque++;
            texto += textoCifrado.charAt(i);
            if (contadorBloque == TamañoBloque){
                Bloques.add(texto);
                texto = "";
                contadorBloque = 0;
            }
        }

        //DESCIFRAR
        texto = "";
        int posCresta = 0;
        for (int i = 0; i < TamañoBloque; i++) {
            if ((i%2)==0){
                texto += Crestas.charAt(posCresta);
                for (int j = 0; j < Bloques.size(); j++) {
                    texto += Bloques.get(j).charAt(i);
                }
                texto += Valles.charAt(posCresta);
                posCresta++;
            }else{
                for (int j = Bloques.size()-1 ; j >= 0; j--) {
                    texto += Bloques.get(j).charAt(i);
                }
            }
        }
        //QUITAR RELLENO
        String textoTerminado = "";
        for (int i = 0; i < texto.length(); i++) {
            if(texto.charAt(i) != '%'){
                textoTerminado += texto.charAt(i);
            }
        }

        return textoTerminado;
    }
}
