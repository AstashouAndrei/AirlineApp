package by.gstu.airline.services;

/**
 * Class with description entity of user objects
 */

public class User {

    private int id;
    private String login;
    private String password;
    private String email;
    private Access access;

    public User(String login, String password, String email, Access access) {
        this.login = login;
        this.password = password;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
                email.equals(user.email) && access.equals(user.access);
    }

    @Override
    public int hashCode() {
        final int hash = 19;
        return hash * id + login.hashCode();
    }

    @Override
    public String toString() {
        return "Login: " + login + ". Email: " + email + ". Access level: " + access.getAccess();
    }
}
