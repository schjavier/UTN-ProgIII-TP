package com.utn.ProgIII.View.Menu;

import com.utn.ProgIII.View.ApiManager.ApiManagerImp;

import java.util.Scanner;

public class Menu {

    public static void menu (Scanner scan){

        ApiManagerImp manager = new ApiManagerImp();

        int opcion = 1;

        System.out.println("Iniciar Sesion");

        //logeo
        System.out.println("Usuario");
        String username = scan.nextLine();
        System.out.println("Contraseña");
        String password = scan.nextLine();

//        switch (rol){
//            case 1: //admin
//            case 2: //manager
//            case 3: //cashier(cajero)
//        }

    }

    //elegir opcion
    public static int chooseOption(Scanner scan){

        int opcion = 0;
        System.out.println("Ingrese una opcion:  ");
        opcion = scan.nextInt();
        scan.nextLine();
        return opcion;
    }



}
