package shop.mtcoding.myapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import shop.mtcoding.myapp.handler.ex.CustomException;

@Controller
public class AccountController {

    @GetMapping({ "/", "main" })
    public String main() {
        throw new CustomException("인증되지 않았습니다", HttpStatus.UNAUTHORIZED);
        // return "account/main";
    }

    @GetMapping("/account/{id}")
    public String detail(@PathVariable int id) {
        return "account/detail";
    }

    @GetMapping("/account/saveForm")
    public String saveForm() {
        return "account/saveForm";
    }

    @GetMapping("/account/withdrawForm")
    public String withdrawForm() {
        return "account/withdrawForm";
    }

    @GetMapping("/account/transterForm")
    public String transterForm() {
        return "account/transterForm";
    }

    @GetMapping("/account/depositForm")
    public String depositForm() {
        return "account/depositForm";
    }

}
