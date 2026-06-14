package com.kimsoran.app.quotation;

import com.kimsoran.app.quotation.dto.QuotationRequest;
import com.kimsoran.app.quotation.dto.QuotationResponse;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;

@Service
public class QuotationService {

    public QuotationResponse calculatePremium(
            QuotationRequest request) {

        int premium = 50000;

        // 등급
        if (request.getGrade() != null) {
            int grade = Integer.parseInt(
                    request.getGrade().replace("等級", "")
            );
            premium += grade * 1000;
        }

        // 지역
        if ("東京都".equals(request.getArea())) {
            premium += 5000;
        }

        // 차량보험
        if (request.getVehicleInsurance() != null) {
            premium += request.getVehicleInsurance() / 1000;
        }

        if (request.getBirthday() != null && !request.getBirthday().isEmpty()) {

            LocalDate birth = LocalDate.parse(request.getBirthday());
            int age = Period.between(birth, LocalDate.now()).getYears();

            // 예: 나이 반영
            if (age < 4) {
                premium += 10000;
            } else if (age >= 60) {
                premium += 8000;
            }
        }

        if (request.getCoverage() != null) {

            switch (request.getCoverage()) {

                case "無制限":
                    premium += 10000;
                    break;

                case "1億円":
                    premium += 5000;
                    break;

            }
        }


        QuotationResponse response = new QuotationResponse();
        response.setQuotationId("Q-20260531-0007");
        response.setPremiumAmount(premium);
        response.setCurrency("JPY");
        response.setExpiresAt(
                LocalDate.now().plusDays(30).toString()
        );
        return response;
    }
}