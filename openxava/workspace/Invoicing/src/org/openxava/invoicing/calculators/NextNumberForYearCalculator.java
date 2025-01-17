package org.openxava.invoicing.calculators;
 
import javax.persistence.*;
import org.openxava.calculators.*;
import org.openxava.jpa.*;
 
public class NextNumberForYearCalculator implements ICalculator { // A calculator must implement ICalculator
 
    private int year; // This value will be injected (using its setter) before calculating
 
    public Object calculate() throws Exception { // It does the calculation
        Query query = XPersistence.getManager() // A JPA query
            .createQuery("select max(i.number) from Invoice i" + " where i.year = :year"); // The query returns
                                                                // the max invoice number of the indicated year
        query.setParameter("year", year); // We use the injected year as a parameter for the query
        Integer lastNumber = (Integer) query.getSingleResult();
        return lastNumber == null ? 1 : lastNumber + 1; // Returns the last invoice number
                                                        // of the year + 1 or 1 if there is no last number
    }
 
    public int getYear() {
        return year;
    }
 
    public void setYear(int year) {
        this.year = year;
    }
 
}