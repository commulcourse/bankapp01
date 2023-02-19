package shop.mtcoding.myapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.myapp.dto.account.AccountSaveReqDto;
import shop.mtcoding.myapp.dto.account.AccountWithdrawReqDto;
import shop.mtcoding.myapp.handler.ex.CustomException;
import shop.mtcoding.myapp.model.account.Account;
import shop.mtcoding.myapp.model.account.AccountRepository;
import shop.mtcoding.myapp.model.history.History;
import shop.mtcoding.myapp.model.history.HistoryRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Transactional
    public void 계좌생성(AccountSaveReqDto accountSaveReqDto, int principalId) {
        Account account = accountSaveReqDto.toModel(principalId);
        accountRepository.insert(account);
    }

    @Transactional
    public int 계좌출금(AccountWithdrawReqDto accountWithdrawReqDto) {
        // 1.계좌존재여부확인
        Account accountPS = accountRepository.findByNumber((accountWithdrawReqDto.getWAccountNumber()));
        if (accountPS == null) {
            throw new CustomException("계좌가 없는뎀?", HttpStatus.BAD_REQUEST);
        }
        // 2.계좌비밀번호확인
        if (accountPS.getPassword().equals(accountWithdrawReqDto.getWAccountPassword())) {
            throw new CustomException("출금계좌 비밀번호가 틀렸다!!", HttpStatus.BAD_REQUEST);
        }
        // 3.계좌잔액확인
        if (accountPS.getBalance() < accountWithdrawReqDto.getAmount()) {
            throw new CustomException("잔액이 부족해 흑흑!", HttpStatus.BAD_REQUEST);
        }
        // 4.계좌출금(balance -마이너스)
        // accountPS에서 가져온 금액에서 - 출금금액만큼 뺀 후 accountPS.setBalance로 넣어주겠다.
        accountPS.setBalance(accountPS.getBalance() -
                accountWithdrawReqDto.getAmount());
        // 기존값을 바로 select해서 전체를 업데이트 할수 있다. account.xml의 updateById로 한번에 가능함
        accountRepository.updateById(accountPS);
        // 5.히스토리 (거래내역) //영상 12강 출금하기, 45분쯤부터 보면됨
        History history = new History();
        history.setAmount(accountWithdrawReqDto.getAmount());
        history.setWAccountId(null);
        history.setDAccountId(null);
        history.setWBalance(accountPS.getBalance());
        history.setDBalance(null);

        historyRepository.insert(history);

        return accountPS.getId();
    }
}