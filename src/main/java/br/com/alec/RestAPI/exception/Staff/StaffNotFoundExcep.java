package br.com.alec.RestAPI.exception.Staff;

public class StaffNotFoundExcep extends RuntimeException{
    public StaffNotFoundExcep(Long id){
        super("StaffNotFound: Não foi possível encontrar o funcionário de ID "+id+"."+
            "\nContexto: Possivelmente um funcionário com este ID não existe." +
            "\nSolução: Confira o ID utilizado e informe um ID existente.");
    }
    public StaffNotFoundExcep(String cpf){
        super("StaffNotFound: Não foi possível encontrar o funcionário de CPF "+cpf+"."+
            "\nContexto: Possivelmente um funcionário com este CPF não existe." +
            "\nSolução: Confira o CPF utilizado e informe um CPF existente.");
    }
}
