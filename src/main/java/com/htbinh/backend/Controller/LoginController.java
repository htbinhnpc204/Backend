package com.htbinh.backend.Controller;

import com.htbinh.backend.Model.LoginModel;
import com.htbinh.backend.MultiSession;
import com.htbinh.backend.SessionHelper;
import io.sentry.Sentry;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    //    static String loginUrl = "https://daotao1.ute.udn.vn/dang-nhap.html";
    static String loginUrl = "http://daotao.ute.udn.vn/svlogin.asp";

    static Connection.Response getResponse(LoginModel user) {

        if (user == null){
            return null;
        }

        try {
            return Jsoup.connect(loginUrl)
                    .data("maSV", user.getMsv())
                    .data("pw", user.getPassword())
                    .method(Connection.Method.POST)
                    .execute();
        } catch (Exception ioException) {
            Sentry.captureException(ioException);
            SessionHelper.setNotification("Request timeout");
            return null;
        }
    }

    @PostMapping()
    public String doLogin(@RequestBody LoginModel user) {
        Connection.Response res = getResponse(user);
        if (res == null) {
            if(SessionHelper.getNotification() == null){
                return "false";
            }
            else if (SessionHelper.getNotification().contains("timeout")){
                return "Vui lòng thử lại sau";
            }
        }
        if(SessionHelper.getUserByID(user.getMsv()) == null){
            SessionHelper.setUser(user);
        }
        return checkLogin(res.body());
    }

    public static Map<String, String> freshLogin(LoginModel user) {
        Connection.Response res = getResponse(user);
        if(res == null){
            return null;
        }
        return res.cookies();
    }

    private static String checkLogin(String sources) {
        return sources.toLowerCase().contains("chào sinh viên") ? "true" : "false";
    }
}
