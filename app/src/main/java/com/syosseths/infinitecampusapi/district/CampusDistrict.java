package com.syosseths.infinitecampusapi.district;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CampusDistrict {
    private int id;
    private String aliases;
    private String dateCreated;
    private String districtAppName;
    private String districtBaseURL;
    private String districtCode;
    private String districtGUID;
    private String districtLatitude;
    private String districtLongitude;
    private String districtName;
    private int districtNumber;
    private String districtWebsite;
    private String lastUpdated;
    private int mobileCheckins;
    private int portalCheckins;
    private String stateCode;

    public int getId() {
        return id;
    }

    public String getAliases() {
        return aliases;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getDistrictAppName() {
        return districtAppName;
    }

    public String getDistrictBaseURL() {
        return districtBaseURL;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public String getDistrictGUID() {
        return districtGUID;
    }

    public String getDistrictLatitude() {
        return districtLatitude;
    }

    public String getDistrictLongitude() {
        return districtLongitude;
    }

    public String getDistrictName() {
        return districtName;
    }

    public int getDistrictNumber() {
        return districtNumber;
    }

    public String getDistrictWebsite() {
        return districtWebsite;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public int getMobileCheckins() {
        return mobileCheckins;
    }

    public int getPortalCheckins() {
        return portalCheckins;
    }

    public String getStateCode() {
        return stateCode;
    }
}
