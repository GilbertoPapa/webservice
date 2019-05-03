package br.com.gilbertopapa.webservice.exceptions;

import br.com.gilbertopapa.webservice.model.domain.ErrorMessage;


import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DAOExceptionMapper implements ExceptionMapper <DAOExceptions> {

    public Response toResponse(DAOExceptions exception) {
        ErrorMessage error = new ErrorMessage(exception.getMessage(), exception.getCode());
        if (exception.getCode() == ErrorCode.BAD_REQUEST.getCode()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(error)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } else if (exception.getCode() == ErrorCode.NOT_FOUND.getCode()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(error)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(error)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

}
