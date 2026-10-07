package farm_to_table.farm_to_table.Repository;

import farm_to_table.farm_to_table.model.UserDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;


@Repository
public interface UserDetailRepository extends JpaRepository<UserDetailEntity, Long> {

    Optional<UserDetailEntity> findByUserId(Long userId);

    @Query(value = """
            SELECT * FROM sp_insert_user_detail(
                :userId, :nationality, :cardId, :address,
                CAST(:dateOfBirth AS date), :placeOfBirth, :maritalStatus, :userDetailId)
            """, nativeQuery = true)
    UserDetailEntity insertWithProcedure(
            @Param("userId") String userId,
            @Param("nationality") String nationality,
            @Param("cardId") String cardId,
            @Param("address") String address,
            @Param("dateOfBirth") LocalDate dateOfBirth,
            @Param("placeOfBirth") String placeOfBirth,
            @Param("maritalStatus") String maritalStatus,
            @Param("userDetailId") String userDetailId);

    @Query(value = """
            SELECT * FROM sp_update_user_detail(
                :id, :nationality, :cardId, :address,
                CAST(:dateOfBirth AS date), :placeOfBirth, :maritalStatus)
            """, nativeQuery = true)
    Optional<UserDetailEntity> updateWithProcedure(
            @Param("id") Long id,
            @Param("nationality") String nationality,
            @Param("cardId") String cardId,
            @Param("address") String address,
            @Param("dateOfBirth") LocalDate dateOfBirth,
            @Param("placeOfBirth") String placeOfBirth,
            @Param("maritalStatus") String maritalStatus);

    @Query(value = "SELECT * FROM sp_get_user_detail_by_user_id(:userId)", nativeQuery = true)
    Optional<UserDetailEntity> findByUserIdWithProcedure(@Param("userId") String userId);

    @Query(value = "SELECT sp_delete_user_detail(:id)", nativeQuery = true)
    int deleteWithProcedure(@Param("id") Long id);
}