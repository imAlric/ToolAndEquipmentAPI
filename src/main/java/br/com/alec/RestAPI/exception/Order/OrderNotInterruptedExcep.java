package br.com.alec.RestAPI.exception.Order;

public class OrderNotInterruptedExcep extends RuntimeException{
    public OrderNotInterruptedExcep(){
        super("OrderNotInterrupted: Não foi possível executar o resumo do pedido." +
                "\nContexto: O pedido já está em andamento.");
    }
}
