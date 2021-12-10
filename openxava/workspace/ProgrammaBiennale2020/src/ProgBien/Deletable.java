package ProgBien;

import javax.persistence.*;

import org.hibernate.envers.*;
import org.openxava.annotations.*;

@MappedSuperclass
@Audited
public class Deletable {
	
 	@Hidden
    @Column(columnDefinition="boolean default false")
    private boolean deleted;
 
    public boolean isDeleted() {
        return deleted;
    }
 
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}
