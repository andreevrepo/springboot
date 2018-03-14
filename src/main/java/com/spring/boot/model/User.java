package com.spring.boot.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/*
 * @Entity - означает, что Класс является Сущностью и относится к таблице в бд
 *
 */

@Entity
public class User{
    private static final long serialVersionUID = 1723614580571357619L;
    //Требования

    //У сущности обязан быть default конструктор
    public User(){}

    //Обязан быть хотя бы один @Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    /*
        К не помеченым полям будут применены настройки по умолчанию
        имя переменной = имя поля в таблице бд
        тип переменной = соответствующий тип в бд(VARCHAR, BIGINT и т.д)
     */

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    /*
        @JoinTable создает связывающую таблицу
        joinColumns = @JoinColumn(name="название ключа User в связывающей таблице", referencedColumnName ="ссылка на имя ключа User")
        inverseJoinColumns = @JoinColumn(name="аналогично только с Role", referencesColumnName ="аналогично с Role")
        В итоге создаем связывающую таблицу, в которой будут содержаться ключи User и Role
     */
    @JoinTable(name = "join_role",
            joinColumns = @JoinColumn(name= "user_id" ,referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name= "role_id", referencedColumnName = "id")
    )
    //Связь один к одному
    @OneToOne
    private Role role;


    private Boolean enable = true;

    @Transient
    private String roleName;

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
