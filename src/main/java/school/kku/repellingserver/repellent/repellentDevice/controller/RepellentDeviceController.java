package school.kku.repellingserver.repellent.repellentDevice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.kku.repellingserver.gateway.dto.SerialIdExistResponse;
import school.kku.repellingserver.repellent.repellentDevice.service.RepellentDeviceService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class RepellentDeviceController {

    private final RepellentDeviceService repellentDeviceService;

    @GetMapping("/repellent-device/valid/serial-id")
    public ResponseEntity<SerialIdExistResponse> validSerialId(@RequestParam String serialId) {
        boolean isSerialIdExists = repellentDeviceService.isSerialIdExistsActivated(serialId);

        return ResponseEntity.ok(SerialIdExistResponse.of(isSerialIdExists));
    }


}
