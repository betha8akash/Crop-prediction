package com.example.farmwise;

public class HarvestDetail {
    private String monthAndYear;
    private String cropName;
    private String approxProduction;
    private String region;
    private String areaUnderCultivation;
    public HarvestDetail() {}

    public HarvestDetail(String monthAndYear, String cropName, String approxProduction, String region, String areaUnderCultivation) {
        this.monthAndYear = monthAndYear;
        this.cropName = cropName;
        this.approxProduction = approxProduction;
        this.region = region;
        this.areaUnderCultivation = areaUnderCultivation;
    }
    // getters
    public String getMonthAndYear() {
        return monthAndYear;
    }

    public String getCropName() {
        return cropName;
    }

    public String getApproxProduction() {
        return approxProduction;
    }

    public String getRegion() {
        return region;
    }

    public String getAreaUnderCultivation() {
        return areaUnderCultivation;
    }

    // setters
    public void setMonthAndYear(String monthAndYear) {
        this.monthAndYear = monthAndYear;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public void setApproxProduction(String approxProduction) {
        this.approxProduction = approxProduction;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setAreaUnderCultivation(String areaUnderCultivation) {
        this.areaUnderCultivation = areaUnderCultivation;
    }
}
