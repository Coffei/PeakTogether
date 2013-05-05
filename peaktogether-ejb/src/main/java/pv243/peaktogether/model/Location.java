package pv243.peaktogether.model;

import com.vividsolutions.jts.geom.Point;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Coffei
 * Date: 28.4.13
 * Time: 12:14
 * Entity representing point of some kind.
 */
@Entity
public class Location implements Serializable {
    private static final long serialVersionUID = 6784323498L;

    public Location(LocationType type, Point point) {
        this.type = type;
        this.point = point;
    }

    public Location() { }

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private LocationType type;

    @Type(type = "org.hibernate.spatial.GeometryType")
    @NotNull
    private Point point;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocationType getType() {
        return type;
    }

    public void setType(LocationType type) {
        this.type = type;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (id != null ? !id.equals(location.id) : location.id != null) return false;
        if (point != null ? !point.equals(location.point) : location.point != null) return false;
        if (type != location.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (point != null ? point.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", type=" + type +
                ", point=" + point +
                '}';
    }
}
