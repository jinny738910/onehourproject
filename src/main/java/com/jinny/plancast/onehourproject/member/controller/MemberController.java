package com.jinny.plancast.onehourproject.member.controller;

import com.jinny.plancast.onehourproject.member.controller.dto.JoinRequest;
import com.jinny.plancast.onehourproject.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/hello")
    public String gethello(){
        return "Hello One Hour Project";
    }

    @PostMapping("/join")
    public String join(@RequestBody JoinRequest joinRequest) {

        String result = memberService.join(joinRequest);

        if(result.equals("success")){
            return "success";
        } else {
            return "fail";
        }
    }

}
