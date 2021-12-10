package ProgBien;

import javax.persistence.*;

import org.openxava.annotations.*;

@Entity
@View(name="Simple", // This view is used only when “Simple” is specified
members="codice, descrizione" // Shows only number and name in the same line
)
public class Cpv {

    @Id
    @Required
    @Column(name = "Codice", length = 11, nullable = true)
    private String codice;

    @Column(name = "Descrizione", length = 250, nullable = true)
    private String descrizione;    

	@Required
    @Column(name = "Divisione", length = 2, nullable = false)
    private String divisione;
	
    @Required
    @Column(name = "CodiceLivello1", length = 1, nullable = false)
    private String codicelivello1;    

    @Column(name = "FoS", length = 1, nullable = true)
    private String fos;

    @Required
    @Column(name = "Informatica", nullable = false)
    private boolean informatica;

    public void setCodice(String aValue) {
        codice = aValue;
    }

    public String getCodice() {
        return codice;
    }

    public void setDescrizione(String aValue) {
        descrizione = aValue;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDivisione(String aValue) {
        divisione = aValue;
    }

    public String getDivisione() {
        return divisione;
    }
    
    public String getCodicelivello1() {
		return codicelivello1;
	}

	public void setCodicelivello1(String codicelivello1) {
		this.codicelivello1 = codicelivello1;
	}

	public void setFos(String aValue) {
        fos = aValue;
    }

    public String getFos() {
        return fos;
    }

    public void setInformatica(boolean aValue) {
        informatica = aValue;
    }

    public boolean getInformatica() {
        return informatica;
    }
}
