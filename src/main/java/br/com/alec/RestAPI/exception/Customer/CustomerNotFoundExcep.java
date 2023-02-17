package br.com.alec.RestAPI.exception.Customer;

public class CustomerNotFoundExcep extends RuntimeException{
    public CustomerNotFoundExcep(Long id){
        super("CustomerNotFound: Não foi possível encontrar o cliente de ID "+id+"."+
                "\nContexto: Possivelmente um cliente com este ID não existe." +
                "\nSolução: Confira o ID utilizado e informe um ID existente.");
    }
}
