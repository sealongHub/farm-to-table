package farm_to_table.farm_to_table.Controller;

import farm_to_table.farm_to_table.DTO.UserDetail.UserDetailRequest;
import farm_to_table.farm_to_table.Service.UserDetailService;
import farm_to_table.farm_to_table.model.UserDetailEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user_detail")
@RequiredArgsConstructor
public class UserDetailController {

    private final UserDetailService userDetailService;

    @PostMapping
    public ResponseEntity<UserDetailEntity> create(@RequestBody UserDetailRequest request) {
        return new ResponseEntity<>(userDetailService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDetailEntity> update(@PathVariable Long id,
                                                   @RequestBody UserDetailRequest request) {
        return ResponseEntity.ok(userDetailService.update(id, request));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDetailEntity> getByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(userDetailService.getByUserId(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(userDetailService.delete(id));
    }
}