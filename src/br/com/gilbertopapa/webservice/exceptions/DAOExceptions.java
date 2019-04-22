package br.com.gilbertopapa.webservice.exceptions;

public class DAOExceptions extends RuntimeException{
    private static final long serialVersionUID = 3965087475900464946L;

    private int code;

    public DAOExceptions(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
