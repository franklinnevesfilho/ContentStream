package com.momentz.contentsteam.utils.factories;

import com.momentz.contentsteam.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ResponseFactory {

    /**
     * Generate an OK Response Status: 200
     * @param response The payload, may contain an object, or error messages
     * @return a response containing the response input with the correct status
     */
    public ResponseEntity<Response> generateOk(Response response) {
        return generateResponse(response, HttpStatus.OK);
    }

    public ResponseEntity<Response> generateBadRequest(Response response) {
        return generateResponse(response, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Response> generateUnauthorized(Response response) {
        return generateResponse(response, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<Response> generateForbidden(Response response) {
        return generateResponse(response, HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<Response> generateNotFound() {
        return generateResponse(null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Response> generateServerError() {
        return generateResponse(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Response> generateError(Response response, HttpStatus status) {
        return generateResponse(response, status);
    }

    private ResponseEntity<Response> generateResponse(Response response, HttpStatus status) {
        Response payload = Response.builder().build();
        if (response != null){
            payload = response;
        }

        log.info(payload.toString());
        return new ResponseEntity<Response>(payload, status);
    }
}
