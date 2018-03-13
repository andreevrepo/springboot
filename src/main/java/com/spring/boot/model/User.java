package com.spring.boot.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by AndrewPC on 12.01.2018.
 * Time is: 18:27
 */

@Entity
public class User implements Serializable{
    private static final long serialVersionUID = 1723614580571357619L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    //TODO подробности работы JoinTable OneToOne
    @JoinTable(name = "join_role",
            joinColumns = @JoinColumn(name= "user_id" ,referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name= "role_id", referencedColumnName = "id")
    )
    @OneToOne
    private Role role;

    private Boolean enable = true;

    @Transient
    private String roleName;

    @SuppressWarnings("for hibernate")
    public User(){}

    //Constructor for result
    public User(long id, Role role, String name, String login, String password) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.role = role;
    }
    public User(long id, String name, String login, String password) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    //Constructor for set
    public User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(name, user.name) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, login, password);
    }
}
