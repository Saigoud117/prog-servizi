package ProgBien;

import java.io.*;
import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;

@Entity
@View(name="Simple", // This view is used only when “Simple” is specified
members="cf, cognome, nome" // Shows only number and name in the same line
)
public class Dipendenti implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @Required
    @Column(name = "CF", length = 16, nullable = true)
    private String cf;

    @Required
    @Column(name = "Cognome", length = 50, nullable = false)
    private String cognome;

    @Required
    @Column(name = "Nome", length = 50, nullable = false)
    private String nome;

    @Column(name = "RUP", nullable = false)
    private boolean rup;

    @Column(name = "Matricola", nullable = true)
    private Integer matricola;

	@OneToMany(mappedBy = "servizi")
    private Set<Inquadrato> inquadrato = new HashSet<Inquadrato>();

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
    public boolean isRup() {
		return rup;
	}

	public void setRup(boolean rup) {
		this.rup = rup;
	}

	public Integer getMatricola() {
		return matricola;
	}

	public void setMatricola(Integer matricola) {
		this.matricola = matricola;
	}
}
