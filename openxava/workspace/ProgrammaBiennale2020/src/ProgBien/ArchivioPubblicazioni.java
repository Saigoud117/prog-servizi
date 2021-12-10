package ProgBien;

import java.util.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;

@Entity
@Immutable
public class ArchivioPubblicazioni {
	
	@Id
    @Hidden // The property is not shown to the user. It's an internal identifier
    @GeneratedValue(generator="system-uuid") // Universally Unique Identifier (1)
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(length=32)
    private String oid;
	
	@ReadOnly
	@ReferenceView("Simple")
	@ManyToOne( // The reference is persisted as a database relationship
		fetch=FetchType.LAZY, // The reference is loaded on demand
        optional=false) // The reference can have no value
    @DescriptionsList(descriptionProperties="id, dataApprovazione") // Thus the reference is displayed using a combo
    private Pubblicazione pubblicazione; // A regular Java reference
	
	@ReadOnly
	@OneToMany (mappedBy="archivio", cascade=CascadeType.REMOVE)
	@ListProperties("procedure.stato.descrizione, procedure.cui, procedure.descrizione, procedure.totalequadroeconomico")
    private Collection<ArchivioRedazioneDettaglio> archivioredazionedettaglio;
	
	@ReadOnly
	@OneToMany (mappedBy="archivio", cascade=CascadeType.REMOVE)
	@ListProperties("procedure.stato.descrizione, procedure.cui, procedure.descrizione, procedure.totalequadroeconomico")
    private Collection<ArchivioModificaDettaglio> archiviomodifichedettaglio;
	
	@ReadOnly
	@OneToMany (mappedBy="archivio", cascade=CascadeType.REMOVE)
	@ListProperties("procedure.stato.descrizione, procedure.cui, procedure.descrizione, procedure.totalequadroeconomico")
    private Collection<ArchivioNonRiproposteDettaglio> archiviononripropostedettaglio;

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public Pubblicazione getPubblicazione() {
		return pubblicazione;
	}

	public void setPubblicazione(Pubblicazione pubblicazione) {
		this.pubblicazione = pubblicazione;
	}

	public Collection<ArchivioRedazioneDettaglio> getArchivioredazionedettaglio() {
		return archivioredazionedettaglio;
	}

	public void setArchivioredazionedettaglio(Collection<ArchivioRedazioneDettaglio> archivioredazionedettaglio) {
		this.archivioredazionedettaglio = archivioredazionedettaglio;
	}

	public Collection<ArchivioModificaDettaglio> getArchiviomodifichedettaglio() {
		return archiviomodifichedettaglio;
	}

	public void setArchiviomodifichedettaglio(Collection<ArchivioModificaDettaglio> archiviomodifichedettaglio) {
		this.archiviomodifichedettaglio = archiviomodifichedettaglio;
	}

	public Collection<ArchivioNonRiproposteDettaglio> getArchiviononripropostedettaglio() {
		return archiviononripropostedettaglio;
	}

	public void setArchiviononripropostedettaglio(
			Collection<ArchivioNonRiproposteDettaglio> archiviononripropostedettaglio) {
		this.archiviononripropostedettaglio = archiviononripropostedettaglio;
	}
}
