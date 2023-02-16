package shop.mtcoding.myapp.model.account;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Account {

    private Integer id;
    private String number;
    private String password;
    private Long blance;
    private Integer userId;
    private Timestamp createdAt;
}
