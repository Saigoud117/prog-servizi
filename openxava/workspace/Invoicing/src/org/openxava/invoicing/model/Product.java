package org.openxava.invoicing.model;

import javax.persistence.*;
import org.openxava.annotations.*;
import java.math.*;
 
@Entity
public class Product {
 
    @Id @Column(length=9)
    private int number;
 
    @Column(length=50) @Required
    private String description;
    
    @ManyToOne( // The reference is persisted as a database relationship
            fetch=FetchType.LAZY, // The reference is loaded on demand
            optional=true) // The reference can have no value
        @DescriptionsList // Thus the reference is displayed using a combo
        private Category category; // A regular Java reference
    
    public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public String getMorePhotos() {
		return morePhotos;
	}
	public void setMorePhotos(String morePhotos) {
		this.morePhotos = morePhotos;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@Stereotype("MONEY") // The price property is used to store money
    private BigDecimal price; // Include the import java.math.*  BigDecimal is typically used for money
     
    @Stereotype("PHOTO") // The user can view and change a photo
    @Column(length=16777216) // This size to store big photos
    private byte [] photo;
     
    @Stereotype("IMAGES_GALLERY") // A complete image gallery is available
    @Column(length=32) // The 32 length string is for storing the key of the gallery
    private String morePhotos;
     
    @Stereotype("MEMO") // This is for a big text, a text area or equivalent will be used
    private String remarks;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @DescriptionsList
    private Author author;
 
    public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}
	public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
 
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }   
 
}