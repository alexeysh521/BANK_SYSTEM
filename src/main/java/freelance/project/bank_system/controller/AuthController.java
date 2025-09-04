package freelance.project.bank_system.controller;


import freelance.project.bank_system.dto.LoginRequestDto;
import freelance.project.bank_system.dto.RegisterRequestDto;
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
    public ResponseEntity<?> login(@RequestBody LoginRequestDto dto){
        return ResponseEntity.ok(authService.login(dto.username(), dto.password()));
    }

    @PostMapping("/api/auth/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequestDto dto){
        return ResponseEntity.ok(authService.register(dto.username(), dto.role(), dto.password()));
    }

}
