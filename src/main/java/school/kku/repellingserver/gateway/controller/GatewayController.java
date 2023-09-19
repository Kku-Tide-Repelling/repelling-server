package school.kku.repellingserver.gateway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.kku.repellingserver.gateway.dto.RepellentDataRequest;
import school.kku.repellingserver.gateway.dto.SerialIdExsistResponse;
import school.kku.repellingserver.gateway.service.GatewayService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class GatewayController {

    private final GatewayService gatewayService;

    @GetMapping("/valid/serial-id")
    public ResponseEntity<SerialIdExsistResponse> validSerialId(@RequestParam String serialId) {

        boolean isSerialIdExists = gatewayService.isSerialIdExists(serialId);

        return ResponseEntity.ok(SerialIdExsistResponse.of(isSerialIdExists));
    }

    @PostMapping("/repellent-data")
    public ResponseEntity<String> repellentData(@RequestBody RepellentDataRequest request) {
//        gatewayService.sendData(serialId, data);
        return ResponseEntity.ok("OK");
    }

}
