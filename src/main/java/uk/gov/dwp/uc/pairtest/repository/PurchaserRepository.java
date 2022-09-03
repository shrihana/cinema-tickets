package uk.gov.dwp.uc.pairtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.gov.dwp.uc.pairtest.entity.Purchaser;

public interface PurchaserRepository extends JpaRepository<Purchaser,Long> {
}
