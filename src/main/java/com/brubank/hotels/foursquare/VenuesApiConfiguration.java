package com.brubank.hotels.foursquare;

/**
 * Configuration properties for {@link RestConnectorVenuesApi} connection
 */
public class VenuesApiConfiguration {

  /** The Api Host */
  private String host;
  /** The clientId used to authenticate */
  private String clientId;
  /** The clientSecret used to authenticate */
  private String clientSecret;
  /** The Api version */
  private String version;

  public VenuesApiConfiguration() {
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getClientSecret() {
    return clientSecret;
  }

  public void setClientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }
}
