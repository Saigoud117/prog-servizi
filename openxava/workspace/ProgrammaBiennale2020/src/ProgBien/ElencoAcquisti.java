package ProgBien;

import javax.persistence.*;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.*;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties("row_number")
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="row_number")
public class ElencoAcquisti {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long row_number;
	
	private String progressivo;
	private String stato;
	private String servizio_struttura;
	private String descrizioneStruttura;
	private String settore;
	private int primaAnnualita;
	private String codiceCui;
	private String descrizioneAcquisto;
	private String annualitaAvvioProceduraAffidamento;
	private String codiceCup;
	private String cupMaster;
	private String acquistoRicompreso;
	private String cuiRicompreso;
	private String lottoFunzionale;
	private String nuts;
	private String cpv;
	private String descrizioneCpv;
	private String livelloPriorita;
	private String cfResponsabileProcedimento;
	private String nomeResponsabileProcedimento;
	private String cognomeResponsabileProcedimento;
	private String durataMesi;
	private String quantita;
	private String unitaDiMisura;
	private String nuovoAffidamentoContrattoInEssere;
	private String affidamentoExArt63;
	private String codiceAusa;
	private String denominazioneAusa;
	private String acquistoAggiuntoVariato;
	
	//private String nuovo;
	//private String modifica;
	
	private String cancellazione;
	private String motivazionePerLaNonRiproposizione;
	private String aggregabile;
	private String motivazioniNonAggregabilita;
	private String progettoIct;
	
	private String verdi;
	private String noVerdi;
	private String oggettoAcquistiVerdi;
	private String riferimentoNormativoVerdi;
	private String cpvAcquistiVerdi;
	private String importoNettoAcquistiVerdi;
	private String aliquotaIvaAcquistiVerdi;
	private String oggettoAcquistiMaterialiRiciclati;
	private String cpvacquistiMaterialiRiciclati;
	private String importoNettoAcquistiMaterialiRiciclati;
	private String aliquotaIvaAcquistiMaterialiRiciclati;
	
	private String stanziamentiBilancioAnno1;
	private String contrazioneMutuoAnno1;
	private String finanziamentiAnno1;
	private String privatiAnno1;
	private String trasferimentoImmobiliAnno1;
	private String risorseVincolateAnno1;
	private String altraTipologiaRisorsaAnno1;
	private String totaleAnnualitaAnno1;
	private String stanziamentiBilancioAnno2;
	private String contrazioneMutuoAnno2;
	private String finanziamentiAnno2;
	private String privatiAnno2;
	private String trasferimentoImmobiliAnno2;
	private String risorseVincolateAnno2;
	private String altraTipologiaRisorsaAnno2;
	private String totaleAnnualitaAnno2;
	private String stanziamentiBilancioAs;
	private String contrazioneMutuoAs;
	private String finanziamentiAs;
	private String privatias;
	private String trasferimentoImmobiliAs;
	private String risorseVincolateAs;
	private String altraTipologiaRisorsaAs;
	private String totaleAnnualitaAs;
	private String costiA1;
	private String costiA2;
	private String costiAs;
	private String costiComplessivi;
	private String valoreStimatoAppalto;
	private String importoBaseAsta;
	private String sommeADisposizione;
	private String totaleImposte;
	private String totaleIvaQuadroEconomico;
	private String totaleQuadroEconomico;
	private String costipregressi;
	private String totaleCoperture;
	private String costoTotale;
	private String note;
	public long getRow_number() {
		return row_number;
	}
	public void setRow_number(long row_number) {
		this.row_number = row_number;
	}
	public String getProgressivo() {
		return progressivo;
	}
	public void setProgressivo(String progressivo) {
		this.progressivo = progressivo;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public String getServizio_struttura() {
		return servizio_struttura;
	}
	public void setServizio_struttura(String servizio_struttura) {
		this.servizio_struttura = servizio_struttura;
	}
	public String getDescrizioneStruttura() {
		return descrizioneStruttura;
	}
	public void setDescrizioneStruttura(String descrizioneStruttura) {
		this.descrizioneStruttura = descrizioneStruttura;
	}
	public String getSettore() {
		return settore;
	}
	public void setSettore(String settore) {
		this.settore = settore;
	}
	public int getPrimaAnnualita() {
		return primaAnnualita;
	}
	public void setPrimaAnnualita(int primaAnnualita) {
		this.primaAnnualita = primaAnnualita;
	}
	public String getCodiceCui() {
		return codiceCui;
	}
	public void setCodiceCui(String codiceCui) {
		this.codiceCui = codiceCui;
	}
	public String getDescrizioneAcquisto() {
		return descrizioneAcquisto;
	}
	public void setDescrizioneAcquisto(String descrizioneAcquisto) {
		this.descrizioneAcquisto = descrizioneAcquisto;
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
	public String getCupMaster() {
		return cupMaster;
	}
	public void setCupMaster(String cupMaster) {
		this.cupMaster = cupMaster;
	}
	public String getAcquistoRicompreso() {
		return acquistoRicompreso;
	}
	public void setAcquistoRicompreso(String acquistoRicompreso) {
		this.acquistoRicompreso = acquistoRicompreso;
	}
	public String getCuiRicompreso() {
		return cuiRicompreso;
	}
	public void setCuiRicompreso(String cuiRicompreso) {
		this.cuiRicompreso = cuiRicompreso;
	}
	public String getLottoFunzionale() {
		return lottoFunzionale;
	}
	public void setLottoFunzionale(String lottoFunzionale) {
		this.lottoFunzionale = lottoFunzionale;
	}
	public String getNuts() {
		return nuts;
	}
	public void setNuts(String nuts) {
		this.nuts = nuts;
	}
	public String getCpv() {
		return cpv;
	}
	public void setCpv(String cpv) {
		this.cpv = cpv;
	}
	public String getDescrizioneCpv() {
		return descrizioneCpv;
	}
	public void setDescrizioneCpv(String descrizioneCpv) {
		this.descrizioneCpv = descrizioneCpv;
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
	public String getDurataMesi() {
		return durataMesi;
	}
	public void setDurataMesi(String durataMesi) {
		this.durataMesi = durataMesi;
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
	
	public String getNuovoAffidamentoContrattoInEssere() {
		return nuovoAffidamentoContrattoInEssere;
	}
	public void setNuovoAffidamentoContrattoInEssere(String nuovoAffidamentoContrattoInEssere) {
		this.nuovoAffidamentoContrattoInEssere = nuovoAffidamentoContrattoInEssere;
	}
	public String getAffidamentoExArt63() {
		return affidamentoExArt63;
	}
	public void setAffidamentoExArt63(String affidamentoExArt63) {
		this.affidamentoExArt63 = affidamentoExArt63;
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
	/*
	public String getNuovo() {
		return nuovo;
	}
	public void setNuovo(String nuovo) {
		this.nuovo = nuovo;
	}
	public String getModifica() {
		return modifica;
	}
	public void setModifica(String modifica) {
		this.modifica = modifica;
	}
	*/
	public String getCancellazione() {
		return cancellazione;
	}
	public void setCancellazione(String cancellazione) {
		this.cancellazione = cancellazione;
	}
	public String getMotivazionePerLaNonRiproposizione() {
		return motivazionePerLaNonRiproposizione;
	}
	public void setMotivazionePerLaNonRiproposizione(String motivazionePerLaNonRiproposizione) {
		this.motivazionePerLaNonRiproposizione = motivazionePerLaNonRiproposizione;
	}
	public String getAcquistoAggiuntoVariato() {
		return acquistoAggiuntoVariato;
	}
	public void setAcquistoAggiuntoVariato(String acquistoAggiuntoVariato) {
		this.acquistoAggiuntoVariato = acquistoAggiuntoVariato;
	}
	public String getAggregabile() {
		return aggregabile;
	}
	public void setAggregabile(String aggregabile) {
		this.aggregabile = aggregabile;
	}
	public String getMotivazioniNonAggregabilita() {
		return motivazioniNonAggregabilita;
	}
	public void setMotivazioniNonAggregabilita(String motivazioniNonAggregabilita) {
		this.motivazioniNonAggregabilita = motivazioniNonAggregabilita;
	}
	public String getProgettoIct() {
		return progettoIct;
	}
	public void setProgettoIct(String progettoIct) {
		this.progettoIct = progettoIct;
	}
	public String getStanziamentiBilancioAnno1() {
		return stanziamentiBilancioAnno1;
	}
	public void setStanziamentiBilancioAnno1(String stanziamentiBilancioAnno1) {
		this.stanziamentiBilancioAnno1 = stanziamentiBilancioAnno1;
	}
	public String getContrazioneMutuoAnno1() {
		return contrazioneMutuoAnno1;
	}
	public void setContrazioneMutuoAnno1(String contrazioneMutuoAnno1) {
		this.contrazioneMutuoAnno1 = contrazioneMutuoAnno1;
	}
	public String getFinanziamentiAnno1() {
		return finanziamentiAnno1;
	}
	public void setFinanziamentiAnno1(String finanziamentiAnno1) {
		this.finanziamentiAnno1 = finanziamentiAnno1;
	}
	public String getPrivatiAnno1() {
		return privatiAnno1;
	}
	public void setPrivatiAnno1(String privatiAnno1) {
		this.privatiAnno1 = privatiAnno1;
	}
	public String getTrasferimentoImmobiliAnno1() {
		return trasferimentoImmobiliAnno1;
	}
	public void setTrasferimentoImmobiliAnno1(String trasferimentoImmobiliAnno1) {
		this.trasferimentoImmobiliAnno1 = trasferimentoImmobiliAnno1;
	}
	public String getRisorseVincolateAnno1() {
		return risorseVincolateAnno1;
	}
	public void setRisorseVincolateAnno1(String risorseVincolateAnno1) {
		this.risorseVincolateAnno1 = risorseVincolateAnno1;
	}
	public String getAltraTipologiaRisorsaAnno1() {
		return altraTipologiaRisorsaAnno1;
	}
	public void setAltraTipologiaRisorsaAnno1(String altraTipologiaRisorsaAnno1) {
		this.altraTipologiaRisorsaAnno1 = altraTipologiaRisorsaAnno1;
	}
	public String getTotaleAnnualitaAnno1() {
		return totaleAnnualitaAnno1;
	}
	public void setTotaleAnnualitaAnno1(String totaleAnnualitaAnno1) {
		this.totaleAnnualitaAnno1 = totaleAnnualitaAnno1;
	}
	public String getStanziamentiBilancioAnno2() {
		return stanziamentiBilancioAnno2;
	}
	public void setStanziamentiBilancioAnno2(String stanziamentiBilancioAnno2) {
		this.stanziamentiBilancioAnno2 = stanziamentiBilancioAnno2;
	}
	public String getContrazioneMutuoAnno2() {
		return contrazioneMutuoAnno2;
	}
	public void setContrazioneMutuoAnno2(String contrazioneMutuoAnno2) {
		this.contrazioneMutuoAnno2 = contrazioneMutuoAnno2;
	}
	public String getFinanziamentiAnno2() {
		return finanziamentiAnno2;
	}
	public void setFinanziamentiAnno2(String finanziamentiAnno2) {
		this.finanziamentiAnno2 = finanziamentiAnno2;
	}
	public String getPrivatiAnno2() {
		return privatiAnno2;
	}
	public void setPrivatiAnno2(String privatiAnno2) {
		this.privatiAnno2 = privatiAnno2;
	}
	public String getTrasferimentoImmobiliAnno2() {
		return trasferimentoImmobiliAnno2;
	}
	public void setTrasferimentoImmobiliAnno2(String trasferimentoImmobiliAnno2) {
		this.trasferimentoImmobiliAnno2 = trasferimentoImmobiliAnno2;
	}
	public String getRisorseVincolateAnno2() {
		return risorseVincolateAnno2;
	}
	public void setRisorseVincolateAnno2(String risorseVincolateAnno2) {
		this.risorseVincolateAnno2 = risorseVincolateAnno2;
	}
	public String getAltraTipologiaRisorsaAnno2() {
		return altraTipologiaRisorsaAnno2;
	}
	public void setAltraTipologiaRisorsaAnno2(String altraTipologiaRisorsaAnno2) {
		this.altraTipologiaRisorsaAnno2 = altraTipologiaRisorsaAnno2;
	}
	public String getTotaleAnnualitaAnno2() {
		return totaleAnnualitaAnno2;
	}
	public void setTotaleAnnualitaAnno2(String totaleAnnualitaAnno2) {
		this.totaleAnnualitaAnno2 = totaleAnnualitaAnno2;
	}
	public String getStanziamentiBilancioAs() {
		return stanziamentiBilancioAs;
	}
	public void setStanziamentiBilancioAs(String stanziamentiBilancioAs) {
		this.stanziamentiBilancioAs = stanziamentiBilancioAs;
	}
	public String getContrazioneMutuoAs() {
		return contrazioneMutuoAs;
	}
	public void setContrazioneMutuoAs(String contrazioneMutuoAs) {
		this.contrazioneMutuoAs = contrazioneMutuoAs;
	}
	public String getFinanziamentiAs() {
		return finanziamentiAs;
	}
	public void setFinanziamentiAs(String finanziamentiAs) {
		this.finanziamentiAs = finanziamentiAs;
	}
	public String getPrivatias() {
		return privatias;
	}
	public void setPrivatias(String privatias) {
		this.privatias = privatias;
	}
	public String getTrasferimentoImmobiliAs() {
		return trasferimentoImmobiliAs;
	}
	public void setTrasferimentoImmobiliAs(String trasferimentoImmobiliAs) {
		this.trasferimentoImmobiliAs = trasferimentoImmobiliAs;
	}
	public String getRisorseVincolateAs() {
		return risorseVincolateAs;
	}
	public void setRisorseVincolateAs(String risorseVincolateAs) {
		this.risorseVincolateAs = risorseVincolateAs;
	}
	public String getAltraTipologiaRisorsaAs() {
		return altraTipologiaRisorsaAs;
	}
	public void setAltraTipologiaRisorsaAs(String altraTipologiaRisorsaAs) {
		this.altraTipologiaRisorsaAs = altraTipologiaRisorsaAs;
	}
	public String getTotaleAnnualitaAs() {
		return totaleAnnualitaAs;
	}
	public void setTotaleAnnualitaAs(String totaleAnnualitaAs) {
		this.totaleAnnualitaAs = totaleAnnualitaAs;
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
	public String getCostiComplessivi() {
		return costiComplessivi;
	}
	public void setCostiComplessivi(String costiComplessivi) {
		this.costiComplessivi = costiComplessivi;
	}
	public String getValoreStimatoAppalto() {
		return valoreStimatoAppalto;
	}
	public void setValoreStimatoAppalto(String valoreStimatoAppalto) {
		this.valoreStimatoAppalto = valoreStimatoAppalto;
	}
	public String getImportoBaseAsta() {
		return importoBaseAsta;
	}
	public void setImportoBaseAsta(String importoBaseAsta) {
		this.importoBaseAsta = importoBaseAsta;
	}
	public String getSommeADisposizione() {
		return sommeADisposizione;
	}
	public void setSommeADisposizione(String sommeADisposizione) {
		this.sommeADisposizione = sommeADisposizione;
	}
	public String getTotaleImposte() {
		return totaleImposte;
	}
	public void setTotaleImposte(String totaleImposte) {
		this.totaleImposte = totaleImposte;
	}
	public String getTotaleIvaQuadroEconomico() {
		return totaleIvaQuadroEconomico;
	}
	public void setTotaleIvaQuadroEconomico(String totaleIvaQuadroEconomico) {
		this.totaleIvaQuadroEconomico = totaleIvaQuadroEconomico;
	}
	public String getTotaleQuadroEconomico() {
		return totaleQuadroEconomico;
	}
	public void setTotaleQuadroEconomico(String totaleQuadroEconomico) {
		this.totaleQuadroEconomico = totaleQuadroEconomico;
	}
	public String getCostipregressi() {
		return costipregressi;
	}
	public void setCostipregressi(String costipregressi) {
		this.costipregressi = costipregressi;
	}
	public String getTotaleCoperture() {
		return totaleCoperture;
	}
	public void setTotaleCoperture(String totaleCoperture) {
		this.totaleCoperture = totaleCoperture;
	}
	public String getCostoTotale() {
		return costoTotale;
	}
	public void setCostoTotale(String costoTotale) {
		this.costoTotale = costoTotale;
	}
	public String getVerdi() {
		return verdi;
	}
	public void setVerdi(String verdi) {
		this.verdi = verdi;
	}
	public String getNoVerdi() {
		return noVerdi;
	}
	public void setNoVerdi(String noVerdi) {
		this.noVerdi = noVerdi;
	}
	public String getOggettoAcquistiVerdi() {
		return oggettoAcquistiVerdi;
	}
	public void setOggettoAcquistiVerdi(String oggettoAcquistiVerdi) {
		this.oggettoAcquistiVerdi = oggettoAcquistiVerdi;
	}
	public String getRiferimentoNormativoVerdi() {
		return riferimentoNormativoVerdi;
	}
	public void setRiferimentoNormativoVerdi(String riferimentoNormativoVerdi) {
		this.riferimentoNormativoVerdi = riferimentoNormativoVerdi;
	}
	public String getCpvAcquistiVerdi() {
		return cpvAcquistiVerdi;
	}
	public void setCpvAcquistiVerdi(String cpvAcquistiVerdi) {
		this.cpvAcquistiVerdi = cpvAcquistiVerdi;
	}
	public String getImportoNettoAcquistiVerdi() {
		return importoNettoAcquistiVerdi;
	}
	public void setImportoNettoAcquistiVerdi(String importoNettoAcquistiVerdi) {
		this.importoNettoAcquistiVerdi = importoNettoAcquistiVerdi;
	}
	public String getAliquotaIvaAcquistiVerdi() {
		return aliquotaIvaAcquistiVerdi;
	}
	public void setAliquotaIvaAcquistiVerdi(String aliquotaIvaAcquistiVerdi) {
		this.aliquotaIvaAcquistiVerdi = aliquotaIvaAcquistiVerdi;
	}
	public String getOggettoAcquistiMaterialiRiciclati() {
		return oggettoAcquistiMaterialiRiciclati;
	}
	public void setOggettoAcquistiMaterialiRiciclati(String oggettoAcquistiMaterialiRiciclati) {
		this.oggettoAcquistiMaterialiRiciclati = oggettoAcquistiMaterialiRiciclati;
	}
	public String getCpvacquistiMaterialiRiciclati() {
		return cpvacquistiMaterialiRiciclati;
	}
	public void setCpvacquistiMaterialiRiciclati(String cpvacquistiMaterialiRiciclati) {
		this.cpvacquistiMaterialiRiciclati = cpvacquistiMaterialiRiciclati;
	}
	public String getImportoNettoAcquistiMaterialiRiciclati() {
		return importoNettoAcquistiMaterialiRiciclati;
	}
	public void setImportoNettoAcquistiMaterialiRiciclati(String importoNettoAcquistiMaterialiRiciclati) {
		this.importoNettoAcquistiMaterialiRiciclati = importoNettoAcquistiMaterialiRiciclati;
	}
	public String getAliquotaIvaAcquistiMaterialiRiciclati() {
		return aliquotaIvaAcquistiMaterialiRiciclati;
	}
	public void setAliquotaIvaAcquistiMaterialiRiciclati(String aliquotaIvaAcquistiMaterialiRiciclati) {
		this.aliquotaIvaAcquistiMaterialiRiciclati = aliquotaIvaAcquistiMaterialiRiciclati;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}	
}