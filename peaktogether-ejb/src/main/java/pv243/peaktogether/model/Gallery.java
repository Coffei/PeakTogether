package pv243.peaktogether.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA. User: Coffei Date: 28.4.13 Time: 9:40 Entity
 * representing gallery of photos.
 */
@Entity
public class Gallery {

	@Id
	@GeneratedValue
	private Long id;

	@OneToMany
	private List<Photo> photos;

	@NotNull
	private String name;
	private String description;
	@NotNull
	private User User;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return User;
	}

	public void setUser(User user) {
		User = user;
	}

	private Boolean isPublic;

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

		return id.hashCode();
	}
	
	@Override
	public String toString() {
		
		return "";
	}

}
