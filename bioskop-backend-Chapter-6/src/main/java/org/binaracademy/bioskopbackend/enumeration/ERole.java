package org.binaracademy.bioskopbackend.enumeration;

public enum ERole {
    ROLE_CUSTOMER("ROLE_CUSTOMER"),
    ROLE_ADMIN("ROLE_ADMIN");

    private String name;

    ERole(String name) {
        this.name = name;
    }
}
