package ProgBien;

public class ProcedureKey implements java.io.Serializable { // The key class must be serializable
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer anno0; // It contains the properties marked ...
    private Integer codiceinterno; // ... as @Id in the entity
 
    public String toString() {
        return "ProcedureKey::" + anno0 + ":" + codiceinterno;
    }

	public Integer getAnno0() {
		return anno0;
	}

	public void setAnno0(Integer anno0) {
		this.anno0 = anno0;
	}

	public Integer getCodiceinterno() {
		return codiceinterno;
	}

	public void setCodiceinterno(Integer codiceinterno) {
		this.codiceinterno = codiceinterno;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((anno0 == null) ? 0 : anno0.hashCode());
		result = prime * result + ((codiceinterno == null) ? 0 : codiceinterno.hashCode());
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
		ProcedureKey other = (ProcedureKey) obj;
		if (anno0 == null) {
			if (other.anno0 != null)
				return false;
		} else if (!anno0.equals(other.anno0))
			return false;
		if (codiceinterno == null) {
			if (other.codiceinterno != null)
				return false;
		} else if (!codiceinterno.equals(other.codiceinterno))
			return false;
		return true;
	}
 
	
	
 
}
