package br.com.alec.RestAPI.exception.Order;

public class OrderByStaffNotFoundExcep extends RuntimeException{
    public OrderByStaffNotFoundExcep(Long id){
        super("OrderByStaffNotFound: Não foi possível encontrar um pedido com ID de funcionário "+id+"."+
            "\nContexto: Possivelmente um pedido com este funcionário não existe ou não foi feito neste contexto/um funcionário com este ID não existe." +
            "\nSolução: Confira o ID utilizado e informe um ID existente ou que tenha sido utilizado antes.");
    }
}
