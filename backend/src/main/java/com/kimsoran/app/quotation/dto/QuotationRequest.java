package com.kimsoran.app.quotation.dto;
public class QuotationRequest {

    private String insuranceType;
    private String birthday;
    private String grade;
    private String area;
    private String coverage;
    private Integer vehicleInsurance;

    public String getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public Integer getVehicleInsurance() {
        return vehicleInsurance;
    }

    public void setVehicleInsurance(Integer vehicleInsurance) {
        this.vehicleInsurance = vehicleInsurance;
    }
}