package com.gdb.dtos.responses;

import com.gdb.dtos.CloseAccountDTO;

/**
 * CloseAccountResponse
 */
public class CloseAccountResponse {
    private int httpStatusCode;
    private String message;
    private CloseAccountDTO closeAccountDTO;

    public CloseAccountResponse(CloseAccountDTO closeAccountDTO) {
        this.closeAccountDTO = closeAccountDTO;
    }

    public void setResponseStatus(int httpStatusCode, String message) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CloseAccountDTO getCloseAccountDTO() {
        return closeAccountDTO;
    }

    public void setCloseAccountDTO(CloseAccountDTO closeAccountDTO) {
        this.closeAccountDTO = closeAccountDTO;
    }
}
