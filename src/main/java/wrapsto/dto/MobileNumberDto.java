package wrapsto.dto;

import javax.validation.constraints.NotBlank;

public class MobileNumberDto {
    @NotBlank(message = "Mobile number is mandatory")
    String mobileNumber;

    public String getMobileNumber() {


        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
