package com.projeto.tracker.payment;

import com.stripe.Stripe;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StripeClient {

    private static final String API_TEST_KEY = "sk_test_51KdZOyI1l7hWQ6Pr8ZzuzedjT9Ex7YVCzivJM45c7D6HDyYsEuotquTEpk6QDGfXqXglcPhp0Haq2NTb2PJZK1LO008aNGeLOY";

    @Autowired
    StripeClient() {
        Stripe.apiKey = API_TEST_KEY;
    }

    //carregando o cartão de credito com o valor a moeda e o token
    public Charge chargeCreditCard(String token, double amount) throws Exception {
        Map<String, Object> chargeParams = new HashMap<String, Object>();

        chargeParams.put("amount", (int)(amount * 100)); //aqui passa para inteiro e multiplica por 100 o valor por que o stipe usa centavos nao reais
        chargeParams.put("currency", Currencys.BRL);
        chargeParams.put("source", token);

        return Charge.create(chargeParams);
    }



}
