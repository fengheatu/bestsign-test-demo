package com.pangmaobao.bestsign.util;


/**
 * @author he.feng
 */
public class HttpResp {
    /** The Trans flag. */
    private boolean transSuccess;
    /** The Response context. */
    private String responseContext;
    /** The Response code. */
    private String responseCode;

    /**
     * Is trans flag boolean.
     *
     * @return the boolean
     */
    public boolean isTransSuccess() {
        return transSuccess;
    }

    /**
     * Sets trans flag.
     *
     * @param transSuccess the trans flag
     */
    public void setTransSuccess(boolean transSuccess) {
        this.transSuccess = transSuccess;
    }

    /**
     * Gets response context.
     *
     * @return the response context
     */
    public String getResponseContext() {
        return responseContext;
    }

    /**
     * Sets response context.
     *
     * @param responseContext the response context
     */
    public void setResponseContext(String responseContext) {
        this.responseContext = responseContext;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }
}
