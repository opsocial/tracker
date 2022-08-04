package com.projeto.tracker.model;


import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "pagamento")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long idPagamento;

    private String name;

    private String currency;
    private String success_url;
    private String cancelUrl;
    private BigDecimal unitAmount;
    private Long longAmount;
    private Long quantity;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private Integer id_usuario;

    public Payment() {
    }

    public Payment(Long idPagamento, String name, String currency, String success_url, String cancelUrl, BigDecimal unitAmount, Long longAmount, Long quantity, Integer id_usuario) {
        this.idPagamento = idPagamento;
        this.name = name;
        this.currency = currency;
        this.success_url = success_url;
        this.cancelUrl = cancelUrl;
        this.unitAmount = unitAmount;
        this.longAmount = longAmount;
        this.quantity = quantity;
        this.id_usuario = id_usuario;
    }

    public Long getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(Long idPagamento) {
        this.idPagamento = idPagamento;
    }

    public Integer getUsuario() {
        return id_usuario;
    }

    public void setUsuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

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
