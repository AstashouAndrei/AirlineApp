package main.java.by.gstu.airline.db;

/**
 * Data base type enumeration
 */
public enum DataBaseType {
    MySQL("My SQL"),
    Oracle("Oracle Database"),
    PostgreSQL("PostgreSQL"),
    Microsof_SQL("Microsoft SQL Server"),
    MongoDB("MongoDB");

    private String dataBaseType;

    DataBaseType(String dataBaseType) {
        this.dataBaseType = dataBaseType;
    }

    public String getDataBaseType() {
        return dataBaseType;
    }

    public void setDataBaseType(String dataBaseType) {
        this.dataBaseType = dataBaseType;
    }
}
