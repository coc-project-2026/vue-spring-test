package com.kimsoran.app.quotation;

import com.kimsoran.app.quotation.dto.QuotationRequest;
import com.kimsoran.app.quotation.dto.QuotationResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/quotations")
public class QuotationController {

    private final QuotationService quotationService;

    public QuotationController(
            QuotationService quotationService) {
        this.quotationService = quotationService;
    }

    @PostMapping
    public QuotationResponse createQuotation(
            @RequestBody QuotationRequest request) {

        return quotationService.calculatePremium(request);
    }
}