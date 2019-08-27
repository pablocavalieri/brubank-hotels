package com.brubank.hotels.reservations;

import com.brubank.hotels.api.Reservations;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * This class represents the Flights reservations Api response
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class FlightsReservation {

  /** The creation date */
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime date;
  /** The destination */
  private String destination;
  /** The Flight reservation id */
  @Id
  private String reservationId;

  /**
   * Default constructor needed by Spring injection
   */
  protected FlightsReservation() {
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public String getReservationId() {
    return reservationId;
  }

  public void setReservationId(String reservationId) {
    this.reservationId = reservationId;
  }

  public Reservations toApiReservations() {
    return Reservations.newInstance(
        Optional.ofNullable(getDate())
            .map(LocalDateTime::toString)
            .orElse(""),
        getReservationId());
  }
}
