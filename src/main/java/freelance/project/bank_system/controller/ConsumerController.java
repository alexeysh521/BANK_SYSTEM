package freelance.project.bank_system.controller;

import freelance.project.bank_system.dto.*;
import freelance.project.bank_system.enums.CurrencyType;
import freelance.project.bank_system.model.User;
import freelance.project.bank_system.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/users")
@PreAuthorize("hasAnyRole('ADMIN', 'USER', 'MANAGER')")
@RequiredArgsConstructor
public class ConsumerController {

    private final AccountService accountService;
    private final TransactionService transactionService;
    private final DataService dataService;
    private final UserService userService;

    //create new account
    @PostMapping("/new/account/{currency}")
    public ResponseEntity<?> newAccount(@PathVariable CurrencyType currency,
                                        @AuthenticationPrincipal User user){
        return ResponseEntity.ok(accountService.createAccount(currency, user));
    }

    //view all my accounts
    @GetMapping("/view/all/my/accounts")
    public ResponseEntity<?> viewAllAccounts(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(accountService.findAllByUser(user));
    }

    //checking balance
    @GetMapping("/check/balance/{account_id}")
    public ResponseEntity<?> checkBalance(@PathVariable UUID account_id,
                                          @AuthenticationPrincipal User user){
        return ResponseEntity.ok(accountService.checkBalance(user, account_id));
    }

    //view all transaction on account
    @GetMapping("/view/all/transactions/my/account/{account_id}")
    public ResponseEntity<?> viewAllTransactionsByAccount(@PathVariable UUID account_id,
                                                          @AuthenticationPrincipal User user){
        return ResponseEntity.ok(transactionService.viewAllTranByAcc(account_id, user.getId()));
    }

    //deposit
    @PostMapping("/balance/operation/deposit")
    public ResponseEntity<?> deposit(@RequestBody @Valid DepOrWithAccRequest dto,
                                     @AuthenticationPrincipal User user){
        return ResponseEntity.ok(accountService.deposit(dto, user));
    }

    //withdraw money
    @PostMapping("/balance/operation/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody @Valid DepOrWithAccRequest dto,
                                      @AuthenticationPrincipal User user){
        return ResponseEntity.ok(accountService.withdraw(dto, user));
    }

    //transfer money
    @PostMapping("/balance/operation/transfer")
    public ResponseEntity<?> transfer(@RequestBody @Valid TransferAccRequest dto,
                                      @AuthenticationPrincipal User user){
        return ResponseEntity.ok(transactionService.createTransaction(dto, user));
    }

    //change username
    @PatchMapping("/account/change/username")
    public ResponseEntity<?> changeUsername(@RequestBody @Valid ChangeUsernameRequest dto,
                                            @AuthenticationPrincipal User user){
        return ResponseEntity.ok(userService.changeUsername(dto.newUsername(), user));
    }

    //change password
    @PatchMapping("/account/change/password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePasswordRequest dto,
                                            @AuthenticationPrincipal User user){
        return ResponseEntity.ok(userService.changePassword(dto.newPassword(), user));
    }

    //add data
    @PostMapping("/account/add/data")
    public ResponseEntity<?> addData(@RequestBody @Valid AddDataRequest dto,
                                     @AuthenticationPrincipal User user){
        return ResponseEntity.ok(dataService.create(dto, user));
    }

    //change data
    @PatchMapping("/account/change/user/data")
    public ResponseEntity<?> changeUserData(@RequestBody @Valid ChangeUserDataRequest dto,
                                              @AuthenticationPrincipal User user){
        return ResponseEntity.ok(userService.changeUserData(dto, user));
    }

    //closed account
    @PatchMapping("/close/your/account/{account_id}")
    public ResponseEntity<?> closeAccount(@PathVariable UUID account_id,
                                           @AuthenticationPrincipal User user){
        return ResponseEntity.ok(accountService.closeAccount(account_id, user.getId()));
    }
}
