package com.inventory.management.HelperClasses;

import java.util.Random;

public class PolicyNumberGenerator {
    public static String generatePolicyNumber() {
        return "PQL" + System.currentTimeMillis() + "-" + new Random().nextInt(9999);
    }
}
