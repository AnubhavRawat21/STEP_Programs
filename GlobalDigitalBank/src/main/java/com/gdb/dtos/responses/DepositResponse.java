package com.gdb.dtos.responses;

import com.gdb.dtos.DepositDTO;

/**
 * DepositResponse
 */
public class DepositResponse {
    private int httpStatusCode;
    private String message;
    private DepositDTO depositDTO;

    public DepositResponse(DepositDTO depositDTO) {
        this.depositDTO = depositDTO;
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

    public DepositDTO getDepositDTO() {
        return depositDTO;
    }

    public void setDepositDTO(DepositDTO depositDTO) {
        this.depositDTO = depositDTO;
    }
}
