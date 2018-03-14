package com.spring.boot.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/*
 * @Entity - означает, что Класс является Сущностью и относится к таблице в бд
 *
 */

@Entity
public class Role {
    private static final long serialVersionUID = 9091673488504929047L;
    //Требования

    //У сущности обязан быть default конструктор
    public Role(){}

    //Обязан быть хотя бы один @Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String role;

    public Role(String role) {
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role1 = (Role) o;
        return id == role1.id && Objects.equals(role, role1.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role);
    }

    @Override
    public String toString() {
        return role;
    }
}
