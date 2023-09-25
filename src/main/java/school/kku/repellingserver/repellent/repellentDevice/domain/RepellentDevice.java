package school.kku.repellingserver.repellent.repellentDevice.domain;

import jakarta.persistence.*;
import lombok.*;
import school.kku.repellingserver.farm.domain.Farm;
import school.kku.repellingserver.repellent.repellentData.domain.RepellentData;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class RepellentDevice {

    @Id @GeneratedValue
    private Long id;

    private String serialId;

    private String latitude;
    private String longitude;

    @Column(columnDefinition = "boolean default false")
    private Boolean isActivated;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farm_id")
    private Farm farm;

    @OneToMany(mappedBy = "repellentDevice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RepellentData> repellentData = new ArrayList<>();


    @Builder
    public RepellentDevice(Long id, String serialId, String latitude, String longitude, Boolean isActivated, Farm farm) {
        this.id = id;
        this.serialId = serialId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isActivated = isActivated;
        this.farm = farm;
    }

    public void activate() {
        this.isActivated = true;
    }
}
