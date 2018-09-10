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

import java.util.ArrayList;

import nu.xom.Element;

class ClassbookGroup {
    private final String activityID;
    private final String name;
    private final float weight;
    private final int seq;
    private final boolean notGraded;
    //hidePortal
    //hasValidScore
    //composite
    //calcEclude
    private final int termID;
    private final int taskID;
    private final float percentage;
    private String formattedPercentage;
    private String letterGrade;
    private final float pointsEarned;
    private final float totalPointsPossible;

    private final ArrayList<ClassbookActivity> activities = new ArrayList<>();

    public ClassbookGroup(Element group) {
        activityID = group.getAttributeValue("activityID");
        name = group.getAttributeValue("name");
        weight = Float.parseFloat(group.getAttributeValue("weight"));
        seq = Integer.parseInt(group.getAttributeValue("seq"));
        notGraded = group.getAttributeValue("notGraded").equalsIgnoreCase("true");
        termID = Integer.parseInt(group.getAttributeValue("termID"));
        taskID = Integer.parseInt(group.getAttributeValue("taskID"));
        percentage = Float.parseFloat(group.getAttributeValue("percentage"));
        formattedPercentage = group.getAttributeValue("formattedPercentage");
        letterGrade = group.getAttributeValue("letterGrade");
        pointsEarned = Float.parseFloat(group.getAttributeValue("pointsEarned"));
        totalPointsPossible = Float.parseFloat(group.getAttributeValue("totalPointsPossible"));

        for (int i = 0; i < group.getFirstChildElement("activities").getChildElements("ClassbookActivity").size(); i++)
            activities.add(new ClassbookActivity(group.getFirstChildElement("activities").getChildElements("ClassbookActivity").get(i)));


        letterGrade = (letterGrade == null ? "?" : letterGrade);
        formattedPercentage = (formattedPercentage == null ? "?" : formattedPercentage);
    }

    public String getInfoString() {
        StringBuilder str = new StringBuilder(name + (name.length() < 16 ? "\t" : "") + "\t(" + (weight > 0 ? "Weight: " + weight + ", " : "") + "Grade: " + letterGrade + ", " + formattedPercentage + "%)");

        for (ClassbookActivity a : activities)
            str.append("\n\t").append(a.getInfoString().replace("\n", "\n\t"));

        return str.toString();
    }
}
