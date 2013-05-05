package pv243.peaktogether.model;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Coffei
 * Date: 28.4.13
 * Time: 9:36
 * Entity representing a skill of certain sport at some level.
 */
@Entity
public class Skill implements Serializable {
    private static final long serialVersionUID = 671245L;

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private Sport sport;

    @DecimalMax(value= "10", message = "level must be below or equal to 10")
    @DecimalMin(value = "0", message = "level must be above or equal to 0")
    private int level;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Skill skill = (Skill) o;

        if (!id.equals(skill.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", sport=" + sport +
                ", level=" + level +
                '}';
    }
}
