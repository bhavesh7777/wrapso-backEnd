package wrapsto.service;


import org.springframework.http.ResponseEntity;
import wrapsto.dto.SMSDto;

public interface SendOtp {

    void sendOTP(SMSDto sms);

    ResponseEntity<String> sendNotification(String mobileNumber);

}
