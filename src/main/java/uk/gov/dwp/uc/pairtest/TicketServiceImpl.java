package uk.gov.dwp.uc.pairtest;

import org.springframework.beans.factory.annotation.Autowired;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import java.util.Arrays;


public class TicketServiceImpl implements TicketService {

    @Autowired
    private SeatReservationService reservationService;

    @Autowired
    private TicketPaymentService ticketPaymentService;

    /**
     * Should only have private methods other than the one below.
     */
    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {

        int adultCount = Arrays.stream(ticketTypeRequests)
                .filter(ticketTypeRequest -> ticketTypeRequest.getTicketType().name().equals(TicketTypeRequest.Type.ADULT.name()))
                .mapToInt(TicketTypeRequest::getNoOfTickets).sum();

        int childCount = Arrays.stream(ticketTypeRequests)
                .filter(ticketTypeRequest -> ticketTypeRequest.getTicketType().name().equals(TicketTypeRequest.Type.CHILD.name()))
                .mapToInt(TicketTypeRequest::getNoOfTickets).sum();

        int infantCount = Arrays.stream(ticketTypeRequests)
                .filter(ticketTypeRequest -> ticketTypeRequest.getTicketType().name().equals(TicketTypeRequest.Type.INFANT.name()))
                .mapToInt(TicketTypeRequest::getNoOfTickets).sum();

        validate(adultCount,childCount,infantCount,accountId);

        ticketPaymentService.makePayment(accountId,Integer.sum(adultCount * TicketTypeRequest.Type.ADULT.getPrice(),
                childCount * TicketTypeRequest.Type.CHILD.getPrice()));
        reservationService.reserveSeat(accountId,Integer.sum(adultCount,childCount));
        /*
        TO-DO : We can add a DB entry for future reference
         */
    }

    private void validate(int adult, int child, int infant, Long accountId) throws InvalidPurchaseException {
        if(adult<0 || child<0 || infant<0){
            throw new InvalidPurchaseException("Invalid ticket count");
        }
        if(adult==0){
            throw new InvalidPurchaseException("Invalid ticket count");
        }
        if((adult+child+infant) >20){
            throw new InvalidPurchaseException("Maximum ticket limit exceeded");
        }
        if(infant>adult){
            throw new InvalidPurchaseException("All infants must be accompanied by an adult");
        }
        if (accountId.longValue()<=0){
            throw new InvalidPurchaseException("Invalid");
        }
    }
}
