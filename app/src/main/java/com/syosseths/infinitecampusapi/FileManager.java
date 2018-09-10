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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

class FileManager {
    private static final String TEXT_FILE_EXTENSION = ".txt";
    private static final String PATH = "./infinite-campus-info/";

    private static final File usernameFile = new File(PATH + ".username.txt"),
            passwordFile = new File(PATH + ".password.txt"),
            districtCodeFile = new File(PATH + ".district_code.txt");


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getDistrictCode() {
        String code = null;
        Path file = FileSystems.getDefault().getPath(PATH + ".district_code.txt");
        try {
            InputStream input = Files.newInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            code = reader.readLine();
            reader.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return code;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getPassword() {
        String password = null;
        Path file = FileSystems.getDefault().getPath(PATH + ".password.txt");
        try {
            InputStream input = Files.newInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            password = reader.readLine();
            reader.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return password;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getUsername() {
        String username = null;
        Path file = FileSystems.getDefault().getPath(PATH + ".username.txt");
        try {
            InputStream input = Files.newInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            username = reader.readLine();
            reader.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return username;
    }

    public static boolean deleteLoginFiles() {
        return usernameFile.delete() && passwordFile.delete() && districtCodeFile.delete();
    }

    public static boolean loginFilesExist() {
        return districtCodeFile.exists() && usernameFile.exists() && passwordFile.exists();
    }

    public static void saveDistrictCode() throws IOException {
        BufferedReader scan = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Please enter the District Code : ");
        String code = scan.readLine();
        createDistrictCodeFile(code);
    }

    public static void saveUsername() throws IOException {
        BufferedReader scan = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Please enter your username : ");
        String username = scan.readLine();
        createUsernameFile(username);
    }

    public static void savePasswords() throws IOException {
        BufferedReader scan = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Please enter your password : ");
        String password = scan.readLine();
        createPasswordFile(password);
    }

    private static void createUsernameFile(String username) {
        createHiddenFile("username", username);
    }

    private static void createPasswordFile(String password) {
        createHiddenFile("password", password);
    }

    private static void createDistrictCodeFile(String code) {
        createHiddenFile("district_code", code);
    }

    private static void createHiddenFile(String name, String data) {
        name = "." + name;
        createFile(name, data);
        if (System.getProperty("os.name").contains("windows")) {
            try {
                Runtime.getRuntime().exec("attrib +H " + name + TEXT_FILE_EXTENSION);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void createFile(String name, String data) {
        try {
            PrintWriter codeFile = new PrintWriter(PATH + name + TEXT_FILE_EXTENSION);
            codeFile.write(data);
            codeFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
