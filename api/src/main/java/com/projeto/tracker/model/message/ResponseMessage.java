package com.projeto.tracker.model.message;

//A ResponseMessage é para mensagem para o cliente que vamos usar no Rest Controller e no Exception Handler.
public class ResponseMessage {
    private String message;
    public ResponseMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
