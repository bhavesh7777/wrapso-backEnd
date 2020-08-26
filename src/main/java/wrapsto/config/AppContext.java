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


    public String getAccountSID() {
        return accountSID;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}

