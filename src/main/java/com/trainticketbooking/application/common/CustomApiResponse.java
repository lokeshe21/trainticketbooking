package com.trainticketbooking.application.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

/**
 * The type Custom api response.
 *
 * @param <T> the type parameter
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomApiResponse<T> {

    private int status;
    private String message;
    private T data;
    private Map<String, Object> meta;
    private Map<String, String> links;
    private CustomApiError error;

    /**
     * Instantiates a new Custom api response.
     */
    public CustomApiResponse() {
    }

    /**
     * Instantiates a new Custom api response.
     *
     * @param status  the status
     * @param message the message
     * @param data    the data
     */
    // Constructor for successful response with data
    public CustomApiResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    /**
     * Instantiates a new Custom api response.
     *
     * @param status  the status
     * @param message the message
     * @param data    the data
     * @param meta    the meta
     */
    // Constructor for successful response with data and meta information
    public CustomApiResponse(int status, String message, T data, Map<String, Object> meta) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.meta = meta;
    }

    /**
     * Instantiates a new Custom api response.
     *
     * @param status  the status
     * @param message the message
     * @param error   the error
     */
    // Constructor for error response
    public CustomApiResponse(int status, String message, CustomApiError error) {
        this.status = status;
        this.message = message;
        this.error = error;
    }

    // Getters and Setters

    /**
     * Gets status.
     *
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    public T getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * Gets meta.
     *
     * @return the meta
     */
    public Map<String, Object> getMeta() {
        return meta;
    }

    /**
     * Sets meta.
     *
     * @param meta the meta
     */
    public void setMeta(Map<String, Object> meta) {
        this.meta = meta;
    }

    /**
     * Gets links.
     *
     * @return the links
     */
    public Map<String, String> getLinks() {
        return links;
    }

    /**
     * Sets links.
     *
     * @param links the links
     */
    public void setLinks(Map<String, String> links) {
        this.links = links;
    }

    /**
     * Gets error.
     *
     * @return the error
     */
    public CustomApiError getError() {
        return error;
    }

    /**
     * Sets error.
     *
     * @param error the error
     */
    public void setError(CustomApiError error) {
        this.error = error;
    }
}