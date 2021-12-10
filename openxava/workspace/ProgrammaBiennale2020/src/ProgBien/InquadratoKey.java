package ProgBien;

import javax.persistence.*;
import java.io.*;

@Embeddable
public class InquadratoKey implements Serializable {
	     
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String servizi;
	
	private String dipendenti;

	public String getServizio() {
		return servizi;
	}
	public void setServizio(String servizi) {
		this.servizi = servizi;
	}
	public String getDipendente() {
		return dipendenti;
	}
	public void setDipendente(String dipendenti) {
		this.dipendenti = dipendenti;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dipendenti == null) ? 0 : dipendenti.hashCode());
		result = prime * result + ((servizi == null) ? 0 : servizi.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InquadratoKey other = (InquadratoKey) obj;
		if (dipendenti == null) {
			if (other.dipendenti != null)
				return false;
		} else if (!dipendenti.equals(other.dipendenti))
			return false;
		if (servizi == null) {
			if (other.servizi != null)
				return false;
		} else if (!servizi.equals(other.servizi))
			return false;
		return true;
	}
	
	
     
}