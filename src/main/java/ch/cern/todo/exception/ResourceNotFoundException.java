package ch.cern.todo.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public final class ResourceNotFoundException extends ResponseStatusException {



    public ResourceNotFoundException( String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }
    public static ResourceNotFoundException generateFailedIdLookup
            (String target, Long identifier){
        return new ResourceNotFoundException("Failed to find '" + target + "' with id '" + identifier + "'.");
    }
    public static ResourceNotFoundException generateFailedNameLookup
            (String target, String identifier){
        return new ResourceNotFoundException("Failed to find '" + target + "' with name '" + identifier + "'.");
    }



}
