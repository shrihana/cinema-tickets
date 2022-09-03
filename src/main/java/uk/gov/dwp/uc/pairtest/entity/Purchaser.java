package uk.gov.dwp.uc.pairtest.entity;

import javax.persistence.*;
import java.util.List;

@Table(name = "PURCHASER")
@Entity
public class Purchaser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long purchaserId;

    private String accountId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Seat> seats;
}
