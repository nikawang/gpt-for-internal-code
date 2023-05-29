package com.daniel.poc.apidoc;

import io.swagger.models.*;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.properties.Property;
import io.swagger.parser.SwaggerParser;

import java.util.HashMap;
import java.util.Map;

public class SwaggerV2Parser implements APIParser {

    public static APIDoc parse(String swaggerFilePath) {
        APIDoc apiDoc = new APIDoc();

        Swagger swagger = new SwaggerParser().read(swaggerFilePath);

        apiDoc.setUrl(swagger.getHost() + swagger.getBasePath());
        apiDoc.setDescription(swagger.getInfo().getDescription());
        apiDoc.setMetadata(swagger.getInfo().toString());
        apiDoc.setHeaders(new HashMap<>());

        Map<String, Path> paths = swagger.getPaths();

        Map<String, String> requestParams = new HashMap<>();
        Map<String, String> responseParams = new HashMap<>();

        paths.forEach((path, pathItem) -> {
            for (Operation operation : pathItem.getOperations()) {
                List<Parameter> parameters = operation.getParameters();
                for (Parameter parameter : parameters) {
                    if (!(parameter instanceof RefParameter)) {
                        requestParams.putIfAbsent(parameter.getName(), parameter.getDescription());
                    }
                }

                Map<String, Response> responses = operation.getResponses();
                responses.forEach((responseCode, response) -> {
                    Map<String, Property> schemaProperties = response.getResponseSchema().getProperties();
                    schemaProperties.forEach((propertyName, property) ->
                            responseParams.putIfAbsent(propertyName, property.getDescription()));
                });
            }
        });

        apiDoc.setRequestParams(requestParams);
        apiDoc.setResponseParams(responseParams);

        return apiDoc;
    }
}
