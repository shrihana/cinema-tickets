package uk.gov.dwp.uc.pairtest.entity;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "SEAT")
@Entity
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long seatId;

    private String ticketType;

    @ManyToOne
    private Integer purchaserId;

    private String seatNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return ticketType.equals(seat.ticketType) && purchaserId.equals(seat.purchaserId) && seatNumber.equals(seat.seatNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketType, purchaserId, seatNumber);
    }

}