package model;

import java.util.Date;
import java.util.Objects;

public class Reservation {
    private final Customer customer;
    private final IRoom iRoom;
    private final Date checkInDate;
    private final Date checkOutDate;

    public Reservation(Customer customer, IRoom iRoom, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.iRoom = iRoom;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Date getCheckInDate() {
        return this.checkInDate;
    }

    public Date getCheckOutDate() {
        return this.checkOutDate;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public IRoom getARoom() {
        return this.iRoom;
    }

    public boolean isRoomReserved(Date checkInDate, Date checkOutDate) {
        return checkInDate.before(this.checkOutDate) && checkOutDate.after(this.checkInDate);
    }

    public String toString() {
        return "Reservation {" +
                "Customer = " + customer +
                ", Room = " + iRoom +
                ", Check-In Date = " + checkInDate +
                ", Check-Out Date = " + checkOutDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation reservation = (Reservation) o;
        return customer.equals(this.customer) && iRoom.equals(this.iRoom) && checkInDate.equals(this.checkInDate) && checkOutDate.equals(this.checkOutDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, iRoom, checkInDate, checkOutDate);
    }
}
