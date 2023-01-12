package ch.cern.todo.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public final class FieldRequirementException extends ResponseStatusException {



    public FieldRequirementException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }
    public static FieldRequirementException generateMissingFieldException
            (String target, String field){
        return new FieldRequirementException("The '" + target + "' must have '" + field + "'.");
    }
    public static FieldRequirementException generateDuplicateFieldException
            (String target, String field){
        return new FieldRequirementException("The '" + target + "' must have an unique'" + field + "'.");
    }




}
