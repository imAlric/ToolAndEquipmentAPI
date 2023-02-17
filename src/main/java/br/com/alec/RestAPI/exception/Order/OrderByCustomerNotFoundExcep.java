package br.com.alec.RestAPI.exception.Order;

public class OrderByCustomerNotFoundExcep extends RuntimeException{
    public OrderByCustomerNotFoundExcep(Long id){
        super("OrderByCustomerNotFound: Não foi possível encontrar um pedido com ID de cliente "+id+"."+
            "\nContexto: Possivelmente um pedido com este cliente não existe ou não foi feito neste contexto/um cliente com este ID não existe." +
            "\nSolução: Confira o ID utilizado e informe um ID existente ou que tenha sido utilizado antes.");
    }
}
