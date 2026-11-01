package farm_to_table.farm_to_table.Controller;

import farm_to_table.farm_to_table.DTO.Delivery.DeliveryResponseDto;
import farm_to_table.farm_to_table.Service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService service;

    // POST, GET /{id}, PUT /{id}, DELETE /{id} ដូច FeeDeliveryController

    @GetMapping
    public ResponseEntity<List<DeliveryResponseDto>> getAll(
            @RequestParam(required = false) String orderId,
            @RequestParam(required = false) String statusId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.getAll(orderId, statusId, page, size));
    }
}
