package com.htbinh.backend.Controller;

import com.htbinh.backend.Model.LoginModel;
import com.htbinh.backend.MultiSession;
import com.htbinh.backend.SessionHelper;
import io.sentry.Sentry;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.web.bind.annotation.*;

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

            if(MultiSession.sessionDic == null){
                MultiSession.init();
            }

            MultiSession.sessionDic.put(user.getMsv(), new SessionHelper(res.cookies(), user));
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
