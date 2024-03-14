package com.trainticketbooking.application.common;

/**
 * The type Custom api error.
 */
public class CustomApiError {

    private String code;
    private String message;
    private String details; // Optional field for additional error details

    /**
     * Instantiates a new Custom api error.
     */
    public CustomApiError() {
    }

    /**
     * Instantiates a new Custom api error.
     *
     * @param code    the code
     * @param message the message
     */
    public CustomApiError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    // Getters and Setters

    /**
     * Gets code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets code.
     *
     * @param code the code
     */
    public void setCode(String code) {
        this.code = code;
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
     * Gets details.
     *
     * @return the details
     */
    public String getDetails() {
        return details;
    }

    /**
     * Sets details.
     *
     * @param details the details
     */
    public void setDetails(String details) {
        this.details = details;
    }

    public static class ApiResponseMessages {
        public static final String PRODUCT_CREATED_SUCCESSFULLY = "Product created successfully";
        public static final String PRODUCT_FOUND = "Product found";
        public static final String PRODUCT_UPDATED = "Product updated";
        public static final String PRODUCT_DELETED = "Product deleted";
        public static final String ALL_PRODUCTS_RETRIEVED = "All products retrieved";
        public static final String DISCOUNT_OR_TAX_APPLIED_SUCCESSFULLY = "Discount or tax applied successfully";
        public static final String EMPLOYEE_CERTIFICATE_GENERATION_ERROR = "Error generating employee certificate";
    }
}

