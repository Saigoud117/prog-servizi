package ProgBien;

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;

@Entity
public class ArchivioRedazioneDettaglio {

	@ManyToOne // Lazy fetching produces a fails on removing a detail from invoice
	private ArchivioPubblicazioni archivio;
	
	@Id
    @Hidden // The property is not shown to the user. It's an internal identifier
    @GeneratedValue(generator="system-uuid") // Universally Unique Identifier (1)
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(length=32)
    private String oid;
	
	@NoFrame
	@ReferenceView("Archive")
	@ManyToOne(fetch=FetchType.LAZY)
    private Procedure procedure;

	public ArchivioPubblicazioni getArchivio() {
		return archivio;
	}

	public void setArchivio(ArchivioPubblicazioni archivio) {
		this.archivio = archivio;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public Procedure getProcedure() {
		return procedure;
	}

	public void setProcedure(Procedure procedure) {
		this.procedure = procedure;
	}
}
