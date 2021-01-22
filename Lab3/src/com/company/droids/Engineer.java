package com.company.droids;

import java.util.Random;

import static com.colors.Colors.*;
import static com.colors.Colors.ANSI_GREEN;

public class Engineer extends com.droids.Droid {
    private int addHP;

    public Engineer(String name) {
        this.name = name;
        this.health = new Random().nextInt(51) + 20;
        this.healer = true;
    }
    @Override
    public void action(com.droids.Droid target) {
        target.health += addHP;
        System.out.println(ANSI_GREEN + this.name + " healed " + target.name);
    }
}