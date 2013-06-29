package pv243.peaktogether.model;

import com.vividsolutions.jts.geom.Point;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Member: Coffei
 * Date: 28.4.13
 * Time: 9:32
 * Entity representing the user.
 */
@Entity
public class Member implements Serializable {
    private static final long serialVersionUID = 435734452982163L;

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(min = 3, max = 128)
    @Column(unique = true)
    private String username;

    @Email
    @Size(min = 3, max=256)
    @Column(unique = true)
    private String email;

    @NotNull
    private Date registered;

    @ManyToOne(optional = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Photo picture;

    @OneToMany(mappedBy = "owner")
    private List<Gallery> galleries;

    @ManyToMany(fetch = FetchType.LAZY) //lazy fetch to prevent dependency cycles
    private List<Member> friends;

    @Size(min = 0, max = 512)
    private String status;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Skill> skills;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    private List<Event> ownedEvents;

    @ManyToMany(mappedBy = "joinedMembers")
    private List<Event> joinedEvents;

    @Type(type = "org.hibernate.spatial.GeometryType")
    private Point homeLocation;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
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

    public List<Member> getFriends() {
        return friends;
    }

    public void setFriends(List<Member> friends) {
        this.friends = friends;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public List<Event> getOwnedEvents() {
        return ownedEvents;
    }

    public void setOwnedEvents(List<Event> ownedEvents) {
        this.ownedEvents = ownedEvents;
    }

    public List<Event> getJoinedEvents() {
        return joinedEvents;
    }

    public void setJoinedEvents(List<Event> joinedEvents) {
        this.joinedEvents = joinedEvents;
    }

    public Point getHomeLocation() {
        return homeLocation;
    }

    public void setHomeLocation(Point homeLocation) {
        this.homeLocation = homeLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Member member = (Member) o;

        if (id != null ? !id.equals(member.id) : member.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
