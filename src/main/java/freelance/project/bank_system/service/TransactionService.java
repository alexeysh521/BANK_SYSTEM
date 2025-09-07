package freelance.project.bank_system.service;

import freelance.project.bank_system.dto.*;
import freelance.project.bank_system.enums.TransactionStatusType;
import freelance.project.bank_system.model.Account;
import freelance.project.bank_system.model.Transaction;
import freelance.project.bank_system.model.User;
import freelance.project.bank_system.repository.TransactionRepository;
import jakarta.transaction.Transactional;
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
    private final ValidationService validationService;

    @Transactional
    public TransferAccResponse createTransaction(TransferAccRequest dto, User user){
        Account fromAcc = validationService.executeAccount(dto.fromAccId());
        Account toAcc = validationService.executeAccount(dto.toAccId());

        validationService.checkOwnerForAccount(fromAcc.getUser().getId(), user.getId());
        BigDecimal amount = validationService.checkBalance(fromAcc.getBalance(), dto.amount());

        Transaction transaction = new Transaction(
                fromAcc,
                toAcc,
                amount,
                fromAcc.getCurrency()
        );

        BigDecimal convertedAmount = amount;

        if(fromAcc.getCurrency() != toAcc.getCurrency())
            convertedAmount = fromAcc.getCurrency().convert(amount, toAcc.getCurrency());

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

    public ViewAllTranByAccResponse viewAllTranByAcc(UUID account_id, UUID user_id) {
        Account account = validationService.executeAccount(account_id);
        validationService.checkOwnerForAccount(account.getUser().getId(), user_id);

        List<UUID> transactionsId = transactionRepository.findAllByAccountId(account_id);

        return new ViewAllTranByAccResponse(
                transactionsId
        );
    }

    public ViewAllTranResponse viewAll(){
        return new ViewAllTranResponse(
                transactionRepository.findAllTransactions()
        );
    }
}
