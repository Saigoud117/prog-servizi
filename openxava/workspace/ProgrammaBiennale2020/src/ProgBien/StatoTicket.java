package ProgBien;

import javax.persistence.*;

import org.openxava.annotations.*;

@Entity
public class StatoTicket {
	@Id
    @Required
    @Column(name = "Key", length=1, nullable = false)
    private String key;

    @Required
    @Column(name = "Descrizione", length=100, nullable = false)
    private String descrizione;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
}
