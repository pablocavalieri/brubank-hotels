package com.brubank.hotels.foursquare;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

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
    checkNotNull(host, "Host is not valid");
    checkArgument(!host.isEmpty(), "Host is not valid");
    this.host = host;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    checkNotNull(clientId, "ClientId is not valid");
    checkArgument(!clientId.isEmpty(), "ClientId is not valid");
    this.clientId = clientId;
  }

  public String getClientSecret() {
    return clientSecret;
  }

  public void setClientSecret(String clientSecret) {
    checkNotNull(clientSecret, "ClientSecret is not valid");
    checkArgument(!clientSecret.isEmpty(), "ClientSecret is not valid");
    this.clientSecret = clientSecret;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    checkNotNull(version, "version is not valid");
    checkArgument(!version.isEmpty(), "version is not valid");
    this.version = version;
  }
}
