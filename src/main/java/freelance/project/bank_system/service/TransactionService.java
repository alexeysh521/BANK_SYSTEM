package freelance.project.bank_system.service;

import freelance.project.bank_system.dto.TransferAccRequest;
import freelance.project.bank_system.dto.TransferAccResponse;
import freelance.project.bank_system.dto.ViewAllTranByAccRequest;
import freelance.project.bank_system.dto.ViewAllTranByAccResponse;
import freelance.project.bank_system.enums.TransactionStatusType;
import freelance.project.bank_system.model.Account;
import freelance.project.bank_system.model.Transaction;
import freelance.project.bank_system.model.User;
import freelance.project.bank_system.repository.AccountRepository;
import freelance.project.bank_system.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public TransferAccResponse createTransaction(TransferAccRequest dto, User user){
        Account fromAcc = executeAccount(dto.fromAccId());
        Account toAcc = executeAccount(dto.toAccId());

        checkOwnerForAccount(fromAcc.getUser().getId(), user.getId());
        BigDecimal amount = checkBalance(fromAcc.getBalance(), dto.amount());

        Transaction transaction = new Transaction(
                fromAcc,
                toAcc,
                amount,
                fromAcc.getCurrency()
        );

        BigDecimal convertedAmount = amount;

        if(fromAcc.getCurrency() != toAcc.getCurrency()){
            convertedAmount = fromAcc.getCurrency().convert(amount, toAcc.getCurrency());
        }

        fromAcc.setBalance(fromAcc.getBalance().subtract(amount));
        toAcc.setBalance(toAcc.getBalance().add(convertedAmount));

        transaction.setTransactionStatus(TransactionStatusType.SUCCESSFULLY);

        transactionRepository.save(transaction);

        return new TransferAccResponse(
                "Successfully",
                fromAcc.getBalance(),
                fromAcc.getCurrency()
        );
    }

    public ViewAllTranByAccResponse viewAllTranByAcc(ViewAllTranByAccRequest dto, User user) {
        Account account = executeAccount(dto.account_id());
        checkOwnerForAccount(account.getUser().getId(), user.getId());

        List<UUID> transactionsId = transactionRepository.findAllByAccountId(dto.account_id());

        if(transactionsId.isEmpty())
            throw new EntityNotFoundException("You have no transactions");

        return new ViewAllTranByAccResponse(
                transactionsId
        );
    }



    private BigDecimal checkBalance(BigDecimal balance, BigDecimal amount){
        if(balance.compareTo(amount) < 0)
            throw new IllegalArgumentException("There are not enough funds in your account");
        return amount;
    }

    private Account executeAccount(UUID accountId){
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found " + accountId));
    }

    private void checkOwnerForAccount(UUID account_user_id, UUID user_id){
        if(!account_user_id.equals(user_id))
            throw new IllegalArgumentException("You are not the owner of this account");
    }
}
