package shop.mtcoding.myapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.myapp.dto.account.AccountDepositReqDto;
import shop.mtcoding.myapp.dto.account.AccountSaveReqDto;
import shop.mtcoding.myapp.dto.account.AccountTransferReqDto;
import shop.mtcoding.myapp.dto.account.AccountWithdrawReqDto;
import shop.mtcoding.myapp.handler.ex.CustomException;
import shop.mtcoding.myapp.model.account.Account;
import shop.mtcoding.myapp.model.account.AccountRepository;
import shop.mtcoding.myapp.model.history.History;
import shop.mtcoding.myapp.model.history.HistoryRepository;

@Service
public class AccountService {

    @Autowired
    private AccountTransferReqDto accountTransferReqDto;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Transactional
    public int 이체하기(AccountTransferReqDto accountTransferReqDto, Integer principalId) {
        // 1. 출금 계좌존재 여부
        Account wAccountPS = accountRepository.findByNumber(accountTransferReqDto.getWAccountNumber());
        if (wAccountPS == null) {
            throw new CustomException("출금 계좌가 없는데?", HttpStatus.BAD_REQUEST);
        }
        // 2. 입금 계좌존재 여부
        Account dAccountPS = accountRepository.findByNumber(accountTransferReqDto.getDAccountNumber());
        if (dAccountPS == null) {
            throw new CustomException("입금 계좌가 없는데?", HttpStatus.BAD_REQUEST);
        }

        // 3. 출금 계좌패스워드 확인
        wAccountPS.checkPassword(accountTransferReqDto.getWAccountPassword());

        // 4. 출금 잔액확인
        wAccountPS.checkBalance(accountTransferReqDto.getAmount());

        // 5. 출금계좌 소유주 확인 (로그인 한 사람)
        wAccountPS.checkOwner(principalId);

        // 6. 출금
        wAccountPS.withdraw(accountTransferReqDto.getAmount());
        accountRepository.updateById(wAccountPS);

        // 7. 입금
        dAccountPS.deposit(accountTransferReqDto.getAmount());
        accountRepository.updateById(dAccountPS);

        // 8. 히스토리 (거래내역)
        History history = new History();
        history.setAmount(accountTransferReqDto.getAmount());
        history.setWAccountId(wAccountPS.getId());
        history.setDAccountId(dAccountPS.getId());
        history.setWBalance(wAccountPS.getBalance());
        history.setDBalance(dAccountPS.getBalance());
        historyRepository.insert(history);

        // 9. 해당 계좌의 id를
        return wAccountPS.getId();
    } // 서비스 메서드 종료시에 커밋됩니다. 서비스 실행하다가 예외터지면 롤백

    @Transactional
    public void 입금하기(AccountDepositReqDto accountDepositReqDto) {
        Account accountPS = accountRepository.findByNumber(accountDepositReqDto.getDAccountNumber());
        if (accountPS == null) {
            throw new CustomException("계좌가 없는데?", HttpStatus.BAD_REQUEST);
        }

        accountPS.deposit(accountDepositReqDto.getAmount());
        accountRepository.updateById(accountPS);

        History history = new History();
        history.setAmount(accountDepositReqDto.getAmount());
        history.setWAccountId(null);
        history.setDAccountId(accountPS.getId());
        history.setWBalance(null);
        history.setDBalance(accountPS.getBalance());

        historyRepository.insert(history);
    }

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
        accountPS.checkPassword(accountWithdrawReqDto.getWAccountPassword());

        // 3.계좌잔액확인
        accountPS.checkBalance(accountWithdrawReqDto.getAmount());

        // 4.계좌출금(balance -마이너스)
        accountPS.withdraw(accountWithdrawReqDto.getAmount());
        // 기존값을 바로 select해서 전체를 업데이트 할수 있다. account.xml의 updateById로 한번에 가능함
        accountRepository.updateById(accountPS);

        // 5.히스토리 (거래내역)
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