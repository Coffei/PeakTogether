package pv243.peaktogether.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA. User: Coffei Date: 28.4.13 Time: 9:40 Entity
 * representing gallery of photos.
 */
@Entity
public class Gallery {

	@Id
	@GeneratedValue
	private Long id;

	@OneToMany(mappedBy="gallery",cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Photo> photos;

	@NotNull
	private String name;
	
	@Size(max=16553)
	private String description;
	
	//@NotNull
	@ManyToOne(optional = false)
	private User owner;
	
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

	
	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photo> photos) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Gallery other = (Gallery) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Gallery [id=" + id + ", name=" + name + ", description=" + description + ", isPublic=" + isPublic + "]";
	}

	
}
