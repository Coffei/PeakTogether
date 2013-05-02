package pv243.peaktogether.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA. User: Coffei Date: 28.4.13 Time: 9:40 Entity
 * representing a photo.
 */
@Entity
public class Photo {

	@Id
	@GeneratedValue
	private Long id;

	
	@Size(min=3,max=255)
	private String title;
	@Size(max=16553)
	private String description;

	
	@ManyToOne(optional=false)
	//@JoinColumn
	private Gallery gallery;

	@NotNull
	private User owner;

	
	@NotNull
	private String location; // file system location
								// "/home/peaktogether/pics/everest.jpg",
								// I don't think that it is a good practice to
								// to store binary data in objects until you
								// actually need them

	// another things to consider
	// Title
	// Keywords/search strings
	// Description
	// Author information
	// Date created
	// File format
	// Width
	// Height
	// Location/Source/Url
	// Resolution (Horizontal/Vertical) in dpi
	// Bit depth
	// Number of Layers
	// Color count

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getTitle() {
		return title;

	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	public Gallery getGallery() {
		return gallery;
	}

	public void setGallery(Gallery gallery) {
		this.gallery = gallery;
	}
	
	@Override
	public int hashCode() {
		
		return id.hashCode();
	}

}
