package model;

import java.util.HashMap;
import java.util.Map;

public enum RoomType {
    /*
    Reference https://www.baeldung.com/java-enum-values
     */
    SINGLE ("1"),
    DOUBLE ("2");

    private final String bedCount;

    private static final Map<String, RoomType> allBeds = new HashMap<String, RoomType>();

    static {
        for (RoomType roomType : values()) {
            allBeds.put(roomType.bedCount, roomType);
        }
    }

    RoomType(String bedCount) {
        this.bedCount = bedCount;
    }

    public static RoomType valueOfBedCount(String bedCount) {
        return allBeds.get(bedCount);
    }
}
