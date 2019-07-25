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
import java.math.BigDecimal;
import java.util.Objects;
/**
 * HistoryDayObj
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2019-07-23T18:38:21.490044+08:00[Asia/Shanghai]")
public class HistoryDayObj {
  @SerializedName("datetime")
  private String datetime = null;

  @SerializedName("ts")
  private Integer ts = null;

  @SerializedName("slp")
  private BigDecimal slp = null;

  @SerializedName("pres")
  private BigDecimal pres = null;

  @SerializedName("rh")
  private Integer rh = null;

  @SerializedName("dewpt")
  private BigDecimal dewpt = null;

  @SerializedName("temp")
  private BigDecimal temp = null;

  @SerializedName("max_temp")
  private BigDecimal maxTemp = null;

  @SerializedName("max_temp_ts")
  private BigDecimal maxTempTs = null;

  @SerializedName("min_temp")
  private BigDecimal minTemp = null;

  @SerializedName("min_temp_ts")
  private BigDecimal minTempTs = null;

  @SerializedName("wind_spd")
  private BigDecimal windSpd = null;

  @SerializedName("wind_dir")
  private Integer windDir = null;

  @SerializedName("wind_gust_spd")
  private BigDecimal windGustSpd = null;

  @SerializedName("max_wind_spd")
  private BigDecimal maxWindSpd = null;

  @SerializedName("max_wind_dir")
  private Integer maxWindDir = null;

  @SerializedName("max_wind_spd_ts")
  private BigDecimal maxWindSpdTs = null;

  @SerializedName("ghi")
  private Integer ghi = null;

  @SerializedName("t_ghi")
  private Integer tGhi = null;

  @SerializedName("dni")
  private Integer dni = null;

  @SerializedName("t_dni")
  private Integer tDni = null;

  @SerializedName("dhi")
  private Integer dhi = null;

  @SerializedName("t_dhi")
  private Integer tDhi = null;

  @SerializedName("max_uv")
  private Integer maxUv = null;

  @SerializedName("precip")
  private BigDecimal precip = null;

  @SerializedName("precip_gpm")
  private BigDecimal precipGpm = null;

  @SerializedName("snow")
  private BigDecimal snow = null;

  @SerializedName("snow_depth")
  private BigDecimal snowDepth = null;

  public HistoryDayObj datetime(String datetime) {
    this.datetime = datetime;
    return this;
  }

   /**
   * Date in format \&quot;YYYY-MM-DD\&quot;. All datetime is in (UTC)
   * @return datetime
  **/
  @Schema(example = "2015-01-03", description = "Date in format \"YYYY-MM-DD\". All datetime is in (UTC)")
  public String getDatetime() {
    return datetime;
  }

  public void setDatetime(String datetime) {
    this.datetime = datetime;
  }

  public HistoryDayObj ts(Integer ts) {
    this.ts = ts;
    return this;
  }

   /**
   * Unix timestamp of datetime (Midnight UTC)
   * @return ts
  **/
  @Schema(example = "1501970516", description = "Unix timestamp of datetime (Midnight UTC)")
  public Integer getTs() {
    return ts;
  }

  public void setTs(Integer ts) {
    this.ts = ts;
  }

  public HistoryDayObj slp(BigDecimal slp) {
    this.slp = slp;
    return this;
  }

   /**
   * Average sea level pressure (mb)
   * @return slp
  **/
  @Schema(example = "1020.1", description = "Average sea level pressure (mb)")
  public BigDecimal getSlp() {
    return slp;
  }

  public void setSlp(BigDecimal slp) {
    this.slp = slp;
  }

  public HistoryDayObj pres(BigDecimal pres) {
    this.pres = pres;
    return this;
  }

   /**
   * Average pressure (mb)
   * @return pres
  **/
  @Schema(example = "885.1", description = "Average pressure (mb)")
  public BigDecimal getPres() {
    return pres;
  }

  public void setPres(BigDecimal pres) {
    this.pres = pres;
  }

  public HistoryDayObj rh(Integer rh) {
    this.rh = rh;
    return this;
  }

   /**
   * Average relative humidity as a percentage (%)
   * @return rh
  **/
  @Schema(example = "85", description = "Average relative humidity as a percentage (%)")
  public Integer getRh() {
    return rh;
  }

  public void setRh(Integer rh) {
    this.rh = rh;
  }

  public HistoryDayObj dewpt(BigDecimal dewpt) {
    this.dewpt = dewpt;
    return this;
  }

   /**
   * Average dewpoint - Default (C)
   * @return dewpt
  **/
  @Schema(example = "-1.5", description = "Average dewpoint - Default (C)")
  public BigDecimal getDewpt() {
    return dewpt;
  }

  public void setDewpt(BigDecimal dewpt) {
    this.dewpt = dewpt;
  }

  public HistoryDayObj temp(BigDecimal temp) {
    this.temp = temp;
    return this;
  }

   /**
   * Average temperature - Default (C)
   * @return temp
  **/
  @Schema(example = "1.0", description = "Average temperature - Default (C)")
  public BigDecimal getTemp() {
    return temp;
  }

  public void setTemp(BigDecimal temp) {
    this.temp = temp;
  }

  public HistoryDayObj maxTemp(BigDecimal maxTemp) {
    this.maxTemp = maxTemp;
    return this;
  }

   /**
   * Max temperature - Default (C)
   * @return maxTemp
  **/
  @Schema(example = "1.5", description = "Max temperature - Default (C)")
  public BigDecimal getMaxTemp() {
    return maxTemp;
  }

  public void setMaxTemp(BigDecimal maxTemp) {
    this.maxTemp = maxTemp;
  }

  public HistoryDayObj maxTempTs(BigDecimal maxTempTs) {
    this.maxTempTs = maxTempTs;
    return this;
  }

   /**
   * Time of max memperature - Unix Timestamp
   * @return maxTempTs
  **/
  @Schema(example = "1501970816", description = "Time of max memperature - Unix Timestamp")
  public BigDecimal getMaxTempTs() {
    return maxTempTs;
  }

  public void setMaxTempTs(BigDecimal maxTempTs) {
    this.maxTempTs = maxTempTs;
  }

  public HistoryDayObj minTemp(BigDecimal minTemp) {
    this.minTemp = minTemp;
    return this;
  }

   /**
   * Min temperature - Default (C)
   * @return minTemp
  **/
  @Schema(example = "11.7", description = "Min temperature - Default (C)")
  public BigDecimal getMinTemp() {
    return minTemp;
  }

  public void setMinTemp(BigDecimal minTemp) {
    this.minTemp = minTemp;
  }

  public HistoryDayObj minTempTs(BigDecimal minTempTs) {
    this.minTempTs = minTempTs;
    return this;
  }

   /**
   * Time of max temperature - unix timestamp
   * @return minTempTs
  **/
  @Schema(example = "1501970516", description = "Time of max temperature - unix timestamp")
  public BigDecimal getMinTempTs() {
    return minTempTs;
  }

  public void setMinTempTs(BigDecimal minTempTs) {
    this.minTempTs = minTempTs;
  }

  public HistoryDayObj windSpd(BigDecimal windSpd) {
    this.windSpd = windSpd;
    return this;
  }

   /**
   * Average wind speed - default (m/s)
   * @return windSpd
  **/
  @Schema(example = "14.98", description = "Average wind speed - default (m/s)")
  public BigDecimal getWindSpd() {
    return windSpd;
  }

  public void setWindSpd(BigDecimal windSpd) {
    this.windSpd = windSpd;
  }

  public HistoryDayObj windDir(Integer windDir) {
    this.windDir = windDir;
    return this;
  }

   /**
   * Average wind direction (degrees)
   * @return windDir
  **/
  @Schema(example = "325", description = "Average wind direction (degrees)")
  public Integer getWindDir() {
    return windDir;
  }

  public void setWindDir(Integer windDir) {
    this.windDir = windDir;
  }

  public HistoryDayObj windGustSpd(BigDecimal windGustSpd) {
    this.windGustSpd = windGustSpd;
    return this;
  }

   /**
   * Wind gust speed - default (m/s)
   * @return windGustSpd
  **/
  @Schema(example = "40.98", description = "Wind gust speed - default (m/s)")
  public BigDecimal getWindGustSpd() {
    return windGustSpd;
  }

  public void setWindGustSpd(BigDecimal windGustSpd) {
    this.windGustSpd = windGustSpd;
  }

  public HistoryDayObj maxWindSpd(BigDecimal maxWindSpd) {
    this.maxWindSpd = maxWindSpd;
    return this;
  }

   /**
   * Max 2min Wind Speed - default (m/s)
   * @return maxWindSpd
  **/
  @Schema(example = "19.98", description = "Max 2min Wind Speed - default (m/s)")
  public BigDecimal getMaxWindSpd() {
    return maxWindSpd;
  }

  public void setMaxWindSpd(BigDecimal maxWindSpd) {
    this.maxWindSpd = maxWindSpd;
  }

  public HistoryDayObj maxWindDir(Integer maxWindDir) {
    this.maxWindDir = maxWindDir;
    return this;
  }

   /**
   * Direction of wind at time of max 2min wind (degrees)
   * @return maxWindDir
  **/
  @Schema(example = "325", description = "Direction of wind at time of max 2min wind (degrees)")
  public Integer getMaxWindDir() {
    return maxWindDir;
  }

  public void setMaxWindDir(Integer maxWindDir) {
    this.maxWindDir = maxWindDir;
  }

  public HistoryDayObj maxWindSpdTs(BigDecimal maxWindSpdTs) {
    this.maxWindSpdTs = maxWindSpdTs;
    return this;
  }

   /**
   * Time of max 2min wind - unix timestamp
   * @return maxWindSpdTs
  **/
  @Schema(example = "1501970516", description = "Time of max 2min wind - unix timestamp")
  public BigDecimal getMaxWindSpdTs() {
    return maxWindSpdTs;
  }

  public void setMaxWindSpdTs(BigDecimal maxWindSpdTs) {
    this.maxWindSpdTs = maxWindSpdTs;
  }

  public HistoryDayObj ghi(Integer ghi) {
    this.ghi = ghi;
    return this;
  }

   /**
   * Average hourly global horizontal solar irradiance (W/m^2)
   * @return ghi
  **/
  @Schema(example = "125", description = "Average hourly global horizontal solar irradiance (W/m^2)")
  public Integer getGhi() {
    return ghi;
  }

  public void setGhi(Integer ghi) {
    this.ghi = ghi;
  }

  public HistoryDayObj tGhi(Integer tGhi) {
    this.tGhi = tGhi;
    return this;
  }

   /**
   * Total global horizontal solar irradiance (W/m^2)
   * @return tGhi
  **/
  @Schema(example = "4500", description = "Total global horizontal solar irradiance (W/m^2)")
  public Integer getTGhi() {
    return tGhi;
  }

  public void setTGhi(Integer tGhi) {
    this.tGhi = tGhi;
  }

  public HistoryDayObj dni(Integer dni) {
    this.dni = dni;
    return this;
  }

   /**
   * Average direct normal solar irradiance (W/m^2)
   * @return dni
  **/
  @Schema(example = "125", description = "Average direct normal solar irradiance (W/m^2)")
  public Integer getDni() {
    return dni;
  }

  public void setDni(Integer dni) {
    this.dni = dni;
  }

  public HistoryDayObj tDni(Integer tDni) {
    this.tDni = tDni;
    return this;
  }

   /**
   * Total direct normal solar irradiance (W/m^2)
   * @return tDni
  **/
  @Schema(example = "4500", description = "Total direct normal solar irradiance (W/m^2)")
  public Integer getTDni() {
    return tDni;
  }

  public void setTDni(Integer tDni) {
    this.tDni = tDni;
  }

  public HistoryDayObj dhi(Integer dhi) {
    this.dhi = dhi;
    return this;
  }

   /**
   * Average hourly diffuse horizontal solar irradiance (W/m^2)
   * @return dhi
  **/
  @Schema(example = "125", description = "Average hourly diffuse horizontal solar irradiance (W/m^2)")
  public Integer getDhi() {
    return dhi;
  }

  public void setDhi(Integer dhi) {
    this.dhi = dhi;
  }

  public HistoryDayObj tDhi(Integer tDhi) {
    this.tDhi = tDhi;
    return this;
  }

   /**
   * Total diffuse horizontal solar irradiance (W/m^2)
   * @return tDhi
  **/
  @Schema(example = "4500", description = "Total diffuse horizontal solar irradiance (W/m^2)")
  public Integer getTDhi() {
    return tDhi;
  }

  public void setTDhi(Integer tDhi) {
    this.tDhi = tDhi;
  }

  public HistoryDayObj maxUv(Integer maxUv) {
    this.maxUv = maxUv;
    return this;
  }

   /**
   * Max UV Index (1-11+)
   * @return maxUv
  **/
  @Schema(example = "6", description = "Max UV Index (1-11+)")
  public Integer getMaxUv() {
    return maxUv;
  }

  public void setMaxUv(Integer maxUv) {
    this.maxUv = maxUv;
  }

  public HistoryDayObj precip(BigDecimal precip) {
    this.precip = precip;
    return this;
  }

   /**
   * Liquid equivalent precipitation - default (mm)
   * @return precip
  **/
  @Schema(example = "3.0", description = "Liquid equivalent precipitation - default (mm)")
  public BigDecimal getPrecip() {
    return precip;
  }

  public void setPrecip(BigDecimal precip) {
    this.precip = precip;
  }

  public HistoryDayObj precipGpm(BigDecimal precipGpm) {
    this.precipGpm = precipGpm;
    return this;
  }

   /**
   * Satellite estimated liquid equivalent precipitation - default (mm)
   * @return precipGpm
  **/
  @Schema(example = "3.0", description = "Satellite estimated liquid equivalent precipitation - default (mm)")
  public BigDecimal getPrecipGpm() {
    return precipGpm;
  }

  public void setPrecipGpm(BigDecimal precipGpm) {
    this.precipGpm = precipGpm;
  }

  public HistoryDayObj snow(BigDecimal snow) {
    this.snow = snow;
    return this;
  }

   /**
   * Snowfall - default (mm)
   * @return snow
  **/
  @Schema(example = "30.0", description = "Snowfall - default (mm)")
  public BigDecimal getSnow() {
    return snow;
  }

  public void setSnow(BigDecimal snow) {
    this.snow = snow;
  }

  public HistoryDayObj snowDepth(BigDecimal snowDepth) {
    this.snowDepth = snowDepth;
    return this;
  }

   /**
   * Snow Depth - default (mm)
   * @return snowDepth
  **/
  @Schema(example = "60.0", description = "Snow Depth - default (mm)")
  public BigDecimal getSnowDepth() {
    return snowDepth;
  }

  public void setSnowDepth(BigDecimal snowDepth) {
    this.snowDepth = snowDepth;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HistoryDayObj historyDayObj = (HistoryDayObj) o;
    return Objects.equals(this.datetime, historyDayObj.datetime) &&
        Objects.equals(this.ts, historyDayObj.ts) &&
        Objects.equals(this.slp, historyDayObj.slp) &&
        Objects.equals(this.pres, historyDayObj.pres) &&
        Objects.equals(this.rh, historyDayObj.rh) &&
        Objects.equals(this.dewpt, historyDayObj.dewpt) &&
        Objects.equals(this.temp, historyDayObj.temp) &&
        Objects.equals(this.maxTemp, historyDayObj.maxTemp) &&
        Objects.equals(this.maxTempTs, historyDayObj.maxTempTs) &&
        Objects.equals(this.minTemp, historyDayObj.minTemp) &&
        Objects.equals(this.minTempTs, historyDayObj.minTempTs) &&
        Objects.equals(this.windSpd, historyDayObj.windSpd) &&
        Objects.equals(this.windDir, historyDayObj.windDir) &&
        Objects.equals(this.windGustSpd, historyDayObj.windGustSpd) &&
        Objects.equals(this.maxWindSpd, historyDayObj.maxWindSpd) &&
        Objects.equals(this.maxWindDir, historyDayObj.maxWindDir) &&
        Objects.equals(this.maxWindSpdTs, historyDayObj.maxWindSpdTs) &&
        Objects.equals(this.ghi, historyDayObj.ghi) &&
        Objects.equals(this.tGhi, historyDayObj.tGhi) &&
        Objects.equals(this.dni, historyDayObj.dni) &&
        Objects.equals(this.tDni, historyDayObj.tDni) &&
        Objects.equals(this.dhi, historyDayObj.dhi) &&
        Objects.equals(this.tDhi, historyDayObj.tDhi) &&
        Objects.equals(this.maxUv, historyDayObj.maxUv) &&
        Objects.equals(this.precip, historyDayObj.precip) &&
        Objects.equals(this.precipGpm, historyDayObj.precipGpm) &&
        Objects.equals(this.snow, historyDayObj.snow) &&
        Objects.equals(this.snowDepth, historyDayObj.snowDepth);
  }

  @Override
  public int hashCode() {
    return Objects.hash(datetime, ts, slp, pres, rh, dewpt, temp, maxTemp, maxTempTs, minTemp, minTempTs, windSpd, windDir, windGustSpd, maxWindSpd, maxWindDir, maxWindSpdTs, ghi, tGhi, dni, tDni, dhi, tDhi, maxUv, precip, precipGpm, snow, snowDepth);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class HistoryDayObj {\n");
    
    sb.append("    datetime: ").append(toIndentedString(datetime)).append("\n");
    sb.append("    ts: ").append(toIndentedString(ts)).append("\n");
    sb.append("    slp: ").append(toIndentedString(slp)).append("\n");
    sb.append("    pres: ").append(toIndentedString(pres)).append("\n");
    sb.append("    rh: ").append(toIndentedString(rh)).append("\n");
    sb.append("    dewpt: ").append(toIndentedString(dewpt)).append("\n");
    sb.append("    temp: ").append(toIndentedString(temp)).append("\n");
    sb.append("    maxTemp: ").append(toIndentedString(maxTemp)).append("\n");
    sb.append("    maxTempTs: ").append(toIndentedString(maxTempTs)).append("\n");
    sb.append("    minTemp: ").append(toIndentedString(minTemp)).append("\n");
    sb.append("    minTempTs: ").append(toIndentedString(minTempTs)).append("\n");
    sb.append("    windSpd: ").append(toIndentedString(windSpd)).append("\n");
    sb.append("    windDir: ").append(toIndentedString(windDir)).append("\n");
    sb.append("    windGustSpd: ").append(toIndentedString(windGustSpd)).append("\n");
    sb.append("    maxWindSpd: ").append(toIndentedString(maxWindSpd)).append("\n");
    sb.append("    maxWindDir: ").append(toIndentedString(maxWindDir)).append("\n");
    sb.append("    maxWindSpdTs: ").append(toIndentedString(maxWindSpdTs)).append("\n");
    sb.append("    ghi: ").append(toIndentedString(ghi)).append("\n");
    sb.append("    tGhi: ").append(toIndentedString(tGhi)).append("\n");
    sb.append("    dni: ").append(toIndentedString(dni)).append("\n");
    sb.append("    tDni: ").append(toIndentedString(tDni)).append("\n");
    sb.append("    dhi: ").append(toIndentedString(dhi)).append("\n");
    sb.append("    tDhi: ").append(toIndentedString(tDhi)).append("\n");
    sb.append("    maxUv: ").append(toIndentedString(maxUv)).append("\n");
    sb.append("    precip: ").append(toIndentedString(precip)).append("\n");
    sb.append("    precipGpm: ").append(toIndentedString(precipGpm)).append("\n");
    sb.append("    snow: ").append(toIndentedString(snow)).append("\n");
    sb.append("    snowDepth: ").append(toIndentedString(snowDepth)).append("\n");
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