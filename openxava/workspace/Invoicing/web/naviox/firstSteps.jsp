<%@include file="../xava/imports.jsp"%>

<%-- To put your own text add entries in the i18n messages files of your project --%>

<%
String language = "es".equals(request.getLocale().getLanguage()) || "ca".equals(request.getLocale().getLanguage())?"es":"en";
%>

<div id="first_steps">
<!--
<p><xava:message key="first_steps_p1"/></p>
<p class="screenshot"><img src="../naviox/images/modules-screenshot_<%=language%>.png"/></p>
<p><xava:message key="first_steps_p2"/></p>
<p class="screenshot"><img src="../naviox/images/list-mode-screenshot_<%=language%>.png"/></p>
<p><xava:message key="first_steps_p3"/></p>
-->

<h2>ISTRUZIONI per inserimento PROGRAMMA BIENNALE ACQUISTI.</h2>
<h3>1.	Tabella Procedure</h3>
<p>In questa tabella, vanno inserite, per ogni procedura di acquisto programmata, tutte le informazioni previste dal regolamento del MIT - D.M. 16/01/2018, n. 14</p>
<p>In particolare:</p>
<ol>
<li>Il codice CUI da pubblicare a seguito dell'approvazione del piano, verra' determinato dal sistema. Durante questa fase preparatoria, ogni procedura verra' individuata da un codice "ridotto" costituito dalla coppia "Anno0" + "Codice Interno". Il codice interno viene attibuito sequenzialmente dal sistema.</li>
<li>CUP = Indica il CUP (cfr. articolo 6 comma 4)</li>
<li>Cui ricompreso = Selezionare da lista uno dei valori indicati "no, si, si - CUI non ancora attribuito, si - interventi o acquisti diversi". Il campo successivo dovra' essere compilato se nella colonna "Acquisto ricompreso nell'importo complessivo di un lavoro o di altra acquisizione presente in programmazione di lavori, forniture e servizi" si e' risposto "SI" e se nella colonna "Codice CUP" non e' stato riportato il CUP in quanto non presente.</li>
<li>Lotto = Indica se lotto funzionale secondo la definizione di cui all'art.3 comma 1 lettera qq) de  D.Lgs.50/2016</li>
<li>CPV = relativa a CPV principale. Il programma verifica la coerenza, per le prime due cifre, con il settore: F= CPV<45 o 48; S= CPV>48 - E' possibile effettuare una ricerca automatica digitanto parte della descrizione</li>
<li>Priorita' = Indica il livello di priorita' di cui all'articolo 6 commi 10 e 11</li>
<li>RUP = E' possibile effettuare una ricerca automatica digitanto parte del nome.</li>
<li>Tipo Affidamento: Indicare "nuovo affidamento di contratto in essere" per Servizi o forniture che presentano caratteri di regolarita' o sono destinati ad essere rinnovati entro un determinato periodo.</li>
<li>Costi antecedenti = Indicare le spese eventualmente gia' sostenute e con competenza di bilancio antecedentemente alla prima annualita' in modo che l'Importo complessivo sia conforme a quanto previsto dall'articolo 3;</li>
<li>_____ Riportare l'importo del capitale privato come quota parte dell'importo complessivo</li>
<li>Dati obbligatori per i soli acquisti ricompresi nella prima annualita' (Cfr. articolo 8)</li>		
<li>Indicare se l'acquisto e' stato aggiunto o e' stato modificato a seguito di modifica in corso d'anno ai sensi dell'art.7 commi 8 e 9. Tale campo, come la relativa nota e tabella, compaiono solo in caso di modifica del programma</li>												
<li>La somma e' calcolata al netto dell'importo degli acquisti ricompresi nell'importo complessivo di un lavoro o di altra acquisizione presente in programmazione di lavori, forniture e servizi</li>
</ol>

</div>