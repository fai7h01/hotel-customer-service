package com.hotel.enums;

import lombok.Getter;

@Getter
public enum UserRole {

    ADMIN("Admin"), CUSTOMER("Customer");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }
}
