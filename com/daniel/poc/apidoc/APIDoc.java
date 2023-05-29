package com.daniel.poc.apidoc;

import java.util.Map;

public class APIDoc {
    private String URL;
    private String Description;
    private String Metadata;
    private Map<String, String> Headers;
    private Map<String, String> ReqestParams;
    private Map<String, String> ResponseParam;
    private String RequestSampleBody;
    private String ResponseSampleBody;

    public APIDoc(String URL, String Description, String Metadata, Map<String, String> Headers, Map<String, String> ReqestParams, Map<String, String> ResponseParam, String RequestSampleBody, String ResponseSampleBody) {
        this.URL = URL;
        this.Description = Description;
        this.Metadata = Metadata;
        this.Headers = Headers;
        this.ReqestParams = ReqestParams;
        this.ResponseParam = ResponseParam;
        this.RequestSampleBody = RequestSampleBody;
        this.ResponseSampleBody = ResponseSampleBody;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getMetadata() {
        return Metadata;
    }

    public void setMetadata(String Metadata) {
        this.Metadata = Metadata;
    }

    public Map<String, String> getHeaders() {
        return Headers;
    }

    public void setHeaders(Map<String, String> Headers) {
        this.Headers = Headers;
    }

    public Map<String, String> getReqestParams() {
        return ReqestParams;
    }

    public void setReqestParams(Map<String, String> ReqestParams) {
        this.ReqestParams = ReqestParams;
    }

    public Map<String, String> getResponseParam() {
        return ResponseParam;
    }

    public void setResponseParam(Map<String, String> ResponseParam) {
        this.ResponseParam = ResponseParam;
    }

    public String getRequestSampleBody() {
        return RequestSampleBody;
    }

    public void setRequestSampleBody(String RequestSampleBody) {
        this.RequestSampleBody = RequestSampleBody;
    }

    public String getResponseSampleBody() {
        return ResponseSampleBody;
    }

    public void setResponseSampleBody(String ResponseSampleBody) {
        this.ResponseSampleBody = ResponseSampleBody;
    }
}