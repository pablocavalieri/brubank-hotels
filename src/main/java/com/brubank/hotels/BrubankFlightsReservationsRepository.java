package com.brubank.hotels;

import com.brubank.hotels.reservations.FlightsReservation;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

public interface BrubankFlightsReservationsRepository
    extends CrudRepository<FlightsReservation, Long> {

  List<FlightsReservation> findAllByDestination(String destination, Sort sort);
}

