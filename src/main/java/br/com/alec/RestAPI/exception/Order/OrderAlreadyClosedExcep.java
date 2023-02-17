package br.com.alec.RestAPI.exception.Order;

public class OrderAlreadyClosedExcep extends RuntimeException{
    public OrderAlreadyClosedExcep(){
        super("OrderAlreadyClosed: Não foi possível executar a baixa do pedido." +
                "\nContexto: O pedido já está baixado.");
    }
}

