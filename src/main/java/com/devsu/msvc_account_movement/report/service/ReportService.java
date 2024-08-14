package com.devsu.msvc_account_movement.report.service;

import com.devsu.msvc_account_movement.report.dto.ReportRequestDTO;
import com.devsu.msvc_account_movement.report.dto.ReportResponseDTO;

public interface ReportService {
    ReportResponseDTO generateReport(ReportRequestDTO request);
}