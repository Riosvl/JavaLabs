package com.kanils.main;

import com.kanils.client.Menu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {
    public static Logger log = LogManager.getRootLogger();
    public static void main(String[] args) {
        Menu.start();
    }
}
