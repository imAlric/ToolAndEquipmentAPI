package br.com.alec.RestAPI.exception.Order;

public class OrderNotFoundExcep extends RuntimeException{
    public OrderNotFoundExcep(Long id){
        super("OrderNotFound: Não foi possível encontrar o pedido de ID "+id+"."+
            "\nContexto: Possivelmente um pedido com este ID não existe." +
            "\nSolução: Confira o ID utilizado e informe um ID existente.");
    }
}
