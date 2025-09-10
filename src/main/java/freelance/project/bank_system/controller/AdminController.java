package freelance.project.bank_system.controller;

import freelance.project.bank_system.dto.*;
import freelance.project.bank_system.service.AccountService;
import freelance.project.bank_system.service.DataService;
import freelance.project.bank_system.service.TransactionService;
import freelance.project.bank_system.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
public class AdminController {

    private final UserService userService;
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final DataService dataService;

    //view all operations of a specific user's account
    @PostMapping("/view/all_operation/by/user")
    public ResponseEntity<?> viewAllOpByUser(@RequestBody @Valid UUIDIdOperationRequest dto){
        return ResponseEntity.ok(transactionService.viewAllTranByAcc(dto.account_id(), dto.user_id()));
    }

    //view all operations sorted by time
    @GetMapping("/view/all/transactions")
    public ResponseEntity<?> viewAllTransactions(){
        return ResponseEntity.ok(transactionService.viewAll());
    }

    //change the user's role
    @PatchMapping("/replace/role/user")
    public ResponseEntity<?> replaceRoleUser(@RequestBody ReplaceRoleUserRequest dto){
        return ResponseEntity.ok(userService.replaceRoleUser(dto));
    }

    //banned user by id
    @PatchMapping("/banned/user/{id}")
    public ResponseEntity<?> banned(@PathVariable UUID id){
        return ResponseEntity.ok(userService.bannedUser(id));
    }

    //change user status
    @PatchMapping("/replace/status/user")
    public ResponseEntity<?> replaceStatusUser(@RequestBody @Valid ReplaceUserStatusRequest dto){
        return ResponseEntity.ok(userService.replaceStatusUser(dto));
    }

    //change the status of a user's account
    @PatchMapping("/replace/status/account")
    public ResponseEntity<?> replaceAccStatus(@RequestBody @Valid ReplaceAccUserRequest dto){
        return ResponseEntity.ok(userService.replaceAccStatus(dto));
    }

    //view user data
    @GetMapping("/view/data/user/{id}")
    public ResponseEntity<?> viewDataUser(@PathVariable UUID id){
        return ResponseEntity.ok(dataService.viewDataUserById(id));
    }

    //view transaction data
    @GetMapping("/view/data/transaction/by/{id}")
    public ResponseEntity<?> viewDataTransaction(@PathVariable UUID id){
        return ResponseEntity.ok(transactionService.viewAllById(id));
    }

    //view user account information
    @GetMapping("/view/data/account/{id}")
    public ResponseEntity<?> viewDataAccount(@PathVariable UUID id){
        return ResponseEntity.ok(accountService.findById(id));
    }

    //view all accounts user
    @GetMapping("/view/all/accounts/user/{id}")
    public ResponseEntity<?> viewAllAccountsUser(@PathVariable UUID id){
        return ResponseEntity.ok(userService.viewAllAccountsUser(id));
    }

    //create new account for user
    @PostMapping("/create/account/for/user")
    public ResponseEntity<?> createAccount(@RequestBody @Valid CreateAccAnAdminRequest dto){
        return ResponseEntity.ok(accountService.creatingAnAccByAnAdmin(dto));
    }
}
