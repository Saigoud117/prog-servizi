<?php
if (!is_null($_POST["token"])){
	$token = base64_decode($_POST["token"]);
	//IL TOKEN PUO ESSERE CRIPTATO IN MODALITA 3-DES/ECB/NOPADDING
	/*
	$key = "chiave 24 caratteri";
	$ivkey = "dummy";
	$td = mcrypt_module_open(MCRYPT_3DES, '', MCRYPT_MODE_ECB, '');
	mcrypt_generic_init($td, $key, $ivkey);
	$token = mdecrypt_generic($td, $token);
	mcrypt_generic_deinit($td);
	mcrypt_module_close($td);
	*/
	//VERIFICARE LA FIRMA DEL TOKEN E SOLO SE PROVENIENTE DA COHESION AGGIUNGERLO IN SESSIONE (NEL CASO SIA STATO CRIPTATO QUESTO PASSO NON è NECESSARIO)
	$_SESSION['TOKEN'] = $token;
}else{
	if(!isset($_SESSION['TOKEN']))
		Header('location: http://localhost:8080/CohesionServlet/Authentication');
}
if(isset($_SESSION['TOKEN']))
	echo($_SESSION['TOKEN']);
?>