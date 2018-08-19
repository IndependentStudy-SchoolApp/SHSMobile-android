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

package classbook;

import java.util.ArrayList;

import nu.xom.Element;
import nu.xom.Elements;

public class ClassbookManager {
    ArrayList<PortalClassbook> portalclassbooks = new ArrayList<PortalClassbook>();

    public ClassbookManager(Element classbook) {
        Elements e = classbook.getChildElements("PortalClassbook");
        for (int i = 0; i < e.size(); i++)
            portalclassbooks.add(new PortalClassbook(e.get(i)));
    }

    public String getInfoString() {
        String str = "";
        for (PortalClassbook p : portalclassbooks)
            str += "\n" + p.getInfoString();
        return str;
    }
}
