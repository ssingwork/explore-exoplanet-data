package com.explore.domains;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Planet {

    @JsonProperty("PlanetIdentifier")
    private String planetIdentifier;

    @JsonProperty("TypeFlag")
    private Short typeFlag;

    @JsonProperty("RadiusJpt")
    private Float radiusJpt;

    @JsonProperty("DiscoveryYear")
    private Short discoveryYear;

    @JsonProperty("HostStarTempK")
    private Short hostStarTempK;


    public String getPlanetIdentifier() {
        return planetIdentifier;
    }

    public void setPlanetIdentifier(String planetIdentifier) {
        this.planetIdentifier = planetIdentifier;
    }

    public Float getRadiusJpt() {
        return radiusJpt;
    }

    public void setRadiusJpt(Float radiusJpt) {
        this.radiusJpt = radiusJpt;
    }

    public Short getHostStarTempK() {
        return hostStarTempK;
    }

    public void setHostStarTempK(Short hostStarTempK) {
        this.hostStarTempK = hostStarTempK;
    }

    public Short getDiscoveryYear() {
        return discoveryYear;
    }

    public void setDiscoveryYear(Short discoveryYear) {
        this.discoveryYear = discoveryYear;
    }

    @Override
    public String toString() {
        return "Planet{" +
                "planetIdentifier='" + planetIdentifier + '\'' +
                ", typeFlag=" + typeFlag +
                ", radiusJpt=" + radiusJpt +
                ", discoveryYear=" + discoveryYear +
                ", hostStarTempK=" + hostStarTempK +
                '}';
    }

    public Short getTypeFlag() {
        return typeFlag;
    }

    public void setTypeFlag(Short typeFlag) {
        this.typeFlag = typeFlag;
    }
}
