package school.kku.repellingserver.repellent.repellentData.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import school.kku.repellingserver.member.domain.Member;
import school.kku.repellingserver.repellent.repellentData.dto.RepellentDataListResponse;
import school.kku.repellingserver.repellent.repellentData.service.RepellentDataService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class RepellentDataController {

    private final RepellentDataService repellentDataService;

    @GetMapping("/repellent-data/main")
    public ResponseEntity<List<RepellentDataListResponse>> getRepellentDataList(@AuthenticationPrincipal Member member, @RequestParam Long farmId) {
        return ResponseEntity.ok(repellentDataService.getRepellentDataList(member, farmId));
    }


}
