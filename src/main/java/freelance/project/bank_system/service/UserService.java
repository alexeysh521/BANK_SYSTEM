package freelance.project.bank_system.service;

import freelance.project.bank_system.dto.*;
import freelance.project.bank_system.enums.AccountStatusType;
import freelance.project.bank_system.enums.RolesType;
import freelance.project.bank_system.enums.UserStatusType;
import freelance.project.bank_system.model.Account;
import freelance.project.bank_system.model.Data;
import freelance.project.bank_system.model.User;
import freelance.project.bank_system.repository.AccountRepository;
import freelance.project.bank_system.repository.DataRepository;
import freelance.project.bank_system.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final DataRepository dataRepository;

    @Transactional
    public String replaceStatusUser(ReplaceUserStatusRequest dto){
        User user = findUserById(dto.id());
        user.setStatus(dto.status());

        if(dto.status() == UserStatusType.BLOCKED)
            user.setRole(RolesType.TEMPSUSPENSION.name());

        userRepository.save(user);

        return String.format("""
                message: replace status User successfully
                user id: %s,
                user status: %s
                """, dto.id(), dto.status());
    }

    @Transactional
    public String replaceRoleUser(ReplaceRoleUserRequest dto){
        User user = findUserById(dto.id());

        String dtoRole = RolesType.fromString(dto.role());
        checkRoleAndStatusOnBlockedUser(dtoRole);

        user.setRole(dtoRole);
        userRepository.save(user);

        return String.format("""
                message: replace role User successfully
                user id: %s,
                user role: %s
                """, dto.id(), dto.role());
    }

    @Transactional
    public String bannedUser(UUID id){
        User user = findUserById(id);

        user.setRole(RolesType.BANNED.name());

        Account account = accountRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("Incorrect User id OR Account id"));
        account.setStatus(AccountStatusType.BLOCKED);
        user.setStatus(UserStatusType.BLOCKED);

        userRepository.save(user);

        return "Successfully";
    }

    @Transactional
    public String replaceAccStatus(ReplaceAccUserRequest dto) {
        Account account = accountRepository.findById(dto.account_id())
                        .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        account.setStatus(dto.status());

        accountRepository.save(account);

        return String.format("""
                message: replaced account status successfully
                account id: %s
                """, dto.account_id());
    }

    @Transactional
    public String changeUsername(String newUsername, User user) {
        user.setUsername(newUsername);
        userRepository.save(user);

        return "message: changed username successfully";
    }

    @Transactional
    public String changePassword(String newPassword, User user) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return "message: changed password successfully";
    }

    @Transactional
    public String changeUserData(ChangeUserDataRequest dto, User user){
        Data data = user.getData();

        data.setFirstName(dto.firstName() != null ? dto.firstName() : data.getFirstName());
        data.setAverageName(dto.averageName() != null ? dto.averageName() : data.getAverageName());
        data.setLastName(dto.lastName() != null ? dto.lastName() : data.getLastName());
        data.setEmail(dto.email() != null ? dto.email() : data.getEmail());
        data.setPassportSeriesAndNumber(dto.passportSeriesAndNumber() != null ?
                dto.passportSeriesAndNumber() : data.getPassportSeriesAndNumber());
        data.setDateOfBirth(dto.dateOfBirth() != null ? dto.dateOfBirth() : data.getDateOfBirth());

        dataRepository.save(data);

        return "message: changed user data successfully";
    }

    public List<UUID> viewAllAccountsUser(UUID id){
        return userRepository.findAccountByUserId(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private User findUserById(UUID id){
        return userRepository.findUserById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private void checkRoleAndStatusOnBlockedUser(String role){
        if(role.equals(RolesType.BANNED.name()))
            throw new IllegalArgumentException(
                    "message: You can block the user in a special method at the URL: admin/banned/user/{id}"
            );
    }

}
