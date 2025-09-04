package freelance.project.bank_system.controller;

import freelance.project.bank_system.dto.*;
import freelance.project.bank_system.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class ConsumerController {

    //создание нового аккаунта
    @PostMapping("/new/account")
    public ResponseEntity<?> newAccount(@RequestBody @Valid NewAccountDto dto,
                                        @AuthenticationPrincipal User user){
        return ResponseEntity.ok().build();
    }

    //просмотреть все свои аккаунты
    @GetMapping("/view/all/my_accounts")
    public ResponseEntity<?> viewAllAccounts(@AuthenticationPrincipal User user){
        return ResponseEntity.ok().build();
    }

    //проверка баланса
    @GetMapping("/check/balance")
    public ResponseEntity<?> checkBalance(@AuthenticationPrincipal User user){
        return ResponseEntity.ok().build();
    }

    //посмотреть все транзакции на аккаунте
    @PostMapping("/view/all_transactions/on_your_account")
    public ResponseEntity<?> viewAllTransactionsByAccount(@RequestBody @Valid ViewAllTranByAccDto dto,
            @AuthenticationPrincipal User user){
        return ResponseEntity.ok().build();
    }

    //пополнить баланс
    @PostMapping("/balance/operation/deposit")
    public ResponseEntity<?> deposit(@RequestBody @Valid DepositAccDto dto,
                                     @AuthenticationPrincipal User user){
        return ResponseEntity.ok().build();
    }

    //снять деньги
    @PostMapping("/balance/operation/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody @Valid WithdrawAccDto dto,
                                      @AuthenticationPrincipal User user){
        return ResponseEntity.ok().build();
    }

    //перевести деньги
    @PostMapping("/balance/operation/transfer")
    public ResponseEntity<?> transfer(@RequestBody @Valid TransferAccDto dto,
                                      @AuthenticationPrincipal User user){
        return ResponseEntity.ok().build();
    }

    //сменить логин
    @PatchMapping("/account/change/username")
    public ResponseEntity<?> changeUsername(@RequestBody @Valid ChangeUsernameDto dto,
                                            @AuthenticationPrincipal User user){
        return ResponseEntity.ok().build();
    }

    //сменить пароль
    @PatchMapping("/account/change/password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePasswordDto dto,
                                            @AuthenticationPrincipal User user){
        return ResponseEntity.ok().build();
    }

    //изменить данные
    @PatchMapping("/account/to_change/user_data")
    public ResponseEntity<?> toChangeUserData(@RequestBody @Valid ChangeUserDataDto dto,
                                              @AuthenticationPrincipal User user){
        return ResponseEntity.ok().build();
    }

    //закрыть аккаунт
    @PostMapping("/account/closed")
    public ResponseEntity<?> closedAccount(@RequestBody @Valid ClosedAccountDto dto,
                                           @AuthenticationPrincipal User user){
        return ResponseEntity.ok().build();
    }
}
