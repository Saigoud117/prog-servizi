package ProgBien;

import java.util.*;
import javax.persistence.*;
import org.openxava.annotations.*;

import Filters.*;

import org.hibernate.annotations.GenericGenerator;

@SuppressWarnings("serial")
@Entity
@View(name="Simple", // This view is used only when “Simple” is specified
members="servizio, struttura, descrizione" // Shows only number and name in the same line
)
@Tab(
//name="Current",
filter=CurrentUserServizioStrutturaFilter.class,
//properties="servizio, struttura, descrizione, codicesicurezza, datainizio, datafine, nstruttura, nservizio, inquadrato",
baseCondition="(? = 1 or ${servizio} in (?)) and (? = 1 or ${struttura} in (?)) and ${datainizio} < now() and ${datafine} > now()"
)
public class Servizi implements java.io.Serializable {
	
	@Id
    @Hidden // The property is not shown to the user. It's an internal identifier
    @GeneratedValue(generator="system-uuid") // Universally Unique Identifier (1)
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(length=32)
    private String oid;

	@Required
    @Column(name = "Servizio", length = 10, nullable = false)
    private String servizio;
	

    @Required
    @Column(name = "Struttura", length = 10, nullable = false)
    private String struttura;

    @Required
    @Column(name = "Descrizione", length = 1000, nullable = false)
    private String descrizione;

    @Column(name = "CodiceSicurezza", nullable = true)
    private Integer codicesicurezza;

    @Required
    @Column(name = "Datainizio", nullable = false)
    private Date datainizio;

    @Required
    @Column(name = "Datafine", nullable = false)
    private Date datafine;

    @Column(name = "NStruttura", length = 10, nullable = true)
    private String nstruttura;

    @Column(name = "Nservizio", length = 10, nullable = true)
    private String nservizio;
    
    @OneToMany(mappedBy = "dipendenti")
    private Set<Inquadrato> inquadrato = new HashSet<Inquadrato>();
    
    public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

    public void setServizio(String aValue) {
        servizio = aValue;
    }

    public String getServizio() {
        return servizio;
    }

    public void setStruttura(String aValue) {
        struttura = aValue;
    }

    public String getStruttura() {
        return struttura;
    }

    public void setDescrizione(String aValue) {
        descrizione = aValue;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setCodicesicurezza(Integer aValue) {
        codicesicurezza = aValue;
    }

    public Integer getCodicesicurezza() {
        return codicesicurezza;
    }

    public void setDatainizio(Date aValue) {
        datainizio = aValue;
    }

    public Date getDatainizio() {
        return datainizio;
    }

    public void setDatafine(Date aValue) {
        datafine = aValue;
    }

    public Date getDatafine() {
        return datafine;
    }

    public void setNstruttura(String aValue) {
        nstruttura = aValue;
    }

    public String getNstruttura() {
        return nstruttura;
    }

    public void setNservizio(String aValue) {
        nservizio = aValue;
    }

    public String getNservizio() {
        return nservizio;
    }
}
