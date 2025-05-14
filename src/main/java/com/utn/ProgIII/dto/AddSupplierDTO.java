package com.utn.ProgIII.dto;

import com.utn.ProgIII.model.Address.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record AddSupplierDTO(
        @NotBlank
        String companyname,
        @NotBlank
        String cuit,
        @NotBlank
        String phonenumber,
        @Email
        String email,
        @NotEmpty
        AddAddressDTO address) {

        @Override
        public String toString() {
                return "AddSupplierDTO{" +
                        "companyname='" + companyname + '\'' +
                        ", cuit='" + cuit + '\'' +
                        ", phonenumber='" + phonenumber + '\'' +
                        ", email='" + email + '\'' +
                        ", address=" + address.toString() +
                        '}';
        }
}
