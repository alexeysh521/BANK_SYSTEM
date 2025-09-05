package freelance.project.bank_system.service;

import freelance.project.bank_system.dto.*;
import freelance.project.bank_system.enums.RolesType;
import freelance.project.bank_system.enums.UserStatusType;
import freelance.project.bank_system.model.Account;
import freelance.project.bank_system.model.User;
import freelance.project.bank_system.repository.AccountRepository;
import freelance.project.bank_system.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    //ДОПОЛНЕНИЕ: если пользователь ввел дополнительные данные, его статус ACTIVE
    @Transactional
    public ReplaceStatusUserResponse replaceStatusUser(ReplaceUserStatusRequest dto){
        User user = userRepository.findUserById(dto.id())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setStatus(dto.status());

        if(dto.status() == UserStatusType.BLOCKED)
            user.setRole(RolesType.BANNED.name());

        userRepository.save(user);

        return new ReplaceStatusUserResponse(
                dto.id(),
                dto.status(),
                "Replace status User successfully"
        );
    }

    @Transactional
    public ReplaceRoleUserResponse replaceRoleUser(ReplaceRoleUserRequest dto){
        User user = userRepository.findUserById(dto.id())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setRole(RolesType.fromString(dto.role()));

        userRepository.save(user);

        return new ReplaceRoleUserResponse(
                dto.id(),
                dto.role(),
                "Replace role User successfully"
        );
    }

    @Transactional
    public ReplaceAccUserResponse replaceAccStatus(ReplaceAccUserRequest dto) {
        User user = userRepository.findUserById(dto.user_id())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Account account = accountRepository.findByUser(user)
                .stream()
                .filter(acc -> acc.getId().equals(dto.acc_id()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Incorrect User id OR Account id"));

        account.setStatus(dto.status());

        accountRepository.save(account);

        return new ReplaceAccUserResponse(
                dto.user_id(),
                dto.acc_id(),
                "Replaced account status successfully"
        );
    }

    public List<UUID> viewAllAccountsUser(UUID id){
        return userRepository.findAccountByUserId(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
