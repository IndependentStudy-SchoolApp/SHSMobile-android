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

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.IOException;

import nu.xom.ParsingException;

public class Main {
    public final static int ERROR_INVALID_CREDENTIALS = 1;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void main(String[] args) throws IOException, ParsingException {

        if (!FileManager.loginFilesExist()) {
            FileManager.saveUsername();
            FileManager.savePasswords();
            FileManager.saveDistrictCode();
        }

        String username = FileManager.getUsername();
        String password = FileManager.getPassword();
        String districtCode = FileManager.getDistrictCode();
        CoreManager core = new CoreManager(username, password, districtCode);

        DataRetriever dataRetriever = new DataRetriever(core);
        dataRetriever.logGrades();

        print("Log dump successful!");
        System.exit(0);
    }

    public static void print(String s) {
        System.out.println(s);
    }
}
