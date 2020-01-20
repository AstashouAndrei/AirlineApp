package by.gstu.airline.entity.services;

public class User {

    private int id;
    private String login;
    private String password;
    private Access access;

    public User(String login, String password, Access access) {
        this.login = login;
        this.password = password;
        this.access = access;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Access getAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return login.equals(user.login) && password.equals(user.password) &&
                access.equals(user.access);
    }

    @Override
    public int hashCode() {
        final int hash = 19;
        return hash * id;
    }

    @Override
    public String toString() {
        return "Login: " + login + ". Access level: " + access.getAccess();
    }
}
