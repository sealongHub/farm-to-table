package farm_to_table.farm_to_table.Controller;

import farm_to_table.farm_to_table.DTO.Auth.AuthResponse;
import farm_to_table.farm_to_table.DTO.Login.LoginRequest;
import farm_to_table.farm_to_table.DTO.Register.RegisterRequest;
import farm_to_table.farm_to_table.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    // 1. API សម្រាប់ Register User ថ្មី
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        String response = userService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 2. API សម្រាប់ Login និងទទួលបាន JWT Token
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }
}