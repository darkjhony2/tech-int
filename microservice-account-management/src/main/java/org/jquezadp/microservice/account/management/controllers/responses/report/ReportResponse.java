package org.jquezadp.microservice.account.management.controllers.responses.report;

import lombok.Data;
import org.jquezadp.microservice.account.management.controllers.responses.client.ClientResponse;

import java.util.ArrayList;
import java.util.List;

@Data
public class ReportResponse {
    ClientResponse clientResponse;
    List<AccountReportResponse> accountReportResponses = new ArrayList<>();
}
