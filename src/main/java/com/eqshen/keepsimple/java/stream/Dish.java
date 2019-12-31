package com.eqshen.keepsimple.java.stream;

import lombok.Data;

@Data
public class Dish {
    private final String name;
    private final boolean vegetarian;
    private final int calories;
    private final Type type;

    public enum Type { MEAT, FISH, OTHER }
}
