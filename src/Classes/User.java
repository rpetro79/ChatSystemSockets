package Classes;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof User) || obj == null)
            return false;
        User other = (User) obj;
        return other.name.equals(name) && other.password.equals(password);
    }
}
