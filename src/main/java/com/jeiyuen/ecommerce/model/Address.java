package com.jeiyuen.ecommerce.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @NotBlank
    @Size(min = 3, message = "Street name must be at least 3 characters!")
    private String street;

    @NotBlank
    @Size(min = 3, message = "Building name must be at least 3 characters!")
    private String buildingName;

    @NotBlank
    @Size(min = 3, message = "City name must be at least 3 characters!")
    private String cityName;

    @NotBlank
    @Size(min = 3, message = "State must be at least 3 characters!")
    private String state;

    @NotBlank
    @Size(min = 3, message = "Country must be at least 3 characters!")
    private String country;

    @NotBlank
    @Size(min = 6, message = "Zip code must be at least 6 characters!")
    private String zipCode;

    @ManyToMany(mappedBy = "addresses")
    private List<User> users = new ArrayList<>();

    public Address() {
    }

    public Address(String street, String buildingName,String cityName, String state, String country, String zipCode) {
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Address{addressId=" + addressId + ", street=" + street + ", buildingName=" + buildingName
                + ", cityName=" + cityName + ", state=" + state + ", country=" + country + ", zipCode=" + zipCode + "}";
    }

}
