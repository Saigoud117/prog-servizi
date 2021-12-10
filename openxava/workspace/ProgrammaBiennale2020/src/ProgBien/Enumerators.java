package ProgBien;

public class Enumerators {
	
	public enum PrioritaMotivation { CalamitaNaturali, InteressiPubblici, AcquistiAggiuntivi, FondiEuropeiPrivati };
	
	public enum Ricompreso { No, SiCuiNonAncoraAttribuitoServizi, SiCuiNonAncoraAttribuitoLavori, SiInterventiOacquistiDiversi, SiLavori, Si };
	
	public enum Settore { SelezionaUnValore, Forniture, Servizi };
	
	public enum Priorita { max, media, min };
	
	public enum Taffidamento { NuovoContratto, NuovoAffidamentoDiContrattoInEssere, AffidamentoAdUnicoOperatore };
	
	public enum VarianteValori { ModificaB, ModificaC, ModificaD, ModificaE, Modifica }; 
	
	public enum Stima { Stanziata, PerAltriAnni };
	
	public enum Base { Si, No };
	
	public enum FaseRiferimento { A, B, C };
}
