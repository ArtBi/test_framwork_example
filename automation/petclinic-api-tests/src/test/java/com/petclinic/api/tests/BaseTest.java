package com.petclinic.api.tests;

import com.github.javafaker.Faker;

public class BaseTest {
    private static final ThreadLocal<Faker> threadLocalFaker = ThreadLocal.withInitial(Faker::new);

    protected Faker getFaker() {
        return threadLocalFaker.get();
    }


}
