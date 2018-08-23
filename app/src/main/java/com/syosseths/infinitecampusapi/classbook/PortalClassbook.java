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
import nu.xom.Elements;

public class PortalClassbook {
    public String sectionID;
    public ArrayList<Student> students = new ArrayList<>();

    public PortalClassbook(Element classbookElement) {
        this.sectionID = classbookElement.getAttributeValue("sectionID");
        Elements e = classbookElement.getFirstChildElement("ClassbookDetail").getFirstChildElement("StudentList").getChildElements("Student");
        for (int i = 0; i < e.size(); i++)
            students.add(new Student(e.get(i)));
    }

    public String getInfoString() {
        StringBuilder str = new StringBuilder();
        for (Student s : students)
            for (Classbook c : s.classbooks) {
                str.append(c.getInfoString());
            }
        return str.toString();
    }
}
