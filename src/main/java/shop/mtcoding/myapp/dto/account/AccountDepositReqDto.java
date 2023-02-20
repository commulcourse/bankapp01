package shop.mtcoding.myapp.dto.account;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountDepositReqDto {
    private Long amount;
    private String dAccountNumber;
}
