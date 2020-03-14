package main.java.by.gstu.airline.db;

import java.util.ResourceBundle;

/**
 * Class for reading and initializing data base configuration (URL, login, password)
 * from the properties file
 */

public class ConfigurationManager {

    private ResourceBundle bundle = ResourceBundle.getBundle("main.resources.db_configuration");

    public final String DRIVER= bundle.getString("DRIVER");
    public final String DATA_BASE = bundle.getString("DATA_BASE");
    public final String URL = bundle.getString("URL");
    public final String USER = bundle.getString("USER");
    public final String PASSWORD = bundle.getString("PASSWORD");

    private static ConfigurationManager instance;

    private ConfigurationManager() {
    }

    /**
     * Returns an instance of ConfigurationManager.
     * Creates new ConfigurationManager if its not exist, returns existing instance otherwise
     *
     * @return instance of connection pool
     */
    public static ConfigurationManager getInstance() {
        if (instance == null) {
            return instance = new ConfigurationManager();
        }
        return instance;
    }
}
