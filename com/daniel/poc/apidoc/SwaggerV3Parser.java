package com.daniel.poc.apidoc;

import io.swagger.parser.v3.ObjectMapperFactory;
import io.swagger.parser.v3.OpenAPIV3Parser;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SwaggerV3Parser implements APIParser {

    public APIDoc parse(String swaggerFilePath) {
        APIDoc apiDoc = new APIDoc();

        OpenAPIV3Parser openAPIV3Parser = new OpenAPIV3Parser();
        OpenAPI openAPI = openAPIV3Parser.read(swaggerFilePath);

        apiDoc.setUrl(openAPI.getServers().get(0).getUrl());
        apiDoc.setDescription(openAPI.getInfo().getDescription());
        apiDoc.setMetadata(ObjectMapperFactory.createJson().writeValueAsString(openAPI.getInfo()));
        apiDoc.setHeaders(new HashMap<>());

        Components components = openAPI.getComponents();
        Map<String, PathItem> paths = openAPI.getPaths();

        Map<String, String> requestParams = new HashMap<>();
        Map<String, String> responseParams = new HashMap<>();

        paths.forEach((path, pathItem) -> {
            List<Parameter> parameters = pathItem.readOperations().stream()
                    .flatMap(operation -> operation.getParameters().stream())
                    .collect(Collectors.toList());
            for (Parameter parameter : parameters) {
                requestParams.putIfAbsent(parameter.getName(), parameter.getSchema().toString());
            }

            pathItem.readOperations().forEach(operation -> {
                operation.getResponses().forEach((responseCode, response) -> {
                    if (StringUtils.isNumeric(responseCode)) {
                        response.getContent().forEach((contentType, mediaType) -> {
                            Schema<?> schema = mediaType.getSchema();
                            if (schema != null) {
                                responseParams.putIfAbsent(schema.getName(), schema.toString());
                            }
                        });
                    }
                });
            });
        });

        apiDoc.setRequestParams(requestParams);
        apiDoc.setResponseParams(responseParams);

        return apiDoc;
    }
}