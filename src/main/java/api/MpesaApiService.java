package api;

import model.OAuthTokenResponse;
import model.StkPushRequest;
import model.StkPushResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface MpesaApiService {
    @FormUrlEncoded
    @POST("oauth/v1/generate?grant_type=client_credentials")
    Call<OAuthTokenResponse> getOAuthToken(
            @Header("Authorization") String auth
    );

    @POST("mpesa/stkpush/v1/processrequest")
    Call<StkPushResponse> initiateStkPush(
            @Header("Authorization") String auth,
            @Body StkPushRequest stkPushRequest
    );
}
