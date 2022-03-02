package book.shop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemberForm {
    @NotEmpty(message = "회원 이름은 필수입니다.")
    String name;
    String city;
    String street;
    String zipcode;
}
