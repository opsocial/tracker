package com.projeto.tracker.payment;

import com.google.gson.Gson;
import com.projeto.tracker.model.Payment;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PaymentController {

    private StripeClient stripeClient;

    @Autowired
    PaymentController(StripeClient stripeClient) {
        this.stripeClient = stripeClient;
    }

    @PostMapping("/charge")
    public Charge chargeCard(HttpServletRequest request) throws Exception {
        String token = request.getHeader("token");
        Double amount = Double.parseDouble(request.getHeader("amount"));
        return this.stripeClient.chargeCreditCard(token, amount);
    }


    //criar o objeto Gson
    private static Gson gson = new Gson();

    //criando uma intenção de pagamento
    @PostMapping("/payment")
    /**
     * Pagamento com stripe
     *
     * @throws StripeException
     */
    public String paymentWithCheckoutPage(@RequestBody Payment payment) throws StripeException {
        //iniciondo objeto stripe com a chave da api
        SessionCreateParams params = SessionCreateParams.builder()
                //tipo do pagamento
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.BOLETO)
                .setMode(SessionCreateParams.Mode.PAYMENT).setSuccessUrl(payment.getSuccess_url())
                .setCancelUrl(payment.getCancelUrl())
                .addLineItem(SessionCreateParams.LineItem.builder().setQuantity(payment.getQuantity())
                        .setPriceData(
                                SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency(payment.getCurrency()).setUnitAmount(payment.getLongAmount())
                        .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName(payment.getName()).build()).build()).build()).build();

        //criando sessao do stripe
        Session session = Session.create(params);
        Map<String, String> responseData = new HashMap<>();

        responseData.put("id", session.getId());
        return gson.toJson(responseData);
    }


    @PostMapping("/testCharge")
    public void testCharge(@RequestBody Integer amount) {
        Map<String, Object> params = new HashMap<>();

        params.put("amount", amount);
        params.put("currency", Currencys.BRL);
        params.put("source", "tok_visa");
        params.put("description", "TESTE");

        try {
            Charge charge = Charge.create(params);
            System.out.println(charge);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/testCustomer")
    public void createCustomer() {
        CustomerCreateParams params = CustomerCreateParams.builder().
        setEmail("matheusmelhor3@gmail.com").
        setName("teste").
        build();

        try {
            Customer customer = Customer.create(params);
            System.out.println(customer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //metodo para salvar o cartão de credito do cliente (pode ser usada depois)
    /*public Customer createCustomer(String token, String email) throws Exception {
        Map<String, Object> customerParams = new HashMap<String, Object>();

        customerParams.put("email", email);
        customerParams.put("source", token);
        return Customer.create(customerParams);
    }*/

    //metodo para caregar o cartão do cliente já salvo (pode ser usado futuramente)
    public Charge chargeCustomer(String customerId, int amount) throws Exception {
        String sourceCard = Customer.retrieve(customerId).getDefaultSource();
        Map<String, Object> chargeParams = new HashMap<String, Object>();

        chargeParams.put("amount", amount);
        chargeParams.put("currency", Currencys.BRL);
        chargeParams.put("customer", customerId);
        chargeParams.put("source", sourceCard);

        return Charge.create(chargeParams);
    }
}
