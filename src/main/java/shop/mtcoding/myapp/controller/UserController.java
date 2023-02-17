package shop.mtcoding.myapp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.myapp.dto.account.AccountSaveReqDto;
import shop.mtcoding.myapp.dto.user.JoinReqDto;
import shop.mtcoding.myapp.dto.user.LoginReqDto;
import shop.mtcoding.myapp.handler.ex.CustomException;
import shop.mtcoding.myapp.model.user.User;
import shop.mtcoding.myapp.model.user.UserRepository;
import shop.mtcoding.myapp.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession session;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(LoginReqDto loginReqDto) {
        if (loginReqDto.getUsername() == null || loginReqDto.getUsername().isEmpty()) {
            throw new CustomException("username을 입력하세요", HttpStatus.BAD_REQUEST);
        }
        if (loginReqDto.getPassword() == null || loginReqDto.getPassword().isEmpty()) {
            throw new CustomException("password를 입력하세요", HttpStatus.BAD_REQUEST);
        }

        User principal = userRepository.findByUsernameAndPassword(loginReqDto);
        // User principal = new User();
        // principal.setId(1);
        // principal.setUsername("ssar");
        if (principal != null) {
            throw new CustomException("아이디 혹은 비번이 틀렸습니다.", HttpStatus.BAD_REQUEST);
        }
        session.setAttribute("principal", principal);

        return "redirect:/";
    }

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

    @PostMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

}
