package com.gdb.dtos.responses;

import com.gdb.dtos.WithdrawDTO;

/**
 * WithdrawResponse
 */
public class WithdrawResponse {
    private int httpStatusCode;
    private String message;
    private WithdrawDTO withdrawDTO;

    public WithdrawResponse(WithdrawDTO withdrawDTO) {
        this.withdrawDTO = withdrawDTO;
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

    public WithdrawDTO getWithdrawDTO() {
        return withdrawDTO;
    }

    public void setWithdrawDTO(WithdrawDTO withdrawDTO) {
        this.withdrawDTO = withdrawDTO;
    }
}
