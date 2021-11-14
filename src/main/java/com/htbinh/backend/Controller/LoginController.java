package com.htbinh.backend.Controller;

import com.htbinh.backend.Model.LoginModel;
import com.htbinh.backend.SessionHelper;
import io.sentry.Sentry;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/login")
public class LoginController {

    static String loginUrl = "https://daotao1.ute.udn.vn/dang-nhap.html";

    @PostMapping()
    public boolean doLogin(@RequestBody LoginModel user){
        try {
            Connection.Response res = Jsoup.connect(loginUrl).data("username", user.getMsv(),
                    "password", user.getPassword()).method(Connection.Method.POST).execute();
            SessionHelper.setCookies(res.cookies());
            SessionHelper.setUser(user);
            return checkLogin(res.body());
        } catch (IOException ioException) {
            Sentry.captureException(ioException);
            return false;
        }
    }

    private static boolean checkLogin(String sources){
        return sources.contains("success");
    }
}
