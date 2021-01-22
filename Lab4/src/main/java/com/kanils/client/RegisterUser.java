package com.kanils.client;

import com.kanils.database.Connector;
import com.kanils.main.Main;
import com.kanils.user.User;

import java.util.Scanner;

public class RegisterUser {

    public static User doRegistration(User user, Connector connector) {

        return set_user_info(user,connector);
    }

    private static User set_user_info(User user, Connector connector) {
        Scanner scanner = new Scanner(System.in);
        Main.log.info("Реєстрація");
        String name;
        String email;
        String password;
        User user1;
        int key;
        do {
            System.out.print("Введіть ваше ім`я: ");
            name = scanner.nextLine();
            System.out.print("Введіть ваш email: ");
            email = scanner.nextLine();
            System.out.print("Введіть ваш пароль: ");
            password = scanner.nextLine();
            user1 = new User(email,name,0,password,1);
            System.out.println("Все ввели вірно? ");
            System.out.println("Так - 1, Ні - 2");
            key = scanner.nextInt();
            if (key == 1) {
                try {
                    // connector.addNewUser(email,password,name);

                }catch (Exception ex){
                    Main.log.error("Не вдалось за реєструвати");
                    // System.out.println(ex.getMessage());
                }
                break;
            }

        } while (true);

        return user1;
    }
}