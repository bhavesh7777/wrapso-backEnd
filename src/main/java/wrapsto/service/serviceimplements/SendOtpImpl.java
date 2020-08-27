package wrapsto.service.serviceimplements;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wrapsto.config.AppContext;
import wrapsto.dto.SMSDto;
import wrapsto.dto.VerfiyOTPDto;
import wrapsto.exceptionhandling.Conflict;
import wrapsto.models.Users;
import wrapsto.repository.UserRepository;
import wrapsto.service.SendOtp;

import java.util.Random;

@Service
public class SendOtpImpl implements SendOtp {

    @Autowired
    AppContext appContext;

    @Autowired
    UserRepository userRepository;

    @Override
    public void sendOTP(SMSDto sms) {
        Twilio.init(appContext.getAccountSID(), appContext.getAuthToken());
        Message.creator(new PhoneNumber(sms.getTo()), new PhoneNumber(appContext.getPhoneNumber()), sms.getMessage())
                .create();

    }

    @Override
    public ResponseEntity<String> sendNotification(String mobileNumber) {
        boolean isPresent = userRepository.findById(mobileNumber).isPresent();
        if (isPresent) {
            throw new Conflict("Duplicate Entry");
        } else {
            Random random = new Random();
            int number = random.nextInt(999999);
            String otp = String.format("%06d", number);
            SMSDto sms = new SMSDto();
            sms.setTo(mobileNumber);
            sms.setMessage("Your verification code is : " + otp);
            Users users = new Users();
            users.setMobileNumber(mobileNumber);
            users.setOtp(otp);
            userRepository.save(users);
            sendOTP(sms);
            return ResponseEntity.ok().body("SMS sent successfully");
        }
    }

    @Override
    public ResponseEntity<String> verifyOTP(VerfiyOTPDto verfiyOTPDto) {
        if (userRepository.findById(verfiyOTPDto.getMobileNumber()).isPresent()) {
            Users otp = userRepository.getOne(verfiyOTPDto.getMobileNumber());
            if (otp.getOtp().equals(verfiyOTPDto.getOtp())) {
                return ResponseEntity.ok().body("OTP verified");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Verification failed");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Number not found");

    }
}



