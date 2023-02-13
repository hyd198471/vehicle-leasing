package com.allane.vehicleleasingbackend.leasingcontract.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeasingContractRepository extends JpaRepository<LeasingContractEntity, Long> {
}
