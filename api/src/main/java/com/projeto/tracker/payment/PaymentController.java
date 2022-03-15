package com.projeto.tracker.payment;

import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payment")
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
