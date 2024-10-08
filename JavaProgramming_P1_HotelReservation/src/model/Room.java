package model;

import java.util.Objects;

public class Room implements IRoom {
    private final String roomNumber;
    public Double price;

    private final RoomType enumeration;

    public Room(String roomNumber, Double price, RoomType enumeration){
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }

    @Override
    public String getRoomNumber() {
        return this.roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return this.price;
    }

    @Override
    public RoomType getRoomType() {
        return this.enumeration;
    }

    @Override
    public boolean isFree() {
        return this.price == (double) 0;
    }

    public String toString() {
        return "Rooms {" +
                "Room number = " + roomNumber +
                ", Price = " + price +
                ", Room type = " + enumeration +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return roomNumber.equals(room.roomNumber) && price.equals(room.price) && enumeration.equals(room.enumeration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, price, enumeration);
    }
}
