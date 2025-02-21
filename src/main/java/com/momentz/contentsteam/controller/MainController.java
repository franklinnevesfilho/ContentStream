package com.momentz.contentsteam.controller;

import com.momentz.contentsteam.model.Response;
import com.momentz.contentsteam.utils.factories.ResponseFactory;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class MainController {
    private ResponseFactory responseFactory;

    @GetMapping
    public ResponseEntity<Response> genericInput() {
        return responseFactory.generateOk(Response.builder().build());
    }
}
