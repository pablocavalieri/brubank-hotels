package com.brubank.hotels.reservations;

/**
 * Configuration properties for {@link FlightsReservationsRestApi} connection
 */
public class FlightsReservationsApiConfiguration {

  /** The Api Host */
  private String host;

  public FlightsReservationsApiConfiguration() {
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }
}
