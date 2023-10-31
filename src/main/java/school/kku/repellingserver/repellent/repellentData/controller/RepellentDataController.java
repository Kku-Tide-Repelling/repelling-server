package school.kku.repellingserver.repellent.repellentData.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import school.kku.repellingserver.gateway.dto.RepellentDataRequest;
import school.kku.repellingserver.member.domain.Member;
import school.kku.repellingserver.repellent.repellentData.domain.RepellentData;
import school.kku.repellingserver.repellent.repellentData.dto.DayByDetectionListResponse;
import school.kku.repellingserver.repellent.repellentData.dto.MainPageDataResponse;
import school.kku.repellingserver.repellent.repellentData.service.RepellentDataService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/repellent-data")
public class RepellentDataController {

  private final RepellentDataService repellentDataService;

  // FIXME: 현재 Lora gateway serialId :  205
  @PostMapping
  public ResponseEntity<String> repellentData(@RequestBody RepellentDataRequest request,
      HttpServletRequest httpServletRequest) {
    String gatewayIp = httpServletRequest.getRemoteAddr();
    log.info("gateway IP : {}", gatewayIp);
    RepellentData repellentData = repellentDataService.saveData(request, gatewayIp);
    log.info("repellentData : {}", repellentData);

    return ResponseEntity.ok("OK");
  }


  @GetMapping("/main")
  public ResponseEntity<MainPageDataResponse> getRepellentDataList(@RequestParam Long farmId) {
    return ResponseEntity.ok(repellentDataService.getRepellentDataList(farmId));
  }

}
