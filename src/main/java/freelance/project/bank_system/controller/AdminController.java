package freelance.project.bank_system.controller;

import freelance.project.bank_system.dto.*;
import freelance.project.bank_system.service.AccountService;
import freelance.project.bank_system.service.TransactionService;
import freelance.project.bank_system.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

//✅ ❌ @NotNull(message = "This field must not be empty")

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;
    private final AccountService accountService;
    private final TransactionService transactionService;

    //посмотреть все операции аккаунта конкретного пользователя ✅
    @PostMapping("/view/all_operation/by/user")
    public ResponseEntity<?> viewAllOpByUser(@RequestBody @Valid UUIDIdOperationRequest dto){
        return ResponseEntity.ok(transactionService.viewAllTranByAcc(dto.account_id(), dto.user_id()));
    }

    //посмотреть все операции, отсортированные по времени ✅
    @GetMapping("/view/all/transactions")
    public ResponseEntity<?> viewAllTransactions(){
        return ResponseEntity.ok(transactionService.viewAll());
    }

    //сменить роль пользователю ✅
    @PostMapping("/replace/role/user")
    public ResponseEntity<?> replaceRoleUser(@RequestBody ReplaceRoleUserRequest dto){
        return ResponseEntity.ok(userService.replaceRoleUser(dto));
    }

    //сменить статус пользователя ✅
    @PostMapping("/replace/status_user")
    public ResponseEntity<?> replaceStatusUser(@RequestBody @Valid ReplaceUserStatusRequest dto){
        return ResponseEntity.ok(userService.replaceStatusUser(dto));
    }

    //сменить статус аккаунта пользователя ✅
    @PostMapping("/replace/status_acc")
    public ResponseEntity<?> replaceAccStatus(@RequestBody @Valid ReplaceAccUserRequest dto){
        return ResponseEntity.ok(userService.replaceAccStatus(dto));
    }

    //посмотреть данные пользователя ❌                                                                                  NOT REALIZED
    @PostMapping("/view/data/user/{id}")
    public ResponseEntity<?> viewDataUser(@PathVariable UUID id){
        return ResponseEntity.ok().build();
    }

    //посмотреть инфу транзакции ✅
    @PostMapping("/view/data/transaction/by/{id}")
    public ResponseEntity<?> viewDataTransaction(@PathVariable UUID id){
        return ResponseEntity.ok(transactionService.viewAllById(id));
    }

    //посмотреть данные аккаунта пользователя ✅
    @PostMapping("/view/data/account/{id}")
    public ResponseEntity<?> viewDataAccount(@PathVariable UUID id){
        return ResponseEntity.ok(accountService.findById(id));
    }

    //посмотреть все аккаунты пользователя ✅
    @PostMapping("/view/all/accounts/user/{id}")
    public ResponseEntity<?> viewAllAccountsUser(@PathVariable UUID id){
        return ResponseEntity.ok(userService.viewAllAccountsUser(id));
    }

    //создать новый аккаунт пользователю ✅
    @PostMapping("/create/account/for/user")
    public ResponseEntity<?> createAccount(@RequestBody @Valid CreateAccAnAdminRequest dto){
        return ResponseEntity.ok(accountService.creatingAnAccByAnAdmin(dto));
    }
}
