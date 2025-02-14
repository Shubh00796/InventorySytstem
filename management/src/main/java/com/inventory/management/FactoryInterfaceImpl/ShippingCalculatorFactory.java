package com.inventory.management.FactoryInterfaceImpl;

import com.inventory.management.DesignPatternInterfaces.ShippingCarrier;
import com.inventory.management.HelperClasses.ExpressShippingCalculator;
import com.inventory.management.HelperClasses.ShippingCalculator;
import com.inventory.management.HelperClasses.StandardShippingCalculator;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ShippingCalculatorFactory {

    private final Map<String, Class<? extends ShippingCalculator>> calculatorMap = new ConcurrentHashMap<>();

    public ShippingCalculatorFactory() {
        calculatorMap.put("STANDARD", StandardShippingCalculator.class);
        calculatorMap.put("EXPRESS", ExpressShippingCalculator.class);
    }

    public ShippingCalculator getShippingCalculator(String method, ShippingCarrier carrier) {
        Class<? extends ShippingCalculator> calculatorClass = calculatorMap.get(method.toUpperCase());
        if (calculatorClass == null) {
            throw new IllegalArgumentException("Unsupported shipping method: " + method);
        }
        try {
            return calculatorClass.getConstructor(ShippingCarrier.class).newInstance(carrier);
        } catch (Exception e) {
            throw new RuntimeException("Error creating ShippingCalculator instance for method: " + method, e);
        }
    }
}