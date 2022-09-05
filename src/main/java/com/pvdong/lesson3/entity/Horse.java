package com.pvdong.lesson3.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "horse")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Horse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "foaled", nullable = false)
    private Date foaled;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Horse that = (Horse) o;
        return this.name.equals(that.name) && this.foaled.equals(that.foaled) && this.id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
