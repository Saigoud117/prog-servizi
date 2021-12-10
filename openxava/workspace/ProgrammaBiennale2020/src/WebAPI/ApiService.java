package WebAPI;

import EsportazioneMitProg.*;
import retrofit2.*;
import retrofit2.http.*;

public interface ApiService {
	@Headers({
		"Content-Type: application/x-www-form-urlencoded",
		"Accept: application/json",
		"Accept-Encoding: gzip"})
	@POST("/WSLoginCollaudo/rest/Account/LoginPubblica")
	public Call<Login> WsLogin(@Query("username")String username, @Query("password")String password, 
			@Query("clientId")String clientId, @Query(value = "clientKey", encoded = true)String clientKey);
	
	@Headers({
		"Content-Type: application/json",
		"Accept: application/json",
		"Accept-Encoding: gzip, deflate, br"
		})
	@POST("/WSProgrammiCollaudo/rest/Programmi/PubblicaFornitureServizi")
	public Call<PubblicazioneResponse> PubblicaFornitureServizi(@Query("token")String token, @Query("modalitaInvio")String modalitaInvio, @Body PubblicazioneFornitureServizi body);
	
	@Headers({
		"Content-Type: application/x-www-form-urlencoded",
		"Accept: application/json",
		"Accept-Encoding: gzip"})
	@POST("/WSLogin/rest/Account/LoginPubblica")
	public Call<Login> WsLoginProduzione(@Query("username")String username, @Query(value = "password", encoded = true)String password, 
			@Query("clientId")String clientId, @Query(value = "clientKey", encoded = true)String clientKey);
	
	@Headers({
		"Content-Type: application/json",
		"Accept: application/json",
		"Accept-Encoding: gzip, deflate, br"
		})
	@POST("/WSProgrammi/rest/Programmi/PubblicaFornitureServizi")
	public Call<PubblicazioneResponse> PubblicaFornitureServiziProduzione(@Query("token")String token, @Query("modalitaInvio")String modalitaInvio, @Body PubblicazioneFornitureServizi body);
}
