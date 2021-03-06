package pv243.peaktogether.model;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Member: Coffei
 * Date: 28.4.13
 * Time: 9:36
 * Entity representing a skill of certain sport at some level.
 */
@Entity
public class Skill implements Serializable {
    private static final long serialVersionUID = 671245L;

    public Skill() {}

    public Skill(Sport sport, int level) {
        this.sport = sport;
        this.level = level;
    }

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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + level;
		result = prime * result + ((sport == null) ? 0 : sport.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Skill other = (Skill) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (level != other.level)
			return false;
		if (sport != other.sport)
			return false;
		return true;
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
