import org.junit.Test;
import org.mockito.Mock;
import uk.gov.dwp.uc.pairtest.TicketService;
import uk.gov.dwp.uc.pairtest.TicketServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

public class TicketServiceTest {

    @Mock
    TicketService ticketService = new TicketServiceImpl();

    @Test(expected = InvalidPurchaseException.class)
    public void isAdultPresentTest(){
        TicketTypeRequest ticketTypeReq1= new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 4);
        TicketTypeRequest ticketTypeReq2= new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 2);
        ticketService.purchaseTickets(1000000L,ticketTypeReq1,ticketTypeReq2);
    }
    @Test(expected = InvalidPurchaseException.class)
    public void isAdultPresentPerInfantTest(){
        TicketTypeRequest ticketTypeReq1= new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2);
        TicketTypeRequest ticketTypeReq2= new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 4);
        ticketService.purchaseTickets(1000000L,ticketTypeReq1,ticketTypeReq2);
    }

    @Test(expected = InvalidPurchaseException.class)
    public void isMaxTicketsExceededTest(){
        TicketTypeRequest ticketTypeReq1= new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 8);
        TicketTypeRequest ticketTypeReq2= new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 4);
        TicketTypeRequest ticketTypeReq3= new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 9);
        ticketService.purchaseTickets(1000000L,ticketTypeReq1,ticketTypeReq2,ticketTypeReq3);
    }

    @Test(expected = InvalidPurchaseException.class)
    public void invalidAdultCountTest(){
        TicketTypeRequest ticketTypeReq1= new TicketTypeRequest(TicketTypeRequest.Type.ADULT, -1);
        ticketService.purchaseTickets(1000000L,ticketTypeReq1);
    }
    @Test(expected = InvalidPurchaseException.class)
    public void invalidChildCountTest(){
        TicketTypeRequest ticketTypeReq1= new TicketTypeRequest(TicketTypeRequest.Type.CHILD, -1);
        ticketService.purchaseTickets(1000000L,ticketTypeReq1);
    }

    @Test(expected = InvalidPurchaseException.class)
    public void invalidInfantCountTest(){
        TicketTypeRequest ticketTypeReq1= new TicketTypeRequest(TicketTypeRequest.Type.INFANT, -1);
        ticketService.purchaseTickets(1000000L,ticketTypeReq1);
    }
}
