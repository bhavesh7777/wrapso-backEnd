package wrapsto.service.serviceimplements;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import wrapsto.config.AppContext;
import wrapsto.config.Constants;
import wrapsto.dto.SMSDto;
import wrapsto.dto.VerfiyOTPDto;
import wrapsto.models.Users;
import wrapsto.repository.UserRepository;
import wrapsto.security.TokenVerification;
import wrapsto.service.SendOtp;

import javax.servlet.http.HttpServletResponse;
import java.util.Random;

@Service
public class SendOtpImpl implements SendOtp {

    @Autowired
    AppContext appContext;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenVerification tokenVerification;

    @Override
    public void sendOTP(SMSDto sms) {
        Twilio.init(appContext.getAccountSID(), appContext.getAuthToken());
        Message.creator(new PhoneNumber(sms.getTo()), new PhoneNumber(appContext.getPhoneNumber()), sms.getMessage())
                .create();

    }

    @Override
    public ResponseEntity<String> updateOtp(String mobileNumber) {
        Random random = new Random();
        int number = random.nextInt(999999);
        String otp = String.format("%06d", number);
        SMSDto sms = new SMSDto();
        sms.setTo(mobileNumber);
        sms.setMessage("Your verification code is : " + otp);
        Users users = new Users();
        users.setMobileNumber(mobileNumber);
        users.setOtp(otp);
        try{
        userRepository.save(users);
        sendOTP(sms);}
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Constants.TRY_AGAIN);
        }
        return ResponseEntity.ok(Constants.SMS_SENT_SUCCESSFULLY);
    }

    @Override
    public ResponseEntity<String> sendNotification(String mobileNumber) {
        boolean isPresent = userRepository.findById(mobileNumber).isPresent();
        if (isPresent) {
            return updateOtp(mobileNumber);

        } else {
            updateOtp(mobileNumber);
            return ResponseEntity.ok().body(Constants.USER_ADDED_AND_SMS_SENT_SUCCESSFULLY);
        }
    }

    @Override
    public ResponseEntity<String> verifyOTP(HttpServletResponse response,VerfiyOTPDto verfiyOTPDto) {
        if (userRepository.findById(verfiyOTPDto.getMobileNumber()).isPresent()) {
            Users otp = userRepository.getOne(verfiyOTPDto.getMobileNumber());
            if (otp.getOtp().equals(verfiyOTPDto.getOtp())) {
                String accessToken=tokenVerification.accessRefreshToken(verfiyOTPDto.getMobileNumber(), Long.parseLong(appContext.getAccessTokenValidity()));
                String refreshToken=tokenVerification.accessRefreshToken(verfiyOTPDto.getMobileNumber(),Long.parseLong(appContext.getRefreshTokenValidity()));
                tokenVerification.setCookie(response,"AccessToken",accessToken,"/user");
                tokenVerification.setCookie(response,"RefreshToken",refreshToken,"/user");
                return ResponseEntity.ok().body(Constants.OTP_VERIFIED);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Constants.VERIFICATION_FAILED);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Constants.NUMBER_NOT_FOUND);

    }
}



