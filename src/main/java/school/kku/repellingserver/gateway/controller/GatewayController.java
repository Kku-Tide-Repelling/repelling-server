package school.kku.repellingserver.gateway.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.kku.repellingserver.gateway.dto.RepellentDataRequest;
import school.kku.repellingserver.gateway.dto.SerialIdExsistResponse;
import school.kku.repellingserver.gateway.service.GatewayService;

@Slf4j
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
    public ResponseEntity<String> repellentData(@RequestBody RepellentDataRequest request, HttpServletRequest httpServletRequest) {
        String gatewayIp = httpServletRequest.getRemoteAddr();
        log.info("gateway IP : {}", gatewayIp);
        gatewayService.saveData(request, gatewayIp);
        return ResponseEntity.ok("OK");
    }

}
