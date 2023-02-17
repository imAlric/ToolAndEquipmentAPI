package br.com.alec.RestAPI.exception.Order;

import br.com.alec.RestAPI.model.Order;

public class OrderNotUpdatedExcep extends RuntimeException{
        public OrderNotUpdatedExcep(Order order){
            super("OrderNotCreated: Não foi possível executar a atualização do pedido." +
                    "\nSolução: Verifique se todos os campos preenchem os requisitos de tipo e tamanho ou se há informações faltando." +
                    "\n("+order.toString()+")");
        }
}
