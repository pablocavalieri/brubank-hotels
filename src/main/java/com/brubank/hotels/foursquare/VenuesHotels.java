package com.brubank.hotels.foursquare;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * This class exports the Venues Api Hotels entity response from foursquare
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VenuesHotels {

  /** Meta attribute of the Venues Api Response */
  private Meta meta;
  /** Response attribute of the Venues Api Response */
  private Response<Hotels> response;

  /** Constructor for Spring injection */
  protected VenuesHotels() {
  }

  public Meta getMeta() {
    return meta;
  }

  public void setMeta(Meta meta) {
    this.meta = meta;
  }

  public void setResponse(Response<Hotels> response) {
    this.response = response;
  }

  /**
   * Returns the Hotels of the response, if any. If {@code response} is null, returns
   * {@code Collections.emptyList}
   *
   * @return the {@link Hotels} from the Api Response
   */
  public List<Hotels> getHotels() {
    return Optional.ofNullable(response)
        .map(Response::getVenues)
        .orElse(Collections.emptyList());
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Response<T> {

    private List<Hotels> venues = Collections.emptyList();

    protected Response() {
    }

    public List<Hotels> getVenues() {
      return venues;
    }

    public void setVenues(List<Hotels> venues) {
      this.venues = venues;
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Meta {

    private int code;
    private String requestId;

    protected Meta() {
    }

    public int getCode() {
      return code;
    }

    public void setCode(int code) {
      this.code = code;
    }

    public String getRequestId() {
      return requestId;
    }

    public void setRequestId(String requestId) {
      this.requestId = requestId;
    }
  }
}
