package model;

public class FreeRoom extends Room{
    public FreeRoom(String roomNumber, Double price, RoomType roomType){
        super(roomNumber, price, roomType );
        this.price = 0.0;
    }

    @Override
    public Double getRoomPrice() {
        return super.getRoomPrice();
    }
}
