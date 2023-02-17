package br.com.alec.RestAPI.exception.Tool;

public class ToolNotFoundExcep extends RuntimeException{
    public ToolNotFoundExcep(Long id){
        super("ToolNotFound: Não foi possível encontrar a ferramenta de ID "+id+"."+
            "\nContexto: Possivelmente uma ferramenta com este ID não existe." +
            "\nSolução: Confira o ID utilizado e informe um ID existente.");
    }
}
