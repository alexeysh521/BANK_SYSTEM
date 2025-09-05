package freelance.project.bank_system.service;

import freelance.project.bank_system.dto.CreateAccAnAdminRequest;
import freelance.project.bank_system.dto.DataAccountResponse;
import freelance.project.bank_system.dto.NewAccountRequest;
import freelance.project.bank_system.dto.NewAccountResponse;
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

    //@Transactional сначана тест без аннотации
    public Object findById(UUID id){
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Account not found"));

        return convertTo(account);
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

    @Transactional
    public NewAccountResponse createAccount(NewAccountRequest dto, User userReq){
        User user = userRepository.findUserById(userReq.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Account account = new Account(
                BigDecimal.ZERO,
                dto.currency(),
                user
        );

        accountRepository.save(account);

        return new NewAccountResponse(
                account.getId(),
                user.getId(),
                "Account created successfully"
        );
    }

    private DataAccountResponse convertTo(Account account){
        return new DataAccountResponse(
                account.getId(),
                account.getUser().getId(),
                account.getBalance(),
                account.getCurrency(),
                account.getStatus(),
                account.getRegistrationDate()
        );
    }
}
