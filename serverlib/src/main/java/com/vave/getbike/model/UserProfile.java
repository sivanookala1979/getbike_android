package com.vave.getbike.model;

/**
 * Created by adarsht on 05/12/16.
 */

public class UserProfile {
    String name;
    String email;
    String occupation;
    String city;
    String yearOfBirth;
    String homeLocation;
    String officeLocation;
    String mobile;
    boolean verified;

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(String yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getHomeLocation() {
        return homeLocation;
    }

    public void setHomeLocation(String homeLocation) {
        this.homeLocation = homeLocation;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public SaveResult isDataValid() {
        SaveResult result = new SaveResult();
        if (!getName().isEmpty()) {
            if (!getEmail().isEmpty()) {
                if (!getOccupation().isEmpty()) {
                    if (!getCity().isEmpty()) {
                        if (!getYearOfBirth().isEmpty()) {
                            if (!getHomeLocation().isEmpty()) {
                                if (!getOfficeLocation().isEmpty()) {
                                    if (!getMobile().isEmpty()) {
                                        result.setValid(true);
                                    } else {
                                        result.setErrorMessage("mobile");
                                    }
                                } else {
                                    result.setErrorMessage("office location");
                                }
                            } else {
                                result.setErrorMessage("home location");
                            }
                        } else {
                            result.setErrorMessage("year of birth");
                        }
                    } else {
                        result.setErrorMessage("city");
                    }
                } else {
                    result.setErrorMessage("occupation");
                }
            } else {
                result.setErrorMessage("email");

            }
        } else {
            result.setErrorMessage("name");
        }
        return result;
    }

}
