package shop.mtcoding.myapp.model.history;

import java.security.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class History {

    private Integer id;
    private Long amount;
    private Long wBalance;
    private Long dBalance;
    private Integer wAccountId;
    private Integer dAccountId;
    private Timestamp createdAt;

}
