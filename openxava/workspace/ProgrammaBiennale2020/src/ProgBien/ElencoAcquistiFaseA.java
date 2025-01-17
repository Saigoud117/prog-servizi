package ProgBien;

import javax.persistence.*;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.*;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties("row_number")
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="row_number")
public class ElencoAcquistiFaseA {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long row_number;
	
	private String codicecui;
	private String codicefiscaleamministrazione;
	private int primaAnnualita;
	private String annualitaAvvioProceduraAffidamento;
	private String codiceCup;
	private String lottoFunzionale;
	private String valoreStimatoAppalto;
	private String nuts;
	private String cupMaster;
	private String settore;
	private String cpv;
	private String descrizioneAcquisto;
	private String conformita_ambientale;
	private String livelloPriorita;
	private String cfResponsabileProcedimento;
	private String nomeResponsabileProcedimento;
	private String cognomeResponsabileProcedimento;
	private String quantita;
	private String unitaDiMisura;
	private String durataMesi;
	private String costiA1;
	private String costiA2;
	private String costiAs;
	private String costoTotale;
	private String apporto_capitale_privato;
	private String tipologia_capitale_privato;
	private String delega;
	private String codiceAusa;
	private String denominazioneAusa;
	public long getRow_number() {
		return row_number;
	}
	public void setRow_number(long row_number) {
		this.row_number = row_number;
	}
	public String getCodicecui() {
		return codicecui;
	}
	public void setCodicecui(String codicecui) {
		this.codicecui = codicecui;
	}
	public String getCodicefiscaleamministrazione() {
		return codicefiscaleamministrazione;
	}
	public void setCodicefiscaleamministrazione(String codicefiscaleamministrazione) {
		this.codicefiscaleamministrazione = codicefiscaleamministrazione;
	}
	public int getPrimaAnnualita() {
		return primaAnnualita;
	}
	public void setPrimaAnnualita(int primaAnnualita) {
		this.primaAnnualita = primaAnnualita;
	}
	public String getAnnualitaAvvioProceduraAffidamento() {
		return annualitaAvvioProceduraAffidamento;
	}
	public void setAnnualitaAvvioProceduraAffidamento(String annualitaAvvioProceduraAffidamento) {
		this.annualitaAvvioProceduraAffidamento = annualitaAvvioProceduraAffidamento;
	}
	public String getCodiceCup() {
		return codiceCup;
	}
	public void setCodiceCup(String codiceCup) {
		this.codiceCup = codiceCup;
	}
	public String getLottoFunzionale() {
		return lottoFunzionale;
	}
	public void setLottoFunzionale(String lottoFunzionale) {
		this.lottoFunzionale = lottoFunzionale;
	}
	public String getValoreStimatoAppalto() {
		return valoreStimatoAppalto;
	}
	public void setValoreStimatoAppalto(String valoreStimatoAppalto) {
		this.valoreStimatoAppalto = valoreStimatoAppalto;
	}
	public String getNuts() {
		return nuts;
	}
	public void setNuts(String nuts) {
		this.nuts = nuts;
	}
	public String getCupMaster() {
		return cupMaster;
	}
	public void setCupMaster(String cupMaster) {
		this.cupMaster = cupMaster;
	}
	public String getSettore() {
		return settore;
	}
	public void setSettore(String settore) {
		this.settore = settore;
	}
	public String getCpv() {
		return cpv;
	}
	public void setCpv(String cpv) {
		this.cpv = cpv;
	}
	public String getDescrizioneAcquisto() {
		return descrizioneAcquisto;
	}
	public void setDescrizioneAcquisto(String descrizioneAcquisto) {
		this.descrizioneAcquisto = descrizioneAcquisto;
	}
	public String getConformita_ambientale() {
		return conformita_ambientale;
	}
	public void setConformita_ambientale(String conformita_ambientale) {
		this.conformita_ambientale = conformita_ambientale;
	}
	public String getLivelloPriorita() {
		return livelloPriorita;
	}
	public void setLivelloPriorita(String livelloPriorita) {
		this.livelloPriorita = livelloPriorita;
	}
	public String getCfResponsabileProcedimento() {
		return cfResponsabileProcedimento;
	}
	public void setCfResponsabileProcedimento(String cfResponsabileProcedimento) {
		this.cfResponsabileProcedimento = cfResponsabileProcedimento;
	}
	public String getNomeResponsabileProcedimento() {
		return nomeResponsabileProcedimento;
	}
	public void setNomeResponsabileProcedimento(String nomeResponsabileProcedimento) {
		this.nomeResponsabileProcedimento = nomeResponsabileProcedimento;
	}
	public String getCognomeResponsabileProcedimento() {
		return cognomeResponsabileProcedimento;
	}
	public void setCognomeResponsabileProcedimento(String cognomeResponsabileProcedimento) {
		this.cognomeResponsabileProcedimento = cognomeResponsabileProcedimento;
	}
	public String getQuantita() {
		return quantita;
	}
	public void setQuantita(String quantita) {
		this.quantita = quantita;
	}
	public String getUnitaDiMisura() {
		return unitaDiMisura;
	}
	public void setUnitaDiMisura(String unitaDiMisura) {
		this.unitaDiMisura = unitaDiMisura;
	}
	public String getDurataMesi() {
		return durataMesi;
	}
	public void setDurataMesi(String durataMesi) {
		this.durataMesi = durataMesi;
	}
	public String getCostiA1() {
		return costiA1;
	}
	public void setCostiA1(String costiA1) {
		this.costiA1 = costiA1;
	}
	public String getCostiA2() {
		return costiA2;
	}
	public void setCostiA2(String costiA2) {
		this.costiA2 = costiA2;
	}
	public String getCostiAs() {
		return costiAs;
	}
	public void setCostiAs(String costiAs) {
		this.costiAs = costiAs;
	}
	public String getCostoTotale() {
		return costoTotale;
	}
	public void setCostoTotale(String costoTotale) {
		this.costoTotale = costoTotale;
	}
	public String getApporto_capitale_privato() {
		return apporto_capitale_privato;
	}
	public void setApporto_capitale_privato(String apporto_capitale_privato) {
		this.apporto_capitale_privato = apporto_capitale_privato;
	}
	public String getTipologia_capitale_privato() {
		return tipologia_capitale_privato;
	}
	public void setTipologia_capitale_privato(String tipologia_capitale_privato) {
		this.tipologia_capitale_privato = tipologia_capitale_privato;
	}
	public String getDelega() {
		return delega;
	}
	public void setDelega(String delega) {
		this.delega = delega;
	}
	public String getCodiceAusa() {
		return codiceAusa;
	}
	public void setCodiceAusa(String codiceAusa) {
		this.codiceAusa = codiceAusa;
	}
	public String getDenominazioneAusa() {
		return denominazioneAusa;
	}
	public void setDenominazioneAusa(String denominazioneAusa) {
		this.denominazioneAusa = denominazioneAusa;
	}
	
}