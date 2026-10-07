package farm_to_table.farm_to_table.DTO.Response;

public record PermissionResponse(
        Long id, String permissionName, String module,
        Boolean canInsert, Boolean canRead, Boolean canUpdate, Boolean canDelete) {}