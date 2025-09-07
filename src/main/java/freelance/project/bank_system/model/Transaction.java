package freelance.project.bank_system.model;

import freelance.project.bank_system.enums.CurrencyType;
import freelance.project.bank_system.enums.TransactionStatusType;
import freelance.project.bank_system.enums.TransactionType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "from_account_id")
    private Account fromAccountId;

    @ManyToOne
    @JoinColumn(name = "to_account_id")
    private Account toAccountId;

    @Column(nullable = false)
    private BigDecimal amount;

    private LocalDateTime date = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private CurrencyType currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_status", nullable = false)
    private TransactionStatusType transactionStatus = TransactionStatusType.PROCESSING;

    public Transaction(){}

    public Transaction(Account from, Account to, BigDecimal amount, CurrencyType currency) {
        this.toAccountId = to;
        this.fromAccountId = from;
        this.amount = amount;
        this.currency = currency;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Account getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Account to_account_id) {
        this.toAccountId = to_account_id;
    }

    public Account getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Account from_account_id) {
        this.fromAccountId = from_account_id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }

    public TransactionStatusType getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatusType transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
}
