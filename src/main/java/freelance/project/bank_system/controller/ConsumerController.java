package freelance.project.bank_system.controller;

import freelance.project.bank_system.dto.*;
import freelance.project.bank_system.model.User;
import freelance.project.bank_system.service.AccountService;
import freelance.project.bank_system.service.ConsumerService;
import freelance.project.bank_system.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

//✅ ❌ @NotNull(message = "This field must not be empty")

@RestController
@RequestMapping("/users")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
@RequiredArgsConstructor
public class ConsumerController {

    private final ConsumerService consumerService;
    private final AccountService accountService;
    private final TransactionService transactionService;

    //создание нового аккаунта ✅
    @PostMapping("/new/account")
    public ResponseEntity<?> newAccount(@RequestBody @Valid NewAccountRequest dto,
                                        @AuthenticationPrincipal User user){
        return ResponseEntity.ok(accountService.createAccount(dto, user));
    }

    //просмотреть все свои аккаунты ✅
    @GetMapping("/view/all/my_accounts")
    public ResponseEntity<?> viewAllAccounts(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(accountService.findAllByUser(user));
    }

    //проверка баланса ✅
    @PostMapping("/check/balance")
    public ResponseEntity<?> checkBalance(@RequestBody @Valid CheckBalanceRequest dto,
                                          @AuthenticationPrincipal User user){
        return ResponseEntity.ok(consumerService.checkBalance(user, dto.account_id()));
    }

    //посмотреть все транзакции на аккаунте ✅
    @PostMapping("/view/all_transactions/my_account")
    public ResponseEntity<?> viewAllTransactionsByAccount(@RequestBody @Valid ViewAllTranByAccRequest dto,
            @AuthenticationPrincipal User user){
        return ResponseEntity.ok(transactionService.viewAllTranByAcc(dto.account_id(), user.getId()));
    }

    //пополнить баланс ✅
    @PostMapping("/balance/operation/deposit")
    public ResponseEntity<?> deposit(@RequestBody @Valid DepOrWithAccRequest dto,
                                     @AuthenticationPrincipal User user){
        return ResponseEntity.ok(accountService.deposit(dto, user));
    }

    //снять деньги ✅
    @PostMapping("/balance/operation/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody @Valid DepOrWithAccRequest dto,
                                      @AuthenticationPrincipal User user){
        return ResponseEntity.ok(accountService.withdraw(dto, user));
    }

    //перевести деньги ✅
    @PostMapping("/operation/transfer")
    public ResponseEntity<?> transfer(@RequestBody @Valid TransferAccRequest dto,
                                      @AuthenticationPrincipal User user){
        return ResponseEntity.ok(transactionService.createTransaction(dto, user));
    }

    //сменить логин                                                                                                      NOT REALIZED
    @PatchMapping("/account/change/username")
    public ResponseEntity<?> changeUsername(@RequestBody @Valid ChangeUsernameDto dto,
                                            @AuthenticationPrincipal User user){
        return ResponseEntity.ok().build();
    }

    //сменить пароль                                                                                                     NOT REALIZED
    @PatchMapping("/account/change/password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePasswordDto dto,
                                            @AuthenticationPrincipal User user){
        return ResponseEntity.ok().build();
    }

    //изменить данные                                                                                                    NOT REALIZED
    @PatchMapping("/account/to_change/user_data")
    public ResponseEntity<?> toChangeUserData(@RequestBody @Valid ChangeUserDataDto dto,
                                              @AuthenticationPrincipal User user){
        return ResponseEntity.ok().build();
    }

    //закрыть аккаунт ✅
    @PostMapping("/close/your/account")
    public ResponseEntity<?> closeAccount(@RequestBody @Valid ClosedAccRequest dto,
                                           @AuthenticationPrincipal User user){
        return ResponseEntity.ok(accountService.closeAccount(dto.account_id(), user.getId()));
    }
}
