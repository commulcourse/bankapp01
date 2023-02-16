package shop.mtcoding.myapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.myapp.dto.user.JoinReqDto;
import shop.mtcoding.myapp.handler.ex.CustomException;
import shop.mtcoding.myapp.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/join")
    public String join(JoinReqDto joinReqDto) {
        if (joinReqDto.getUsername() == null || joinReqDto.getUsername().isEmpty()) {
            throw new CustomException("username을 입력하세요", HttpStatus.BAD_REQUEST);
        }
        if (joinReqDto.getPassword() == null || joinReqDto.getPassword().isEmpty()) {
            throw new CustomException("password를 입력하세요", HttpStatus.BAD_REQUEST);
        }
        if (joinReqDto.getFullname() == null || joinReqDto.getFullname().isEmpty()) {
            throw new CustomException("fullname를 입력하세요", HttpStatus.BAD_REQUEST);
        }
        userService.회원가입(joinReqDto);

        return "redirect:/loginForm";
    }

    @PostMapping("/login")
    public String login() {
        return "user/login";
    }

    @PostMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

}
