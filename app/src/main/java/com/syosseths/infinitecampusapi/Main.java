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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import nu.xom.ParsingException;

public class Main {
    private final static int SUCCESS = 0, ERROR_INVALID_CREDENTIALS = 1;

    private static PrintWriter out;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void main(String[] args) throws IOException, ParsingException {

        InfoLogger logger = new InfoLogger(loginAndReturnCoreManager());

        createFile("grades");

        logger.logGrades();

        out.close();

        System.out.println("Log dump successful!");

        System.exit(SUCCESS);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static CoreManager loginAndReturnCoreManager() throws IOException {
        String username;
        String password;
        String districtCode;

        FileManagement.deleteGrades();
        if (!FileManagement.getExisting()) {
            FileManagement.saveDistrictCode();
            FileManagement.saveUsername();
            FileManagement.savePasswords();
        }
        districtCode = FileManagement.getDistrictCode();
        username = FileManagement.getUsername();
        password = FileManagement.getPassword();

        CoreManager core = new CoreManager(districtCode);

        boolean successfulLogin = core.attemptLogin(username, password, core.getDistrictInfo());
        if (!successfulLogin) {
            FileManagement.deleteExisting();
            System.out.println("Invalid Username/Password or District Code");
            System.exit(ERROR_INVALID_CREDENTIALS);
        }

        return core;
    }

    private static void createFile(String name) {
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter("infinite-campus-info/" + name + ".txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void print(String s) {
        System.out.println(s);
        out.println(s);
    }
}
