package book.shop.api;

import book.shop.domain.Address;
import book.shop.domain.Member;
import book.shop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping(value = "/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid final Member member) {
        final Long id = this.memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping(value = "/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid final CreateMemberRequest memberRequest) {
        final Member member = new Member();
        member.setAddress(memberRequest.getAddress());
        member.setName(memberRequest.getName());
        final Long id = this.memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @Data
    static class CreateMemberRequest {
        @NotEmpty
        private String name;
        private Address address;
    }

    @Data
    @AllArgsConstructor
    static class CreateMemberResponse {
        private Long id;
    }
}
