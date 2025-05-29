package com.jeiyuen.ecommerce.controller;

import jakarta.validation.Valid;

import com.jeiyuen.ecommerce.model.User;
import com.jeiyuen.ecommerce.payload.AddressDTO;
import com.jeiyuen.ecommerce.service.AddressService;
import com.jeiyuen.ecommerce.utility.AuthUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AddressController {

    private AuthUtil authUtil;
    private AddressService addressService;

    @Autowired
    public AddressController(AuthUtil authUtil, AddressService addressService) {
        this.authUtil = authUtil;
        this.addressService = addressService;
    }

    @PostMapping("/addresses")
    public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO addressDTO) {
        User user = authUtil.loggedInUser();
        AddressDTO savedAddressDto = addressService.createAddress(addressDTO, user);
        return new ResponseEntity<>(savedAddressDto, HttpStatus.CREATED);
    }
}
