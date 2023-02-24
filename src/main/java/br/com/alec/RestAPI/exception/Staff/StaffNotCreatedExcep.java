package br.com.alec.RestAPI.exception.Staff;

public class StaffNotCreatedExcep extends RuntimeException{
    public StaffNotCreatedExcep(){
        super("StaffNotCreated: Não foi possível executar a criação do funcionário." +
        "\nContexto: As informações podem estar com tipos incorretos, tamanhos excedentes, com informações faltando ou um dos objetos inserido não existe." +
        "\nSolução: Verifique se todos os campos preenchem os requisitos de tipo e tamanho ou se há informações faltando/incorretas.");
    }
}
