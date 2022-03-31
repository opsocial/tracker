package com.projeto.tracker.payment;

import com.stripe.Stripe;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StripeClient {

    @Autowired
    StripeClient(@Value("${stripe.apikey}") String api_key) {
        Stripe.apiKey = api_key;}

    //carregando o cartão de credito com o valor a moeda e o token
    public Charge chargeCreditCard(String token, double amount) throws Exception {
        Map<String, Object> chargeParams = new HashMap<String, Object>();

        chargeParams.put("amount", (int)(amount * 100)); //aqui passa para inteiro e multiplica por 100 o valor por que o stipe usa centavos nao reais
        chargeParams.put("currency", Currencys.BRL);
        chargeParams.put("source", token);

        return Charge.create(chargeParams);
    }



}
