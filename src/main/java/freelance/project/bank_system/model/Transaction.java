package freelance.project.bank_system.model;

import freelance.project.bank_system.enums.CurrencyType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    private UUID id = UUID.randomUUID();

    @ManyToOne
    private Account from_account_id;

    @ManyToOne
    private Account to_account_id;

    @Column(nullable = false)
    private BigDecimal amount;

    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private CurrencyType currency;

    public Transaction(){}

    public Transaction(Account to, Account from, BigDecimal amount, CurrencyType currency) {
        this.to_account_id = to;
        this.from_account_id = from;
        this.amount = amount;
        this.currency = currency;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Account getTo_account_id() {
        return to_account_id;
    }

    public void setTo_account_id(Account to_account_id) {
        this.to_account_id = to_account_id;
    }

    public Account getFrom_account_id() {
        return from_account_id;
    }

    public void setFrom_account_id(Account from_account_id) {
        this.from_account_id = from_account_id;
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
}
