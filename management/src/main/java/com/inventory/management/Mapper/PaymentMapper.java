    package com.inventory.management.Mapper;

    import com.inventory.management.Dtos.InventoryItemDTO;
    import com.inventory.management.Dtos.PaymentRequestDTO;
    import com.inventory.management.Dtos.PaymentResponseDTO;
    import com.inventory.management.Model.InventoryItem;
    import com.inventory.management.Model.Payment;
    import org.mapstruct.Mapper;
    import org.mapstruct.Mapping;

    @Mapper(componentModel = "spring")
    public interface PaymentMapper {

        @Mapping(source = "token", target = "stripeChargeId")
        Payment toPayment(PaymentRequestDTO paymentRequest);
        PaymentResponseDTO toPaymentResponse(Payment payment);


    }