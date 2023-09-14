package school.kku.repellingserver.farm.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import school.kku.repellingserver.farm.constant.FarmType;
import school.kku.repellingserver.farm.dto.FarmRequest;
import school.kku.repellingserver.gateway.domain.Gateway;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Farm {
    @Id @GeneratedValue
    private Long id;

    private String name;

    private String address;

    private String latitude;

    private String longitude;

    @Enumerated(EnumType.STRING)
    private FarmType farmType;

    @OneToOne(fetch = FetchType.LAZY)
    private Gateway gateway;

    public Farm(String name, String address, String latitude, String longitude, FarmType farmType) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.farmType = farmType;
    }

    public static Farm toEntity(FarmRequest request) {
        return new Farm(request.farmName(), request.farmAddress(), request.latitude(), request.longitude(), request.farmType());
    }

    public void setGateway(Gateway gateway) {
        this.gateway = gateway;
    }

}
