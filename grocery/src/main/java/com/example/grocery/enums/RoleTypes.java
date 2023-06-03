package com.example.grocery.enums;

public enum RoleTypes {
    ADMIN,
    SHOP_OWNER,
    CUSTOMER;

    @Override
    public String toString(){
        return switch (this) {
            case ADMIN -> "Admin";
            case SHOP_OWNER -> "Shop Owner";
            case CUSTOMER -> "Customer";
        };
    }
}
