package wrapsto.service;


import org.springframework.http.ResponseEntity;
import wrapsto.dto.SMSDto;
import wrapsto.dto.VerfiyOTPDto;

import javax.servlet.http.HttpServletResponse;

public interface SendOtp {

    void sendOTP(SMSDto sms);

    ResponseEntity<String> sendNotification(String mobileNumber);

    ResponseEntity<String> verifyOTP(HttpServletResponse response, VerfiyOTPDto verfiyOTPDto);

    ResponseEntity<String> updateOtp(String mobileNumber);

}
