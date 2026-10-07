package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.Auth.AuthResponse;
import farm_to_table.farm_to_table.DTO.Login.LoginRequest;
import farm_to_table.farm_to_table.DTO.Register.RegisterRequest;
import farm_to_table.farm_to_table.model.UserEntity;

public interface UserService {
    // Method សម្រាប់ Register
    String register(RegisterRequest request);

    // Method សម្រាប់ Login (ដោយប្រើ username និង password)
    AuthResponse login(LoginRequest request);

    // Method សម្រាប់ទាញយក User តាម Username
    UserEntity findByUsername(String username);
}