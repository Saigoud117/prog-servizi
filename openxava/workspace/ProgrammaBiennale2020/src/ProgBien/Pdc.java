package ProgBien;

import javax.persistence.*;

import org.openxava.annotations.*;

@Entity
@View(name="Simple", // This view is used only when “Simple” is specified
members="codice, descrizione" // Shows only number and name in the same line
)
public class Pdc {
	
	@Id
	@Required
	@Column(name = "Codice", length = 14, nullable = true)
	private String codice;
	 
	@Column(name = "Siope", length = 14, nullable = true)
	private String siope;
	 
	@Column(name = "FoS", length = 1, nullable = true)
	private String fos;

	@Column(name = "Divisione", length = 2, nullable = true)
	private String divisione;
	
	@Column(name = "Descrizione", length = 150, nullable = true)
    private String descrizione;
	
	@Column(name = "Glossario", length = 1500, nullable = true)
    private String glossario;

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getSiope() {
		return siope;
	}

	public void setSiope(String siope) {
		this.siope = siope;
	}

	public String getFos() {
		return fos;
	}

	public void setFos(String fos) {
		this.fos = fos;
	}

	public String getDivisione() {
		return divisione;
	}

	public void setDivisione(String divisione) {
		this.divisione = divisione;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getGlossario() {
		return glossario;
	}

	public void setGlossario(String glossario) {
		this.glossario = glossario;
	}	
}
