package com.momentz.contentsteam.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * The Response class is the object that all controllers will return regardless of any error messages
 *
 * @author Franklin Neves Filho
 */


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Response {
    private JsonNode data;

    @Builder.Default
    private String status = "success";
    @Builder.Default
    private List<String> errors = new ArrayList<>();
}
