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

package com.syosseths.infinitecampusapi;

import com.syosseths.infinitecampusapi.classbook.ClassbookManager;
import com.syosseths.infinitecampusapi.classbook.Student;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.ParsingException;

import static com.syosseths.infinitecampusapi.Main.print;

class DataRetriever {
    private final CoreManager core;

    DataRetriever(CoreManager core) {
        this.core = core;
    }

    public void logGrades() throws IOException, ParsingException {
        URL timeURL = new URL(core.getCampusDistrict().getDistrictBaseURL() + "/prism?x=portal.PortalOutline&appName=" + core.getCampusDistrict().getDistrictAppName());
        Builder builder = new Builder();
        Document doc = builder.build(new ByteArrayInputStream(core.getContent(timeURL, false).getBytes()));
        Element root = doc.getRootElement();
        Student user = new Student(root.getFirstChildElement("PortalOutline").getFirstChildElement("Family").getFirstChildElement("Student"), core.getCampusDistrict());

        print(user.getInfoString());

        URL gradesURL = new URL(core.getCampusDistrict().getDistrictBaseURL() + "/prism?&x=portal.PortalClassbook-getClassbookForAllSections&mode=classbook&personID=" + user.personID + "&structureID=" + user.calendars.get(0).schedules.get(0).id + "&calendarID=" + user.calendars.get(0).calendarID);
        Document doc2 = builder.build(new ByteArrayInputStream(core.getContent(gradesURL, false).getBytes()));
        ClassbookManager manager = new ClassbookManager(doc2.getRootElement().getFirstChildElement("SectionClassbooks"));

        print(manager.getInfoString());

    }
}