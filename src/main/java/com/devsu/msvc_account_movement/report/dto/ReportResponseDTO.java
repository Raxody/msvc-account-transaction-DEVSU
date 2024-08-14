package com.devsu.msvc_account_movement.report.dto;

import java.util.List;

public class ReportResponseDTO {
    private List<AccountReportDTO> accounts;

    public ReportResponseDTO(List<AccountReportDTO> accounts) {
        this.accounts = accounts;
    }

    public List<AccountReportDTO> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountReportDTO> accounts) {
        this.accounts = accounts;
    }
}
