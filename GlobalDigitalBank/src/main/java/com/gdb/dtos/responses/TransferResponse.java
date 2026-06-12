package com.gdb.dtos.responses;

import com.gdb.dtos.TransferDTO;

public class TransferResponse {
    private int httpStatusCode;
    private String message;
    private TransferDTO transferDTO;

    public TransferResponse(TransferDTO transferDTO) {
        this.transferDTO = transferDTO;
        this.httpStatusCode = 0;
        this.message = "";
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

    public TransferDTO getTransferDTO() {
        return transferDTO;
    }

    public void setTransferDTO(TransferDTO transferDTO) {
        this.transferDTO = transferDTO;
    }
}
