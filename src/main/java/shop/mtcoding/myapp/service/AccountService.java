package shop.mtcoding.myapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.myapp.dto.account.AccountSaveReqDto;
import shop.mtcoding.myapp.model.account.Account;
import shop.mtcoding.myapp.model.account.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public void 계좌생성(AccountSaveReqDto accountSaveReqDto, int principalId) {
        Account accout = accountSaveReqDto.toModel(principalId);
        accountRepository.insert(accout);
    }
}
