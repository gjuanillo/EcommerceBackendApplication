package com.jeiyuen.ecommerce.service;

import com.jeiyuen.ecommerce.model.User;
import com.jeiyuen.ecommerce.payload.AddressDTO;

public interface AddressService{

    AddressDTO createAddress(AddressDTO addressDTO, User user);

}
