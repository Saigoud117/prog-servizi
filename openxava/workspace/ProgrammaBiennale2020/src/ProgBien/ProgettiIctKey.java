package ProgBien;

public class ProgettiIctKey implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private String sigla;
    private Servizi servizi;
    
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public Servizi getServizi() {
		return servizi;
	}
	public void setServizi(Servizi servizi) {
		this.servizi = servizi;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((servizi == null) ? 0 : servizi.hashCode());
		result = prime * result + ((sigla == null) ? 0 : sigla.hashCode());
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
		ProgettiIctKey other = (ProgettiIctKey) obj;
		if (servizi == null) {
			if (other.servizi != null)
				return false;
		} else if (!servizi.equals(other.servizi))
			return false;
		if (sigla == null) {
			if (other.sigla != null)
				return false;
		} else if (!sigla.equals(other.sigla))
			return false;
		return true;
	}
        
}
