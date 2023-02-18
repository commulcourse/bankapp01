package shop.mtcoding.myapp.dto.account;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.myapp.model.account.Account;

@Setter
@Getter
public class AccountSaveReqDto {
    private String number;
    private String password;

    // insert, update 할때 필요하다.
    public Account toModel(int principalId) {
        Account account = new Account();
        account.setNumber(number);
        account.setPassword(password);
        account.setUserId(principalId);
        account.setBalance(1000L);
        return account;
    }
}
