package org.jquezadp.microservice.account.management.controllers;

import org.jquezadp.microservice.account.management.controllers.responses.report.ReportResponse;
import org.jquezadp.microservice.account.management.services.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService service;

    public ReportController(ReportService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> generateReport(@RequestParam(value = "startDate") String startDate,
                                            @RequestParam(value = "endDate") String endDate,
                                            @RequestParam(value = "clientId") Long clientId) throws Exception {
        ReportResponse reportResponse = service.generateReport(startDate, endDate, clientId);
        return ResponseEntity.ok(reportResponse);
    }
}
