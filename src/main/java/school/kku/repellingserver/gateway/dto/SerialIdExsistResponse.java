package school.kku.repellingserver.gateway.dto;

public record SerialIdExsistResponse(

        boolean isSerialIdExists
) {
    public static SerialIdExsistResponse of(boolean isSerialIdExists) {
        return new SerialIdExsistResponse(isSerialIdExists);
    }
}
