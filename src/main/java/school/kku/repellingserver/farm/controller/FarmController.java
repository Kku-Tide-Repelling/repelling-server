package school.kku.repellingserver.farm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.kku.repellingserver.farm.domain.Farm;
import school.kku.repellingserver.farm.dto.FarmRequest;
import school.kku.repellingserver.farm.service.FarmService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class FarmController {

    private final FarmService farmService;

    @PostMapping("/farm")
    public ResponseEntity<Void> createFarm(@RequestBody FarmRequest farmRequest) {
        farmService.save(farmRequest);

        return ResponseEntity.ok().build();
    }




}
