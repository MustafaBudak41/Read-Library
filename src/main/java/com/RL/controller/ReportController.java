package com.RL.controller;

import com.RL.dto.UserDTO;
import com.RL.dto.response.RLResponse;
import com.RL.dto.response.ReportResponse;
import com.RL.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping
@AllArgsConstructor
public class ReportController {

    private ReportService reportService;

    @GetMapping("/report")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public ResponseEntity<ReportResponse> getAuthenticatedUser( ){

        ReportResponse response= reportService.getReportAboutAllData();


        return ResponseEntity.ok(response);
    }
}
