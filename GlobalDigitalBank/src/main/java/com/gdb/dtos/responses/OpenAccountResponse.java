package com.gdb.dtos.responses;

import com.gdb.dtos.OpenAccountDTO;
import com.gdb.dtos.PrivilegeDTO;

/**
 * openAccountResponse
 * Response to be inside DTO
 * Send Message, Status and DTO
 * Separate methods for the implemenations in service which will internally call
 * the respective methods in repo
 */
public class OpenAccountResponse {
    public int httpStatusCode;
    public String message;
    public OpenAccountDTO openAccountDTO;

    public OpenAccountResponse(OpenAccountDTO openAccountDTO) {
        this.openAccountDTO = openAccountDTO;
    }

    public OpenAccountResponse(OpenAccountDTO openAccountDTO, int httpStatusCode, String message) {
        this.openAccountDTO = openAccountDTO;
        this.httpStatusCode = httpStatusCode;
        this.message = message;
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

    public OpenAccountDTO getOpenAccountDTO() {
        return openAccountDTO;
    }

    public void setOpenAccountDTO(OpenAccountDTO openAccountDTO) {
        this.openAccountDTO = openAccountDTO;
    }
}
