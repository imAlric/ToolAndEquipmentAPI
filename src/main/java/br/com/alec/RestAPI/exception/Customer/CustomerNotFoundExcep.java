package br.com.alec.RestAPI.exception.Customer;

public class CustomerNotFoundExcep extends RuntimeException{
    public CustomerNotFoundExcep(Long id){
        super("CustomerNotFound: Não foi possível encontrar o cliente de ID "+id+"."+
                "\nContexto: Possivelmente um cliente com este ID não existe." +
                "\nSolução: Confira o ID utilizado e informe um ID existente.");
    }
    public CustomerNotFoundExcep(String cpf){
        super("CustomerNotFound: Não foi possível encontrar o cliente de CPF "+cpf+"."+
        "\nContexto: Possivelmente um cliente com este CPF não existe." +
        "\nSolução: Confira o CPF utilizado e informe um CPF existente.");
    }
}
