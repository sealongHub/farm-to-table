package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.UserDetail.UserDetailRequest;
import farm_to_table.farm_to_table.model.UserDetailEntity;

public interface UserDetailService {

    UserDetailEntity create(UserDetailRequest request);

    UserDetailEntity update(Long id, UserDetailRequest request);

    UserDetailEntity getByUserId(String userId);

    String delete(Long id);
}