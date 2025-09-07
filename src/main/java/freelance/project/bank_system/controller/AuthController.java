package freelance.project.bank_system.controller;


import freelance.project.bank_system.dto.LoginRequest;
import freelance.project.bank_system.dto.RegisterRequest;
import freelance.project.bank_system.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest dto){
        return ResponseEntity.ok(authService.login(dto.username(), dto.password()));
    }

    @PostMapping("/api/auth/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest dto){
        return ResponseEntity.ok(authService.register(dto.username(), dto.role(), dto.password()));
    }

}
