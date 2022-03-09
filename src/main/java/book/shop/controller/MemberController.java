package book.shop.controller;

import book.shop.domain.Address;
import book.shop.domain.Member;
import book.shop.domain.MemberForm;
import book.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping(value = "/new")
    public String createForm(final Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping(value = "/new")
    public String create(@Valid final MemberForm memberForm, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "members/createMemberForm";
        Member member = new Member();
        member.setName(memberForm.getName());
        member.setAddress(new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode()));

        this.memberService.join(member);
        return "redirect:/";
    }

    @GetMapping(value = "")
    public String list(final Model model) {
        List<Member> members = this.memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

}
