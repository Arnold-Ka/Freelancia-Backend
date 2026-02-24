package com.hackers.freelancia.repository;

import java.util.List;

import com.hackers.freelancia.config.AbstractRepository;
import com.hackers.freelancia.config.Statut;
import com.hackers.freelancia.entity.Payment;

public interface PaymentRepository extends AbstractRepository<Payment, String> {

    List <Payment> findByMissionIdAndStatut(String missionId, Statut active);

}
