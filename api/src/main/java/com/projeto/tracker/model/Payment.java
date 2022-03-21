package com.projeto.tracker.model;

import com.projeto.tracker.PaymentTypes;
import com.projeto.tracker.payment.Currencys;

import java.math.BigDecimal;

public class Payment {

    private String name;
    private String currency;
    private String success_url;
    private String cancelUrl;
    private BigDecimal unitAmount;
    private Long longAmount;
    private Long quantity;
    private PaymentTypes paymentType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSuccess_url() {
        return success_url;
    }

    public void setSuccess_url(String success_url) {
        this.success_url = success_url;
    }

    public String getCancelUrl() {
        return cancelUrl;
    }

    public void setCancelUrl(String cancelUrl) {
        this.cancelUrl = cancelUrl;
    }

    public BigDecimal getUnitAmount() {
        return unitAmount;
    }

    public void setUnitAmount(BigDecimal unitAmount) {
        this.unitAmount = unitAmount;
    }

    public Long getLongAmount() {
        return longAmount;
    }

    public void setLongAmount(Long longAmount) {
        this.longAmount = longAmount;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
