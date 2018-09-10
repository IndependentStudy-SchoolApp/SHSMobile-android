/* Copyright (c) 2014-2016, Nicolas Nytko, Liam Fruzyna
 All rights reserved.
 ...

 Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

 Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer
 in the documentation and/or other materials provided with the distribution.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS “AS IS” AND ANY EXPRESS OR IMPLIED WARRANTIES,
 INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
 IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 The views and conclusions contained in the software and documentation are those of the authors
 and should not be interpreted as representing official policies, either expressed or implied, of the FreeBSD Project.
*/

package com.syosseths.infinitecampusapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.syosseths.infinitecampusapi.district.CampusDistrict;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import static com.syosseths.infinitecampusapi.Main.print;

class CoreManager {
    private final CampusDistrict campusDistrict;
    private String cookies;

    CoreManager(String username, String password, String districtCode) throws IOException {
        campusDistrict = new ObjectMapper().readValue(
                new URL("https://mobile.infinitecampus.com/mobile/checkDistrict?districtCode=" + districtCode),
                CampusDistrict.class);

        boolean successfulLogin = this.attemptLogin(username, password, campusDistrict);
        if (!successfulLogin) {
            boolean loginFilesDeleted = FileManager.deleteLoginFiles();
            print("Invalid Username/Password or District Code");
            if(!loginFilesDeleted) print("Login Files Failed to Delete");
            System.exit(Main.ERROR_INVALID_CREDENTIALS);
        }
    }

    public CampusDistrict getCampusDistrict() {
        return campusDistrict;
    }

    private boolean attemptLogin(String username, String password, CampusDistrict campusDistrict) {
        try {
            String encodedUsername = URLEncoder.encode(username, "UTF-8"),
                    encodedPassword = URLEncoder.encode(password, "UTF-8");

            URL loginURL = new URL(campusDistrict.getDistrictBaseURL()
                    + "verify.jsp?nonBrowser=true"
                    + "&username=" + encodedUsername
                    + "&password=" + encodedPassword
                    + "&appName=" + campusDistrict.getDistrictAppName());

            print("Attempting login with url: " + loginURL);

            String response = getContent(loginURL, true);
            print("Responded with: " + response);

            if (response.trim().equalsIgnoreCase("<authentication>success</authentication>")) {
                print("Trim returning true");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getContent(URL url, boolean altercookies) {
        StringBuilder s = new StringBuilder();
        try {
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestProperty("Cookie", cookies); //Retain our session
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String input;
            while ((input = br.readLine()) != null) {
                s.append(input).append("\n");
            }
            br.close();

            StringBuilder sb = new StringBuilder();

            // find the cookies in the response header from the first request
            List<String> cookies = con.getHeaderFields().get("Set-Cookie");
            if (cookies != null) {
                for (String cookie : cookies) {
                    if (sb.length() > 0) {
                        sb.append("; ");
                    }

                    // only want the first part of the cookie header that has the value
                    String value = cookie.split(";")[0];
                    sb.append(value);
                }
            }
            if (altercookies)
                this.cookies = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s.toString();
    }
}
