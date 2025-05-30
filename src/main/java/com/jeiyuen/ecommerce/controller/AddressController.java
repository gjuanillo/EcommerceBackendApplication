package com.jeiyuen.ecommerce.controller;

import java.util.List;

import jakarta.validation.Valid;

import com.jeiyuen.ecommerce.model.User;
import com.jeiyuen.ecommerce.payload.AddressDTO;
import com.jeiyuen.ecommerce.service.AddressService;
import com.jeiyuen.ecommerce.utility.AuthUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/addresses")
    public ResponseEntity<List<AddressDTO>> getAddresses() {
        List<AddressDTO> addressDTOs = addressService.getAddresses();
        return new ResponseEntity<>(addressDTOs, HttpStatus.OK);
    }

    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable("addressId") Long addressId) {
        AddressDTO addressDTO = addressService.getAddressById(addressId);
        return new ResponseEntity<>(addressDTO, HttpStatus.OK);
    }

    @GetMapping("/users/addresses")
    public ResponseEntity<List<AddressDTO>> getUserAddress() {
        User user = authUtil.loggedInUser();
        List<AddressDTO> addressList = addressService.getUserAddresses(user);
        return new ResponseEntity<>(addressList, HttpStatus.OK);
    }

    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> updateUserAddress(
            @PathVariable("addressId") Long addressId,
            @RequestBody AddressDTO addressDTO) {
        AddressDTO address = addressService.updateAddress(addressId, addressDTO);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable("addressId") Long addressId) {
        String status = addressService.deleteAddress(addressId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
