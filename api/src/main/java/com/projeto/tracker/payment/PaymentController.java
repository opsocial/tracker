package com.projeto.tracker.payment;

import com.google.gson.Gson;
import com.projeto.tracker.model.ChargeResponse;
import com.projeto.tracker.model.Payment;
import com.projeto.tracker.repository.PaymentRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    private final PaymentRepository paymentRepository;
    @Autowired
    PaymentController(StripeClient stripeClient, PaymentRepository paymentRepository) {
        this.stripeClient = stripeClient;
        this.paymentRepository = paymentRepository;
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
                        .setCurrency("brl").setUnitAmount(payment.getLongAmount())
                        .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName("Tracker").build()).build()).build()).build();

        //criando sessao do stripe
        Session session = Session.create(params);
        Map<String, String> responseData = new HashMap<>();

        responseData.put("id", session.getId());

        paymentRepository.save(payment);
        return gson.toJson(responseData);
    }

    @PostMapping("/payment1")
    /**
     * Pagamento com stripe
     *
     * @throws StripeException
     */
    public ResponseEntity<?> paymentWithngx(@RequestBody Payment payment) throws StripeException {
        System.out.println("Processing token" + payment);

        try {
            Map<String, Object> params = new HashMap<>();
            params.put("amount", 9900L);
            params.put("currency", "brl");
            params.put("description", "pagamento teste");
            params.put("source", payment.getToken());
            Charge charge = Charge.create(params);
            ChargeResponse response = new ChargeResponse();
            response.setId(charge.getId());
            return ResponseEntity.ok(response);
        } catch (StripeException e) {
            return ResponseEntity.badRequest().body(e.getStripeError().getMessage());
        }
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

    @PostMapping("/createCustomer")
    public void createCustomer(@RequestBody Customer user) {
        CustomerCreateParams params = CustomerCreateParams.builder().
        setEmail(user.getEmail()).
        setName(user.getName()).
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

    public Charge chargeCreditCard(String token) throws Exception {
        Map<String, Object> chargeParams = new HashMap<String, Object>();

        chargeParams.put("amount", (int)(99 * 100)); //aqui passa para inteiro e multiplica por 100 o valor por que o stipe usa centavos nao reais
        chargeParams.put("currency", Currencys.BRL);
        chargeParams.put("source", token);

        return Charge.create(chargeParams);
    }

    public Charge chargeCustomerCard(String customerId) throws Exception {
        String sourceCard = Customer.retrieve(customerId).getDefaultSource();
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", 9900L);
        chargeParams.put("currency", "brl");
        chargeParams.put("customer", customerId);
        chargeParams.put("source", sourceCard);
        return Charge.create(chargeParams);
    }
}
