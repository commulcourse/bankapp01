package shop.mtcoding.myapp.model.account;

import java.sql.Timestamp;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.myapp.handler.ex.CustomException;

@Setter
@Getter
public class Account {

    private Integer id;
    private String number;
    private String password;
    private Long balance;
    private Integer userId;
    private Timestamp createdAt;

    public void checkPassword(String password) {
        if (!this.password.equals(password)) {
            throw new CustomException("출금계좌 비밀번호가 틀렸다!!", HttpStatus.BAD_REQUEST);
        }
    }

    public void checkBalance(long amount) {
        if (this.balance < amount) {
            throw new CustomException("잔액이 부족한데?!", HttpStatus.BAD_REQUEST);
        }
    }

    public void withdraw(long amount) {
        // accountPS에서 가져온 금액에서 - 출금금액만큼 뺀 후 accountPS.setBalance로 넣어주겠다.
        this.balance = this.balance - amount;
    }

}
