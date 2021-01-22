package com.kanils.client;

import com.kanils.database.Connector;
import com.kanils.main.Main;
import com.kanils.user.User;

import java.util.Scanner;


public class LoginUser {

    public static boolean doLogin(User user, Connector connector,User user1) {
        Main.log.info("Вхід на аккаунт");
        String email;
        String password;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введіть email: ");
        email = scanner.nextLine();

        System.out.print("Введіть пароль: ");
        password = scanner.nextLine();
//connector.checkUser(email, password, user)
        try {
            if (user.getEmail()==user1.getEmail()) {
                System.out.println("Добрий день, "+user.getName());
                return true;
            } else {
                System.out.println("Не вірний email або password!!");
                Main.log.warn("Невірний ввід даних");
                return false;
            }
        } catch (Exception ex) {
            //System.out.println(ex.getMessage());
            return false;
        }

    }


}