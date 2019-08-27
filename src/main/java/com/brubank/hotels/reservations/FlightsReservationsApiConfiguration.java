package com.brubank.hotels.reservations;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
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
    checkNotNull(host, "Host is not valid");
    checkArgument(!host.isEmpty(), "Host is not valid");
    this.host = host;
  }
}
