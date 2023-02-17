package br.com.alec.RestAPI.exception.Order;

import br.com.alec.RestAPI.model.Order;

public class OrderNotCreatedExcep extends RuntimeException{
    public OrderNotCreatedExcep(){
        super("OrderNotCreated: Não foi possível executar a criação do pedido." +
            "\nContexto: As informações podem estar com tipos incorretos, tamanhos excedentes, com informações faltando ou um dos objetos inserido não existe." +
            "\nSolução: Verifique se todos os campos preenchem os requisitos de tipo e tamanho ou se há informações faltando/incorretas.");
    }
}
