package farm_to_table.farm_to_table.Repository;

import farm_to_table.farm_to_table.model.PermissionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionsRepository extends JpaRepository<PermissionsEntity, Long> {

    @Query("""
        select p from PermissionsEntity p
        where p.id in (select rp.permissionId from RolePermissionEntity rp
                       where rp.roleId = :roleId)
        """)
    List<PermissionsEntity> findByRoleId(@Param("roleId") Long roleId);
}
