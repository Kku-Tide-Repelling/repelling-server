package school.kku.repellingserver.repellent.repellentDevice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import school.kku.repellingserver.farm.domain.Farm;
import school.kku.repellingserver.repellent.repellentData.domain.RepellentData;

import java.util.ArrayList;
import java.util.List;

@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class RepellentDevice {

    @Id @GeneratedValue
    private Long id;

    private String serialId;

    private String name;

    private String latitude;
    private String longitude;

    @JsonIgnore
    @ColumnDefault("false")
    private Boolean isActivated;

    @ColumnDefault("false")
    private Boolean isWorking;

    @JsonIgnore
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farm_id")
    private Farm farm;

    @OneToMany(mappedBy = "repellentDevice", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<RepellentData> repellentData;


    @Builder
    public RepellentDevice(Long id, String serialId, String name, String latitude, String longitude, Boolean isActivated, Boolean isWorking, Farm farm, List<RepellentData> repellentData) {
        this.id = id;
        this.serialId = serialId;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isActivated = isActivated;
        this.isWorking = isWorking;
        this.farm = farm;
        this.repellentData = repellentData;
    }

    public void activate() {
        this.isActivated = true;
    }
}
