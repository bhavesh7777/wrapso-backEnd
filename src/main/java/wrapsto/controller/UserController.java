package wrapsto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import wrapsto.dto.MobileNumberDto;
import wrapsto.dto.VerfiyOTPDto;
import wrapsto.exceptionhandling.BadRequest;
import wrapsto.service.serviceimplements.SendOtpImpl;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin(allowCredentials = "true")
public class UserController {

    @Autowired
    SendOtpImpl sendOtp;

    @PostMapping("otp")
    public ResponseEntity<String> loginSendOtp(@Validated @RequestBody MobileNumberDto mobileNumber, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequest("mobile number is mandatory");
        }
        return sendOtp.sendNotification(mobileNumber.getMobileNumber());
    }

    @PutMapping("otp")
    public ResponseEntity<String> verifyOtp(@Validated @RequestBody VerfiyOTPDto  verfiyOTPDto){

        return sendOtp.verifyOTP(verfiyOTPDto);
    }

}
