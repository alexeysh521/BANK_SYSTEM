package freelance.project.bank_system.service;

import freelance.project.bank_system.dto.*;
import freelance.project.bank_system.enums.AccountStatusType;
import freelance.project.bank_system.enums.CurrencyType;
import freelance.project.bank_system.enums.TransactionType;
import freelance.project.bank_system.model.Account;
import freelance.project.bank_system.model.User;
import freelance.project.bank_system.repository.AccountRepository;
import freelance.project.bank_system.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final ValidationService validationService;

    public DataAccountResponse findById(UUID id){
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Account not found"));

        return new DataAccountResponse(
                account.getId(),
                account.getUser().getId(),
                account.getBalance(),
                account.getCurrency(),
                account.getStatus(),
                account.getRegistrationDate()
        );
    }

    @Transactional
    public DepOrWithAccResponse deposit(DepOrWithAccRequest dto, User user){
        Account account = checkExecuteAndOwnerForAccount(dto.account_id(), user.getId());
        validationService.validateUserStatusForTransactionBegin(user.getStatus(), TransactionType.DEPOSIT);

        BigDecimal depositAmount = dto.currency().convert(dto.amount(), account.getCurrency());
        account.setBalance(account.getBalance().add(depositAmount));

        accountRepository.save(account);

        return new DepOrWithAccResponse(
                "successfully",
                account.getBalance(),
                account.getCurrency()
        );
    }

    @Transactional
    public DepOrWithAccResponse withdraw(DepOrWithAccRequest dto, User user){
        Account account = checkExecuteAndOwnerForAccount(dto.account_id(), user.getId());
        validationService.validateUserStatusForTransactionBegin(user.getStatus(), TransactionType.WITHDRAW);

        BigDecimal withAmount = dto.currency().convert(dto.amount(), account.getCurrency());
        validationService.checkBalanceV(account.getBalance(), withAmount);
        account.setBalance(account.getBalance().subtract(withAmount));

        accountRepository.save(account);

        return new DepOrWithAccResponse(
                "successfully",
                account.getBalance(),
                account.getCurrency()
        );
    }

    public List<UUID> findAllByUser(User user){
        return accountRepository.findAllByUserId(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Transactional
    public UUID creatingAnAccByAnAdmin(CreateAccAnAdminRequest dto){

        User user = userRepository.findUserById(dto.user_id())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Account account = new Account(
                dto.balance(),
                dto.currency(),
                user
        );

        return accountRepository.save(account).getId();
    }

    public String checkBalance(User user, UUID accountId){
        Account account = validationService.executeAccount(accountId);
        validationService.checkOwnerForAccount(account.getUser().getId(), user.getId());

        return String.format("""
                Balance: %.2f
                Currency: %s
                """, account.getBalance(), account.getCurrency());
    }

    @Transactional
    public String closeAccount(UUID account_id, UUID user_id){
        Account account = validationService.executeAccount(account_id);
        validationService.checkOwnerForAccount(account.getUser().getId(), user_id);

        account.setStatus(AccountStatusType.CLOSED);
        accountRepository.save(account);

        return "Account closed successfully";
    }

    @Transactional
    public String createAccount(CurrencyType currency, User userReq){
        User user = userRepository.findUserById(userReq.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Account account = new Account(
                BigDecimal.ZERO,
                currency,
                user
        );

        accountRepository.save(account);

        return "Account created successfully";
    }

    private Account checkExecuteAndOwnerForAccount(UUID acc_id, UUID user_id){
        Account account = validationService.executeAccount(acc_id);
        validationService.checkOwnerForAccount(account.getUser().getId(), user_id);

        return account;
    }
}
