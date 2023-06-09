package kr.co.popool.bblmember.service.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Getter
public class PhoneNumber {

    private static final String DELIMITER = "-";
    private static final String EMPTY = "";

    @ApiModelProperty(example = "010-XXXX-XXXX")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Builder
    public PhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PhoneNumber toCompactNumber() {
        // ex) 01012341234
        return new PhoneNumber(phoneNumber.replaceAll(DELIMITER, EMPTY));
    }

    public PhoneNumber toFullNumber() {
        if (phoneNumber.contains(DELIMITER)) {
            return this;
        }

        final int len = phoneNumber.length();
        StringBuilder sb = new StringBuilder(phoneNumber);
        int frontIndex = 0;
        int backIndex = 0;

        if (len == 11) { // ex) 01012341234 -> 010-1234-1234
            frontIndex = 3;
            backIndex = 8;
        } else { // ex) 0212341234 -> 02-1234-1234
            frontIndex = 2;
            backIndex = 7;
        }

        sb.insert(frontIndex, DELIMITER);
        sb.insert(backIndex, DELIMITER);
        return new PhoneNumber(sb.toString());
    }

    @Override
    public String toString() {
        return phoneNumber + "";
    }
}
