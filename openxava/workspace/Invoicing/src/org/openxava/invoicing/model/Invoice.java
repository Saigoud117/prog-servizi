package org.openxava.invoicing.model;
 
import java.util.*;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator; // ADD THIS IMPORT
import org.openxava.annotations.*;
import org.openxava.calculators.*;
import org.openxava.invoicing.calculators.*;
 
@Entity
@View(members= // This view has no name, so it will be the view used by default
"year, number, date;" + // Comma separated means in the same line
"customer;" + // Semicolon means a new line
"details;" +
"remarks"
)
public class Invoice implements java.io.Serializable {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// ADD THE oid PROPERTY
    @Id
    @GeneratedValue(generator="system-uuid")
    @Hidden
    @GenericGenerator(name="system-uuid", strategy="uuid")
    @Column(length=32)
    private String oid;
 
    // REMEMBER TO GENERATE THE GETTER AND SETTER FOR oid
 
    @Column(length=4)
    @DefaultValueCalculator(CurrentYearCalculator.class) // Current year
    private int year;    
 
    public Collection<Detail> getDetails() {
		return details;
	}

	public void setDetails(Collection<Detail> details) {
		this.details = details;
	}

	@Id @Column(length=6)
    @DefaultValueCalculator(value=NextNumberForYearCalculator.class,
        properties=@PropertyValue(name="year") // To inject the value of year from Invoice to
                                               // the calculator before calling to calculate()
    )
    private int number;
    
    @Required
    @DefaultValueCalculator(CurrentDateCalculator.class) // Current date
    private Date date;
 
    @Stereotype("MEMO")
    private String remarks;
    
    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @ReferenceView("Simple") // The view named 'Simple' is used to display this reference
    private Customer customer;
    
    @ElementCollection
    @ListProperties("product.number, product.description, quantity")
    private Collection<Detail> details;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
 
}