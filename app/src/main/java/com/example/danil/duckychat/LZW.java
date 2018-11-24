/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.danil.duckychat;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 *
 * @author danil
 */
public class LZW {
    Hashtable<String, String> diccionarioCaracteres = new Hashtable<>();
    List<Character> CaracteresDistintos = new ArrayList<>();

    //AQUÃ�
    //MANDÃ�S A LLAMAR EL ÃšNICO QUE ES PRIVADO Y LE MANDÃ�S COMO PARÃ�METRO EL TEXTO DEL ARCHIVO Y TE DEVUELVE EL STRING DE LA TABLA
    //EJEMPLO: a/Ã³,b/$,c/#....etc..
    public String ComprimirTabla(String texto) {
        boolean exists;
        for (int i = 0; i < texto.length(); i++) {
                char letra = texto.charAt(i);
                exists = false;
                if (i == 0){
                CaracteresDistintos.add(letra);
                } else {
                    for(int j = 0; j < CaracteresDistintos.size(); j++){
                        if (letra == CaracteresDistintos.get(j)){
                            exists = true;
                        }                        
                    }
                    if (!exists){
                        CaracteresDistintos.add(letra);
                    }
                }
            }
        OrdenarLista();
        return LlenarDiccionario();
    }
    
    private void OrdenarLista(){
        char aux;
        for(int i = 0; i < CaracteresDistintos.size() - 1; i++){
            for (int j = i + 1; j < CaracteresDistintos.size(); j++){
                if(CaracteresDistintos.get(j) < CaracteresDistintos.get(i)){
                    aux = CaracteresDistintos.get(i);
                    CaracteresDistintos.set(i, CaracteresDistintos.get(j));
                    CaracteresDistintos.set(j, aux);
                }
            }
        }
    }
    private String LlenarDiccionario(){
        String tabla = "";
        for (int i = 0; i < CaracteresDistintos.size(); i++){
            diccionarioCaracteres.put(CaracteresDistintos.get(i).toString(), (char)(i+1) + "");
            tabla+= CaracteresDistintos.get(i);
        }
        return tabla;
    }


    //AQUÃ� ES DONDE SE COMPRIME EL TEXTO DEL ARCHIVO, LO MANDÃ�S A LLAMAR DESPUÃ‰S DEL DE LA TABLA
    //RECIBE EL MISMO PARÃ‰METRO, EL TEXTO COMPLETO DEL ARCHIVO
    public String ComprimirTexto(String texto){
        String textoComprimido = "";
        String anterior = "";
        String actual = "" + texto.charAt(0);
        int posición = 1;
        int SiguienteValor = CaracteresDistintos.size() + 1;
        while (posición < texto.length()) {            
            if(diccionarioCaracteres.containsKey(actual)){
                anterior = actual;
                actual += texto.charAt(posición);
                if (posición == texto.length()-1){
                actual = texto.charAt(posición)+ "";
                textoComprimido+= diccionarioCaracteres.get(anterior);
                textoComprimido+= diccionarioCaracteres.get(actual);
                diccionarioCaracteres.put(actual, (char)SiguienteValor + "");
                }
                posición++;
            } else {
                textoComprimido+= diccionarioCaracteres.get(anterior);                
                diccionarioCaracteres.put(actual, (char)SiguienteValor + "");
                SiguienteValor++;
                actual = "" + actual.charAt(actual.length() - 1);
            }            
        }
        
        diccionarioCaracteres.clear();
        CaracteresDistintos.clear();
        return textoComprimido;
    }



    public String Descomprimir(String tabla, String texto){
        for (int i = 0; i < tabla.length(); i++){
            diccionarioCaracteres.put ((char)(i+1) + "", tabla.charAt(i) + "");
        }
        String textoDescomprimido = "";
        String anterior = "";       
        int SiguienteValor = diccionarioCaracteres.size() + 1;
        for (int j = 0; j < texto.length(); j++) {
            String actual = diccionarioCaracteres.get(texto.charAt(j)+"");
            if (!anterior.equals("")) {
                if (!diccionarioCaracteres.containsValue(anterior + actual.charAt(0))){
                diccionarioCaracteres.put((char)SiguienteValor + "",anterior + actual.charAt(0));
                SiguienteValor++;
                }               
                textoDescomprimido+=actual;
                
            } else{
                textoDescomprimido+=actual;
            }
            anterior = actual;          
        }
        return textoDescomprimido;
    }
}
