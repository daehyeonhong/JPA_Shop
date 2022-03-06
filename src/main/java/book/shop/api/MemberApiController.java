package book.shop.api;

import book.shop.domain.Address;
import book.shop.domain.Member;
import book.shop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PutMapping(value = "/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable final Long id,
                                               @RequestBody @Valid UpdateMemberRequest memberRequest) {
        this.memberService.update(id, memberRequest.getName(), memberRequest.getAddress());
        final Member member = this.memberService.findOne(id);
        return new UpdateMemberResponse(member.getId(), member.getName(), member.getAddress());
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

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
        private Address address;
    }

    @Data
    static class UpdateMemberRequest {
        @NotEmpty
        private String name;
        private Address address;
    }
}
