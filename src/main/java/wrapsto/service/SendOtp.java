package wrapsto.service;


import org.springframework.http.ResponseEntity;
import wrapsto.dto.SMSDto;
import wrapsto.dto.VerfiyOTPDto;

public interface SendOtp {

    void sendOTP(SMSDto sms);

    ResponseEntity<String> sendNotification(String mobileNumber);

    ResponseEntity<String> verifyOTP(VerfiyOTPDto verfiyOTPDto);

}
