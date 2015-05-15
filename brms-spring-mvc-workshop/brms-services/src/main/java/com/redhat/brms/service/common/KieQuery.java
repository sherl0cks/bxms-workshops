package com.redhat.brms.service.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Should be put on the Fields in a Response class that is used by the
 * Component. Will only work if the field is a Collection. Also the fields must
 * have setters that adhere to java bean standards
 * 
 * 
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface KieQuery {

    /**
     * The name of the Query
     * 
     * @return
     */
    String queryName();

    /**
     * The specific value that the Object being queried is bound to.
     * 
     * @return
     */
    String binding();

}