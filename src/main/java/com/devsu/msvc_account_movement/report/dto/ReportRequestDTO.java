package com.devsu.msvc_account_movement.report.dto;

public class ReportRequestDTO {
    private String customerId;
    private String dateInitial;
    private String dateFinal;

    public ReportRequestDTO(String customerId, String dateInitial, String dateFinal) {
        this.customerId = customerId;
        this.dateInitial = dateInitial;
        this.dateFinal = dateFinal;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDateInitial() {
        return dateInitial;
    }

    public void setDateInitial(String dateInitial) {
        this.dateInitial = dateInitial;
    }

    public String getDateFinal() {
        return dateFinal;
    }

    public void setDateFinal(String dateFinal) {
        this.dateFinal = dateFinal;
    }
}