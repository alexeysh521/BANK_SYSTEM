package freelance.project.bank_system.service;

import freelance.project.bank_system.dto.CheckBalanceResponse;
import freelance.project.bank_system.model.Account;
import freelance.project.bank_system.model.User;
import freelance.project.bank_system.repository.AccountRepository;
import freelance.project.bank_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsumerService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public CheckBalanceResponse checkBalance(User user, UUID accountId){
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new UsernameNotFoundException("Account not found"));

        if(!account.getUser().getId().equals(user.getId()))
            throw new IllegalArgumentException("User is not the owner");

        return new CheckBalanceResponse(
                account.getBalance()
        );
    }

}
