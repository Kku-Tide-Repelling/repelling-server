package school.kku.repellingserver.farm.dto;

import school.kku.repellingserver.farm.constant.FarmType;

public record FarmRequest (
        String serialId,
        String farmName,
        String farmAddress,
        String latitude,
        String longitude,
        FarmType farmType
) {
    public static FarmRequest of(String serialId, String farmName, String farmAddress,String latitude, String longitude, FarmType farmType) {
        return new FarmRequest(serialId, farmName, farmAddress,latitude, longitude, farmType);
    }
}
