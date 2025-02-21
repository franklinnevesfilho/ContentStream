package com.momentz.contentsteam.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * MainService class
 * This class is responsible for converting objects into JsonNode objects
 * @author Franklin Neves Filho
 */
public class MainService {
    private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    /**
     * Construct a JsonNode object based on a given object
     * @param obj Object being converted into a JsonNode
     * @return returns a JsonNode of the given object
     */
    protected JsonNode mapToJson(Object obj) {
        mapper.registerModule(new JavaTimeModule());
        return mapper.convertValue(obj, JsonNode.class);
    }
}