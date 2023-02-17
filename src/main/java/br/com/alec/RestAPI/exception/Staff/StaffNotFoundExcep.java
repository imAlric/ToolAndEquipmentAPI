package br.com.alec.RestAPI.exception.Staff;

public class StaffNotFoundExcep extends RuntimeException{
    public StaffNotFoundExcep(Long id){
        super("StaffNotFound: Não foi possível encontrar o funcionário de ID "+id+"."+
            "\nContexto: Possivelmente um funcionário com este ID não existe." +
            "\nSolução: Confira o ID utilizado e informe um ID existente.");
    }
}
