package com.market.spring_security_market.persistence.util;

public enum RolePermission {

    //Permisos para los productos
    READ_ALL_PRODUCTS,
    READ_ONE_PRODUCT,
    CREATE_ONE_PRODUCT,
    UPDATE_ONE_PRODUCT,
    DISABLE_ONE_PRODUCT,

    //Permisos para las categorías
    READ_ALL_CATEGORIES,
    READ_ONE_CATEGORY,
    CREATE_ONE_CATEGORY,
    UPDATE_ONE_CATEGORY,
    DISABLE_ONE_CATEGORY,

    //mi perfil
    READ_MY_PROFILE;


}
