package pv243.peaktogether.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA. Member: Coffei Date: 28.4.13 Time: 9:40 Entity
 * representing a photo.
 */
@Entity
public class Photo implements Serializable {
    private static final long serialVersionUID = 347267864536L;

	@Id
	@GeneratedValue
	private Long id;

	
	@Size(min=3,max=255)
	private String title;
	@Size(max=16553)
	private String description;

	
	@ManyToOne()
	private Gallery gallery;


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

	public Member getOwner() {
		return getGallery().getOwner();
	}

	public void setOwner(Member owner) {
		//noop
	}
	
	public Gallery getGallery() {
		return gallery;
	}

	public void setGallery(Gallery gallery) {
		this.gallery = gallery;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Photo photo = (Photo) o;

        if (description != null ? !description.equals(photo.description) : photo.description != null) return false;
        if (id != null ? !id.equals(photo.id) : photo.id != null) return false;
        if (location != null ? !location.equals(photo.location) : photo.location != null) return false;
        if (title != null ? !title.equals(photo.title) : photo.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
