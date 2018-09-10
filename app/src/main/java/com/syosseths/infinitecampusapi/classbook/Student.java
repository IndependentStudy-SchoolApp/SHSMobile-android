/*	Copyright 2016 Joel Koreth
	This file is part of Infinite Campus API 2.0.

	Infinite Campus API 2.0 is free software: you can redistribute it and/or modify
	it under the terms of the GNU Affero General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	Infinite Campus API 2.0 is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU Affero General Public License for more details.

	You should have received a copy of the GNU General Affero Public License
	along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.syosseths.infinitecampusapi.classbook;

import com.syosseths.infinitecampusapi.calendar.Calendar;
import com.syosseths.infinitecampusapi.district.CampusDistrict;

import java.util.ArrayList;

import nu.xom.Element;

public class Student {
    private final String studentNumber;
    public boolean hasSecurityRole = false;
    public final String personID;
    private final String lastName;
    private final String firstName;
    private final String middleName;
    private final String isGuardian;

    public final ArrayList<Calendar> calendars = new ArrayList<>();
    private final GradingDetailSummary gradeDetailSummary;
    public final ArrayList<Classbook> classbooks = new ArrayList<>();

    private final CampusDistrict distInfo;

    public Student(Element userElement) {
        this(userElement, null);
    }

    public Student(Element userElement, CampusDistrict info) {
        distInfo = info;

        studentNumber = userElement.getAttributeValue("studentNumber");
        personID = userElement.getAttributeValue("personID");
        lastName = userElement.getAttributeValue("lastName");
        firstName = userElement.getAttributeValue("firstName");
        middleName = userElement.getAttributeValue("middleName");
        isGuardian = userElement.getAttributeValue("isGuardian");
        for (int i = 0; i < userElement.getChildElements("Calendar").size(); i++)
            calendars.add(new Calendar(userElement.getChildElements("Calendar").get(i)));
        gradeDetailSummary = new GradingDetailSummary(userElement.getFirstChildElement("GradingDetailSummary"));
        for (int i = 0; i < userElement.getChildElements("Classbook").size(); i++) {
            try {
                classbooks.add(new Classbook(userElement.getChildElements("Classbook").get(i)));
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    private String getPictureURL() {
        return distInfo.getDistrictBaseURL() + "personPicture.jsp?personID=" + personID;
    }

    //TODO: Load news items
    public String getInfoString() {
        StringBuilder userInfo = new StringBuilder("Information for " + firstName + " " + (middleName==null ? "" : middleName + " ") + lastName + ":\nStudent Number: " + studentNumber + "\nPerson ID: " + personID + "\nPicture URL: " + getPictureURL() + "\nIs Guardian? " + isGuardian + "\n\n===Calendars===");

        for (Calendar c : calendars) {
            userInfo.append("\n").append(c.getInfoString());
        }

        return userInfo.toString();
    }
}
