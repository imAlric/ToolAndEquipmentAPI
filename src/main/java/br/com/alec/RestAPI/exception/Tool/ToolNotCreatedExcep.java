package br.com.alec.RestAPI.exception.Tool;

public class ToolNotCreatedExcep extends RuntimeException{
    public ToolNotCreatedExcep(){
        super("ToolNotCreated: Não foi possível executar a criação da ferramenta." +
        "\nContexto: As informações podem estar com tipos incorretos, tamanhos excedentes, com informações faltando ou um dos objetos inserido não existe." +
        "\nSolução: Verifique se todos os campos preenchem os requisitos de tipo e tamanho ou se há informações faltando/incorretas.");
    }
}
