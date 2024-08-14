package com.devsu.msvc_account_movement.report.controller;

import com.devsu.msvc_account_movement.report.dto.ReportRequestDTO;
import com.devsu.msvc_account_movement.report.dto.ReportResponseDTO;
import com.devsu.msvc_account_movement.report.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<ReportResponseDTO> generateReport(@RequestParam String dateInitial,
                                                            @RequestParam String dateFinal,
                                                            @RequestParam String customer) {
        return new ResponseEntity<>(reportService.generateReport(new ReportRequestDTO(customer, dateInitial,
                dateFinal)), HttpStatus.OK);
    }
}
