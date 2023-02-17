package br.com.alec.RestAPI.exception.Order;

public class OrderAlreadyInterruptedExcep extends RuntimeException{
    public OrderAlreadyInterruptedExcep(){
        super("OrderAlreadyClosed: Não foi possível executar a interrupção do pedido." +
                "\nContexto: O pedido já está interrompido.");
    }
}
