package farm_to_table.farm_to_table.DTO.Response;

import java.util.List;

public record RoleResponse(Long id, String name, List<PermissionResponse> permissions) {}