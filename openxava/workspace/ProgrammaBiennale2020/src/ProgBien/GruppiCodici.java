package ProgBien;

import javax.persistence.*;
import org.openxava.annotations.*;

@Entity
public class GruppiCodici {

    @Id
    @Required
    @Column(name = "Tipo", length=1, nullable = false)
    private String tipo;

    @Column(name = "Tipo2", length=1, nullable = true)
    private String tipo2;

    @Required
    @Column(name = "Descrizione", length=100, nullable = false)
    private String descrizione;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTipo2() {
		return tipo2;
	}

	public void setTipo2(String tipo2) {
		this.tipo2 = tipo2;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
    
}