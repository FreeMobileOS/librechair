/*
 *     Copyright (c) 2017-2019 the Lawnchair team
 *     Copyright (c)  2019 oldosfan (would)
 *     This file is part of Lawnchair Launcher.
 *
 *     Lawnchair Launcher is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Lawnchair Launcher is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Lawnchair Launcher.  If not, see <https://www.gnu.org/licenses/>.
 */

/*
 * Weatherbit.io - Swagger UI Weather API documentation
 * This is the documentation for the Weatherbit Weather API.  The base URL for the API is [http://api.weatherbit.io/v2.0/](http://api.weatherbit.io/v2.0/) or [https://api.weatherbit.io/v2.0/](http://api.weatherbit.io/v2.0/). Below is the Swagger UI documentation for the API. All API requests require the `key` parameter.        An Example for a 5 day forecast for London, UK would be `http://api.weatherbit.io/v2.0/forecast/3hourly?city=London`&`country=UK`. See our [Weather API description page](https://www.weatherbit.io/api) for additional documentation.
 *
 * OpenAPI spec version: 2.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package io.weatherbase.api.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
/**
 * GeoIPObj
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2019-07-23T18:38:21.490044+08:00[Asia/Shanghai]")
public class GeoIPObj {
  @SerializedName("longitude")
  private String longitude = null;

  @SerializedName("latitude")
  private String latitude = null;

  @SerializedName("offset")
  private String offset = null;

  @SerializedName("timezone")
  private String timezone = null;

  @SerializedName("organization")
  private String organization = null;

  @SerializedName("continent_code")
  private String continentCode = null;

  @SerializedName("region")
  private String region = null;

  @SerializedName("metro_code")
  private String metroCode = null;

  @SerializedName("dma_code")
  private String dmaCode = null;

  @SerializedName("ip")
  private String ip = null;

  @SerializedName("country_code")
  private String countryCode = null;

  @SerializedName("area_code")
  private String areaCode = null;

  @SerializedName("postal_code")
  private String postalCode = null;

  @SerializedName("charset")
  private String charset = null;

  @SerializedName("city")
  private String city = null;

  @SerializedName("country_code3")
  private String countryCode3 = null;

  public GeoIPObj longitude(String longitude) {
    this.longitude = longitude;
    return this;
  }

   /**
   * City Name
   * @return longitude
  **/
  @Schema(example = "-78.64", description = "City Name")
  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public GeoIPObj latitude(String latitude) {
    this.latitude = latitude;
    return this;
  }

   /**
   * State Abbreviation
   * @return latitude
  **/
  @Schema(example = "35.811", description = "State Abbreviation")
  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public GeoIPObj offset(String offset) {
    this.offset = offset;
    return this;
  }

   /**
   * UTC Offset (deprecated)
   * @return offset
  **/
  @Schema(example = "-4", description = "UTC Offset (deprecated)")
  public String getOffset() {
    return offset;
  }

  public void setOffset(String offset) {
    this.offset = offset;
  }

  public GeoIPObj timezone(String timezone) {
    this.timezone = timezone;
    return this;
  }

   /**
   * Local IANA time zone
   * @return timezone
  **/
  @Schema(example = "America/New_York", description = "Local IANA time zone")
  public String getTimezone() {
    return timezone;
  }

  public void setTimezone(String timezone) {
    this.timezone = timezone;
  }

  public GeoIPObj organization(String organization) {
    this.organization = organization;
    return this;
  }

   /**
   * Organization Name
   * @return organization
  **/
  @Schema(example = "AS11426 Time Warner Cable Internet LLC", description = "Organization Name")
  public String getOrganization() {
    return organization;
  }

  public void setOrganization(String organization) {
    this.organization = organization;
  }

  public GeoIPObj continentCode(String continentCode) {
    this.continentCode = continentCode;
    return this;
  }

   /**
   * Country Abbreviation
   * @return continentCode
  **/
  @Schema(example = "US", description = "Country Abbreviation")
  public String getContinentCode() {
    return continentCode;
  }

  public void setContinentCode(String continentCode) {
    this.continentCode = continentCode;
  }

  public GeoIPObj region(String region) {
    this.region = region;
    return this;
  }

   /**
   * Latitude
   * @return region
  **/
  @Schema(example = "NC", description = "Latitude")
  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public GeoIPObj metroCode(String metroCode) {
    this.metroCode = metroCode;
    return this;
  }

   /**
   * Longitude
   * @return metroCode
  **/
  @Schema(example = "560", description = "Longitude")
  public String getMetroCode() {
    return metroCode;
  }

  public void setMetroCode(String metroCode) {
    this.metroCode = metroCode;
  }

  public GeoIPObj dmaCode(String dmaCode) {
    this.dmaCode = dmaCode;
    return this;
  }

   /**
   * Longitude
   * @return dmaCode
  **/
  @Schema(example = "560", description = "Longitude")
  public String getDmaCode() {
    return dmaCode;
  }

  public void setDmaCode(String dmaCode) {
    this.dmaCode = dmaCode;
  }

  public GeoIPObj ip(String ip) {
    this.ip = ip;
    return this;
  }

   /**
   * IP Address
   * @return ip
  **/
  @Schema(example = "192.168.1.102", description = "IP Address")
  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public GeoIPObj countryCode(String countryCode) {
    this.countryCode = countryCode;
    return this;
  }

   /**
   * Country Code (Short)
   * @return countryCode
  **/
  @Schema(example = "US", description = "Country Code (Short)")
  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public GeoIPObj areaCode(String areaCode) {
    this.areaCode = areaCode;
    return this;
  }

   /**
   * Area Code
   * @return areaCode
  **/
  @Schema(example = "919", description = "Area Code")
  public String getAreaCode() {
    return areaCode;
  }

  public void setAreaCode(String areaCode) {
    this.areaCode = areaCode;
  }

  public GeoIPObj postalCode(String postalCode) {
    this.postalCode = postalCode;
    return this;
  }

   /**
   * Postal Code
   * @return postalCode
  **/
  @Schema(example = "27601", description = "Postal Code")
  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public GeoIPObj charset(String charset) {
    this.charset = charset;
    return this;
  }

   /**
   * Character encoding
   * @return charset
  **/
  @Schema(example = "1", description = "Character encoding")
  public String getCharset() {
    return charset;
  }

  public void setCharset(String charset) {
    this.charset = charset;
  }

  public GeoIPObj city(String city) {
    this.city = city;
    return this;
  }

   /**
   * City Name
   * @return city
  **/
  @Schema(example = "Raleigh", description = "City Name")
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public GeoIPObj countryCode3(String countryCode3) {
    this.countryCode3 = countryCode3;
    return this;
  }

   /**
   * Country Code (Long)
   * @return countryCode3
  **/
  @Schema(example = "USA", description = "Country Code (Long)")
  public String getCountryCode3() {
    return countryCode3;
  }

  public void setCountryCode3(String countryCode3) {
    this.countryCode3 = countryCode3;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GeoIPObj geoIPObj = (GeoIPObj) o;
    return Objects.equals(this.longitude, geoIPObj.longitude) &&
        Objects.equals(this.latitude, geoIPObj.latitude) &&
        Objects.equals(this.offset, geoIPObj.offset) &&
        Objects.equals(this.timezone, geoIPObj.timezone) &&
        Objects.equals(this.organization, geoIPObj.organization) &&
        Objects.equals(this.continentCode, geoIPObj.continentCode) &&
        Objects.equals(this.region, geoIPObj.region) &&
        Objects.equals(this.metroCode, geoIPObj.metroCode) &&
        Objects.equals(this.dmaCode, geoIPObj.dmaCode) &&
        Objects.equals(this.ip, geoIPObj.ip) &&
        Objects.equals(this.countryCode, geoIPObj.countryCode) &&
        Objects.equals(this.areaCode, geoIPObj.areaCode) &&
        Objects.equals(this.postalCode, geoIPObj.postalCode) &&
        Objects.equals(this.charset, geoIPObj.charset) &&
        Objects.equals(this.city, geoIPObj.city) &&
        Objects.equals(this.countryCode3, geoIPObj.countryCode3);
  }

  @Override
  public int hashCode() {
    return Objects.hash(longitude, latitude, offset, timezone, organization, continentCode, region, metroCode, dmaCode, ip, countryCode, areaCode, postalCode, charset, city, countryCode3);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GeoIPObj {\n");
    
    sb.append("    longitude: ").append(toIndentedString(longitude)).append("\n");
    sb.append("    latitude: ").append(toIndentedString(latitude)).append("\n");
    sb.append("    offset: ").append(toIndentedString(offset)).append("\n");
    sb.append("    timezone: ").append(toIndentedString(timezone)).append("\n");
    sb.append("    organization: ").append(toIndentedString(organization)).append("\n");
    sb.append("    continentCode: ").append(toIndentedString(continentCode)).append("\n");
    sb.append("    region: ").append(toIndentedString(region)).append("\n");
    sb.append("    metroCode: ").append(toIndentedString(metroCode)).append("\n");
    sb.append("    dmaCode: ").append(toIndentedString(dmaCode)).append("\n");
    sb.append("    ip: ").append(toIndentedString(ip)).append("\n");
    sb.append("    countryCode: ").append(toIndentedString(countryCode)).append("\n");
    sb.append("    areaCode: ").append(toIndentedString(areaCode)).append("\n");
    sb.append("    postalCode: ").append(toIndentedString(postalCode)).append("\n");
    sb.append("    charset: ").append(toIndentedString(charset)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    countryCode3: ").append(toIndentedString(countryCode3)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}