package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.UserDetail.UserDetailRequest;
import farm_to_table.farm_to_table.Repository.UserDetailRepository;
import farm_to_table.farm_to_table.model.UserDetailEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailService {

    private final UserDetailRepository userDetailRepository;

    @Override
    @Transactional
    public UserDetailEntity create(UserDetailRequest r) {
        log.info("Create user detail for userId: {}", r.userId());
        return userDetailRepository.insertWithProcedure(
                r.userId(),
                r.nationality(),
                r.cardId(),
                r.address(),
                r.dateOfBirth(),
                r.placeOfBirth(),
                r.maritalStatus(),
                r.userDetailId()
        );
    }

    @Override
    @Transactional
    public UserDetailEntity update(Long id, UserDetailRequest r) {
        log.info("Update user detail id: {}", id);
        return userDetailRepository.updateWithProcedure(
                        id,
                        r.nationality(),
                        r.cardId(),
                        r.address(),
                        r.dateOfBirth(),
                        r.placeOfBirth(),
                        r.maritalStatus()
                )
                .orElseThrow(() -> new RuntimeException("រកមិនឃើញ User Detail id: " + id));
    }

    @Override
    public UserDetailEntity getByUserId(String userId) {
        return userDetailRepository.findByUserIdWithProcedure(userId)
                .orElseThrow(() -> new RuntimeException("រកមិនឃើញ User Detail របស់ userId: " + userId));
    }

    @Override
    @Transactional
    public String delete(Long id) {
        int deleted = userDetailRepository.deleteWithProcedure(id);
        if (deleted == 0) {
            throw new RuntimeException("រកមិនឃើញ User Detail id: " + id);
        }
        return "លុបបានជោគជ័យ!";
    }
}