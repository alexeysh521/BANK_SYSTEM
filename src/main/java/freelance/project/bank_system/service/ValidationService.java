package freelance.project.bank_system.service;

import freelance.project.bank_system.enums.TransactionType;
import freelance.project.bank_system.enums.UserStatusType;
import freelance.project.bank_system.model.Account;
import freelance.project.bank_system.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ValidationService {

    private final AccountRepository accountRepository;

    public Account executeAccount(UUID accountId){
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found " + accountId));
    }

    public void validateUserStatusForTransactionBegin(UserStatusType userStatus, TransactionType typeTran){
        if(typeTran == TransactionType.TRANSFER && userStatus == UserStatusType.LIMITED_ACCESS)
            throw new IllegalArgumentException("To send money, you need to verify your details");
        if(userStatus == UserStatusType.BLOCKED)
            throw new IllegalArgumentException("You are blocked and cannot send, withdraw or deposit money");
    }

    public void checkOwnerForAccount(UUID account_user_id, UUID user_id){
        if(!account_user_id.equals(user_id))
            throw new IllegalArgumentException("You are not the owner of this account");
    }

    public void checkBalanceV(BigDecimal balance, BigDecimal amount){
        if(balance.compareTo(amount) < 0)
            throw new IllegalArgumentException("There are not enough funds in your account");
    }

    public BigDecimal checkBalance(BigDecimal balance, BigDecimal amount){
        if(balance.compareTo(amount) < 0)
            throw new IllegalArgumentException("There are not enough funds in your account");
        return amount;
    }
}
