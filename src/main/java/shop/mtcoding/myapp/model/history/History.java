package shop.mtcoding.myapp.model.history;

import java.security.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class History {

    private Integer id;
    private Integer amount;
    private Long wBlance;
    private Long dBlance;
    private String wAccountId;
    private String dAccountId;
    private Timestamp createdAt;
}
