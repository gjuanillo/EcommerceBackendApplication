package com.jeiyuen.ecommerce.service;

import java.util.List;

import com.jeiyuen.ecommerce.model.User;
import com.jeiyuen.ecommerce.payload.AddressDTO;

public interface AddressService{

    AddressDTO createAddress(AddressDTO addressDTO, User user);

    List<AddressDTO> getAddresses();

    AddressDTO getAddressById(Long addressId);

    List<AddressDTO> getUserAddresses(User user);

    AddressDTO updateAddress(Long addressId, AddressDTO addressDTO);

    String deleteAddress(Long addressId);

}
