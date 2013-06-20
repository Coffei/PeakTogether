package pv243.peaktogether.model;

import pv243.peaktogether.model.validations.CorrectCapacity;
import pv243.peaktogether.model.validations.HasLocations;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Member: Coffei
 * Date: 27.4.13
 * Time: 19:00
 * Entity representing an event.
 */
@Entity
@CorrectCapacity(message = "capacity should be bigger than 1 if the event is limited") //this is validation of capacity and limited
public class Event implements Serializable {
    private static final long serialVersionUID = 9445646798L;

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(min = 3, max = 255)
    private String name;

    @Size(min = 0, max = 16553)
    private String description;

    @NotNull
    private Boolean publicEvent;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = true)
    private Photo picture;

    @OneToMany(mappedBy = "event")
    private List<Gallery> galleries;

    @ManyToOne(optional = false)
    private Member owner;

    //validated by @CorrectCapacity
    private Integer capacity;

    //validated by @CorrectCapacity
    @NotNull
    private Boolean limited;

    @Future(message = "event cannot start in the past")
    @NotNull
    private Date start;

    @ManyToMany()
    private Set<Member> joinedMembers;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Skill> requirements;

    @HasLocations(typeRequested = LocationType.START, message = "there has to be at least one start location")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Location> locations;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getPublicEvent() {
        return publicEvent;
    }

    public void setPublicEvent(Boolean publicEvent) {
        this.publicEvent = publicEvent;
    }

    public Photo getPicture() {
        return picture;
    }

    public void setPicture(Photo picture) {
        this.picture = picture;
    }

    public List<Gallery> getGalleries() {
        return galleries;
    }

    public void setGalleries(List<Gallery> galleries) {
        this.galleries = galleries;
    }

    public Member getOwner() {
        return owner;
    }

    public void setOwner(Member owner) {
        this.owner = owner;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Boolean isLimited() {
        return limited;
    }

    public void setLimited(Boolean limited) {
        this.limited = limited;
    }

    public Boolean getLimited() {
        return limited;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Set<Member> getJoinedMembers() {
        return joinedMembers;
    }

    public void setJoinedMembers(Set<Member> joinedMembers) {
        this.joinedMembers = joinedMembers;
    }

    public Set<Skill> getRequirements() {
        return requirements;
    }

    public void setRequirements(Set<Skill> requirements) {
        this.requirements = requirements;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (id != null ? !id.equals(event.id) : event.id != null) return false;
        if (locations != null ? !locations.equals(event.locations) : event.locations != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner=" + owner +
                '}';
    }
}
