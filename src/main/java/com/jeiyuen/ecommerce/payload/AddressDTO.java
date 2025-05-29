package com.jeiyuen.ecommerce.payload;

import java.util.Objects;

public class AddressDTO{

    private Long addressId;
    private String street;
    private String buildingName;
    private String cityName;
    private String state;
    private String country;
    private String zipCode;

    public AddressDTO() {
    }

    public AddressDTO(Long addressId, String street, String buildingName, String cityName, String state, String country,
            String zipCode) {
        this.addressId = addressId;
        this.street = street;
        this.buildingName = buildingName;
        this.cityName = cityName;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressId, street, buildingName, cityName, state, country, zipCode);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AddressDTO other = (AddressDTO) obj;
        return Objects.equals(addressId, other.addressId) && Objects.equals(street, other.street)
                && Objects.equals(buildingName, other.buildingName) && Objects.equals(cityName, other.cityName)
                && Objects.equals(state, other.state) && Objects.equals(country, other.country)
                && Objects.equals(zipCode, other.zipCode);
    }

    @Override
    public String toString() {
        return "AddressDTO{addressId=" + addressId + ", street=" + street + ", buildingName=" + buildingName
                + ", cityName=" + cityName + ", state=" + state + ", country=" + country + ", zipCode=" + zipCode + "}";
    }

}
