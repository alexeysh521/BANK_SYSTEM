package freelance.project.bank_system.service;

import freelance.project.bank_system.dto.LoginResponse;
import freelance.project.bank_system.dto.RegisterResponse;
import freelance.project.bank_system.enums.CurrencyType;
import freelance.project.bank_system.model.Account;
import freelance.project.bank_system.model.User;
import freelance.project.bank_system.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationProvider authenticationProvider;
    private final UserDetailsService userDetailsService;
    private final AccountService accountService;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public LoginResponse login(String username, String password){
        try {

            authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(
                    username,
                    password
            ));

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            String token = jwtService.generatedToken(userDetails);

            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            String role = authorities.iterator().next().getAuthority().substring(5);

            return new LoginResponse(
                    token,
                    role,
                    "Successful",
                    Instant.now()
            );
        }catch (BadCredentialsException e) {
            log.error("Invalid username or password");
            throw new BadCredentialsException("Invalid username or password");
        }catch (AuthenticationException e) {
            log.error("Authentication failed for user: {}", username, e);
            throw new AuthenticationServiceException("Authentication failed", e);
        }
    }

    @Transactional
    public RegisterResponse register(String username, String role, String password){
        if(existsUser(username))
            throw new IllegalArgumentException("Such a user already exists");

        User user = new User();
        user.setUsername(username);
        user.setRole(role);
        user.setPassword(encoder.encode(password));

        User savedUser = userRepository.save(user);

        Account account = new Account(
                BigDecimal.ZERO,
                CurrencyType.RUB,
                savedUser
        );

        savedUser.getAccounts().add(account);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String token = jwtService.generatedToken(userDetails);

        log.info("Registered {} authorities: {}", username, role);

        return new RegisterResponse(
                token,
                savedUser.getId(),
                username,
                Instant.now()
        );
    }

    private boolean existsUser(String username){
        return userRepository.existsUserByUsername(username);
    }

}
