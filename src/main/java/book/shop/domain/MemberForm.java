package book.shop.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import javax.validation.constraints.NotEmpty;
import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@FieldDefaults(level = PRIVATE)
public class MemberForm {
    @NotEmpty(message = "회원 이름은 필수입니다.")
    String name;
    String city;
    String street;
    String zipcode;
}
