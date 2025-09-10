package freelance.project.bank_system.service;

import freelance.project.bank_system.dto.*;
import freelance.project.bank_system.enums.TransactionStatusType;
import freelance.project.bank_system.enums.TransactionType;
import freelance.project.bank_system.model.Account;
import freelance.project.bank_system.model.Transaction;
import freelance.project.bank_system.model.User;
import freelance.project.bank_system.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
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
    public String createTransaction(TransferAccRequest dto, User user){
        Account fromAcc = validationService.executeAccount(dto.fromAccId());
        Account toAcc = validationService.executeAccount(dto.toAccId());

        TransactionType typeTran = TransactionType.TRANSFER;

        validationService.checkOwnerForAccount(fromAcc.getUser().getId(), user.getId());
        validationService.validateUserStatusForTransactionBegin(user.getStatus(), typeTran, fromAcc.getStatus());
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

        return String.format("""
                message: successfully,
                balance: %.2f,
                currency: %s
                """, fromAcc.getBalance(), fromAcc.getCurrency());
    }

    public TransactionInfoResponse viewAllById(UUID id){
        Transaction t = transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found"));

        return new TransactionInfoResponse(
                t.getFromAccountId().getId(),
                t.getToAccountId().getId(),
                t.getAmount(),
                t.getDate(),
                t.getCurrency(),
                t.getTransactionStatus()
        );
    }

    public List<UUID> viewAllTranByAcc(UUID account_id, UUID user_id) {
        Account account = validationService.executeAccount(account_id);
        validationService.checkOwnerForAccount(account.getUser().getId(), user_id);

        return transactionRepository.findAllByAccountId(account_id);
    }

    public List<UUID> viewAll(){
        return transactionRepository.findAllTransactions();
    }
}
