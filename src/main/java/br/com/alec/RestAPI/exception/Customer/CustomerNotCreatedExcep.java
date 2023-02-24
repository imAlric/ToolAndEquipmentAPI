package br.com.alec.RestAPI.exception.Customer;

public class CustomerNotCreatedExcep extends RuntimeException{
    public CustomerNotCreatedExcep(){
        super("CustomerNotCreated: Não foi possível executar a criação do cliente." +
        "\nContexto: As informações podem estar com tipos incorretos, tamanhos excedentes, com informações faltando ou um dos objetos inserido não existe." +
        "\nSolução: Verifique se todos os campos preenchem os requisitos de tipo e tamanho ou se há informações faltando/incorretas.");
    }
}
