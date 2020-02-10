package main.java.by.gstu.airline.db;

import java.util.ResourceBundle;

/**
 * Class with method reading SQL commands from .properties file
 */
public class SqlCommands {

   private static final ResourceBundle resBundle = ResourceBundle.getBundle("db_commands");

    public static String getCommand(String command) {
        return resBundle.getString(command);
    }
}
