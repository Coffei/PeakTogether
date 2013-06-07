package pv243.peaktogether.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 * Created with IntelliJ IDEA. Member: Coffei Date: 28.4.13 Time: 9:40 Entity
 * representing gallery of photos.
 */
@Entity
public class Gallery implements Serializable {
    private static final long serialVersionUID = 983621346598678L;

	@Id
	@GeneratedValue
	private Long id;

	@OneToMany(mappedBy="gallery", cascade=CascadeType.ALL, orphanRemoval = true)
	private Set<Photo> photos;

	@NotNull
	private String name;
	
	@Size(max=16553)
	private String description;
	
	//@NotNull
	@ManyToOne(optional = false)
	private Member owner;
	
	@ManyToOne(optional=true)
	private Event event;
	
	@NotNull
	private Boolean isPublic;

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	
	public Member getOwner() {
		return owner;
	}

	public void setOwner(Member owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(Set<Photo> photos) {
		this.photos = photos;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Gallery gallery = (Gallery) o;

        if (description != null ? !description.equals(gallery.description) : gallery.description != null) return false;
        if (id != null ? !id.equals(gallery.id) : gallery.id != null) return false;
        if (isPublic != null ? !isPublic.equals(gallery.isPublic) : gallery.isPublic != null) return false;
        if (name != null ? !name.equals(gallery.name) : gallery.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (isPublic != null ? isPublic.hashCode() : 0);
        return result;
    }

    @Override
	public String toString() {
		return "Gallery [id=" + id + ", name=" + name + ", description=" + description + ", isPublic=" + isPublic + "]";
	}

	
}
