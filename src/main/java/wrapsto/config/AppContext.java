package wrapsto.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppContext {


    @Value("${twilio.accountSID}")
    private String accountSID;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.phoneNumber}")
    private String phoneNumber;

    @Value("${secretKey}")
    private String secretKey;

    @Value("${accessTokenValidity}")
    private String accessTokenValidity;

    @Value("${refreshTokenValidity}")
    private String refreshTokenValidity;


    public String getAccountSID() {
        return accountSID;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setAccountSID(String accountSID) {
        this.accountSID = accountSID;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(String accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public String getRefreshTokenValidity() {
        return refreshTokenValidity;
    }

    public void setRefreshTokenValidity(String refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }
}

