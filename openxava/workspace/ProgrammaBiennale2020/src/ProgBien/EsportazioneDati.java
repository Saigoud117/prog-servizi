package ProgBien;

import java.util.*;

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;

@Entity
@Immutable
public class EsportazioneDati {
	
	@Id
    @Hidden // The property is not shown to the user. It's an internal identifier
    @GeneratedValue(generator="system-uuid") // Universally Unique Identifier (1)
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(length=32)
    private String oid;
	
	@ReadOnly
	private boolean produzione;
	
	@Stereotype("DATETIME")
	private Date data;
	
	@Column(length = 4096) 
	@Stereotype("TEXT_AREA") // This is for a big text, a text area or equivalent will be used
	private String jsonSentData;
	
	@Column(length = 4096)
	@Stereotype("TEXT_AREA") // This is for a big text, a text area or equivalent will be used
	private String jsonAnswer;
	
	private boolean success;
	
	@Column(nullable = true)
	private int idEsportazione;

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public boolean isProduzione() {
		return produzione;
	}

	public void setProduzione(boolean produzione) {
		this.produzione = produzione;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getJsonSentData() {
		return jsonSentData;
	}

	public void setJsonSentData(String jsonSentData) {
		this.jsonSentData = jsonSentData;
	}

	public String getJsonAnswer() {
		return jsonAnswer;
	}

	public void setJsonAnswer(String jsonAnswer) {
		this.jsonAnswer = jsonAnswer;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getIdEsportazione() {
		return idEsportazione;
	}

	public void setIdEsportazione(int idEsportazione) {
		this.idEsportazione = idEsportazione;
	}
	
}
