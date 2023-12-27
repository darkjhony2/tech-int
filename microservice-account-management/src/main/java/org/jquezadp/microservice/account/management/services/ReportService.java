package org.jquezadp.microservice.account.management.services;

import org.jquezadp.microservice.account.management.controllers.responses.report.ReportResponse;

public interface ReportService {
    ReportResponse generateReport(String startDate, String endDate, Long clientId) throws Exception;
}
