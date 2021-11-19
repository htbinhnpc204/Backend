package com.htbinh.backend.Controller;

import com.htbinh.backend.Model.*;
import com.htbinh.backend.SessionHelper;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@RestController
public class SinhVienController {

    private static final String informationURL = "https://daotao1.ute.udn.vn/thong-tin-ca-nhan.html";
    private static final String scheduleURL = "https://daotao1.ute.udn.vn/thoikhoabieu/index";
    private static final String ketQuaURL = "https://daotao1.ute.udn.vn/ket-qua-hoc-tap.html";
    private static final String notiURL = "https://daotao1.ute.udn.vn/sinhvien/thongbao/thongbaotulophocphan/";

    Map<String, String> cookies;

    Document infoDoc, scheduleDoc, ketQuaDoc;

    @GetMapping("/sinhvien/getnoti")
    public ArrayList<NotificationModel> getNoti(){
        if (checkSession()) {
            return null;
        }
        ArrayList<NotificationModel> result = new ArrayList<>();
        try{
            Document doc = getNotiDocument();
            for (Element row:
                 doc.select("tbody").select("tr")) {
                ArrayList<String> tmp = new ArrayList<>();
                for (Element item:
                     row.select("td")) {
                    tmp.add(item.text());
                }
                String link = row.select("a").attr("href");
                String from = tmp.get(2);
                String toClass = tmp.get(1).split(":")[1].replace(" ","");
                String date = tmp.get(3);
                result.add(new NotificationModel(from, toClass, date, link));
            }
        }catch(Exception ex){return null;}
        return result;
    }

    private Document getNotiDocument(){
        StringBuffer response = new StringBuffer();
        JSONObject json = new JSONObject();
        String raw = "";
        try {
            URL url = new URL(notiURL);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestProperty("cookie", "PHPSESSID=" + cookies.get("PHPSESSID") + ";token=" + cookies.get("token") + ";");
            int responseCode = http.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            json = new JSONObject(response.toString());
            byte[] utf8 = json.get("html").toString().getBytes("UTF-8");
            raw = new String(utf8, "UTF-8");
        }catch (Exception ex){}
        return Jsoup.parse(raw);
    }

    @GetMapping("/getNews")
    public ArrayList<NewsModel> getNews() {

        String newsURL = "https://ute.udn.vn/LoaiTinTuc/1/Tin-tuc-chung.aspx";

        if(SessionHelper.getListNews() != null){
            return SessionHelper.getListNews();
        }

        ArrayList<NewsModel> result = new ArrayList<>();
        try{
            Connection.Response res = Jsoup.connect(newsURL).method(Connection.Method.POST).execute();
            Document raw = res.parse();

            Elements magazine_items = raw.select("div.magazine-list")
                    .select("div.t3-content")
                    .select("div.magazine-item");

            for (Element item:
                 magazine_items) {
                String title = item.select("h3").select("a").text();
                String description = item.select("div.magazine-item-ct").select("p").first().text();
                String published_date = item.select("dd.published").text();
                String imageLink = "https://ute.udn.vn" + item.select("div.item-image").select("img").first().attr("src");
                String detailsLink = item.select("h3").select("a").first().attr("href");

                result.add(new NewsModel(title, description, published_date, imageLink, detailsLink));
            }
        }catch (Exception ex){
        }

        SessionHelper.setListNews(result);
        return result;
    }

    @GetMapping("/sinhvien/getinfo")
    public StudentModel getSinhVien() {
        if (checkSession()) {
            return null;
        }
        if(SessionHelper.getInfoSinhVien() != null){
            return SessionHelper.getInfoSinhVien();
        }

        StudentModel svModel;
        ArrayList<String> raw = new ArrayList<>();

        Elements list = infoDoc.select("input[readonly=readonly]");
        for (Element e :
                list) {
            raw.add(e.val());
        }
        svModel = new StudentModel(SessionHelper.getUser().getMsv(), raw.get(0),
                raw.get(1), raw.get(4), raw.get(3), getDob());
        SessionHelper.setInfoSinhVien(svModel);

        return svModel;
    }

    @GetMapping("/sinhvien/gettkb")
    public ArrayList<ScheduleModel> getTkb() {
        /*
//        if (!checkSession()) {
//            return null;
//        }
//        Elements body = scheduleDoc.select("tbody");
//        Elements rows = body.get(0).select("tr");
//
//        for (Element r :
//                rows) {
//            ArrayList<String> buffer = new ArrayList<>();
//            Elements cols = r.select("td");
//            for (Element c :
//                    cols) {
//                buffer.add(c.text());
//            }
//            result.add(new TkbModel(buffer.get(0), buffer.get(1), buffer.get(2),
//                    buffer.get(3), buffer.get(4), buffer.get(6), buffer.get(7)));
//        }
//        return result;*/ //for old code get Tkb

        if(checkSession()){return null;}
        if(SessionHelper.getListTkb() != null){
            return SessionHelper.getListTkb();
        }
        ArrayList<ScheduleModel> result = new ArrayList<>();
        Map<String, String> data = new HashMap<>();
        data.put("loai","sinhvien");
        data.put("maKhoa","1");
        data.put("maGiangvien","1");
        data.put("maSinhvien",SessionHelper.getUser().getMsv());

        try {
            Connection.Response res = Jsoup.connect(scheduleURL).data(data).method(Connection.Method.POST).execute();
            Document raw = res.parse();
            for (Element row:
                 raw.select("table.tblCalendar").select("tbody").first().select("tr")) {
                result.add(getEachDay(row));
            }
        } catch (IOException ioException) {
            return null;
        }
        SessionHelper.setListTkb(result);
        return result;
    }

    private ScheduleModel getEachDay(Element element){
        ScheduleModel result;
        ArrayList<String> tmp = new ArrayList<>();
        for (Element item:
                element.select("td")) {
            tmp.add(item.text());
        }
        result = new ScheduleModel(tmp.get(1), tmp.get(0), tmp.get(6), tmp.get(3), tmp.get(4) + "-" + tmp.get(5), tmp.get(2));
        return result;
    }

    @GetMapping("/sinhvien/kqhoctap")
    public ArrayList<KetQuaHocTapModel> getKq() {
        if (checkSession()) {
            return null;
        }
        if(SessionHelper.getListKq() != null){
            return SessionHelper.getListKq();
        }

        ArrayList<KetQuaHocTapModel> result = new ArrayList<>();
        Elements table = ketQuaDoc.select("table.tblKhaosat");
        Elements body = table.select("tbody");
        Elements rows = body.get(0).select("tr");
        for (Element r :
                rows) {
            ArrayList<String> buffer = new ArrayList<>();
            Elements cols = r.select("td");
            for (Element c :
                    cols) {
                buffer.add(c.text());
            }

            float dtbHocKy = Float.parseFloat(buffer.get(3));
            float dtbHocBong = Float.parseFloat(buffer.get(5));

            result.add(new KetQuaHocTapModel(buffer.get(0), buffer.get(2), buffer.get(6), dtbHocKy, dtbHocBong));
        }
        SessionHelper.setListKq(result);
        return result;
    }

    @GetMapping("/sinhvien/kqhoctap/chitiet")
    public ArrayList<KetQuaHocTapChiTietModel> getChiTietKq(@RequestParam String hocKy) {
        if (checkSession()) {
            return null;
        }
        if(SessionHelper.getListKqChiTiet() != null){
            return SessionHelper.getListKqChiTiet();
        }

        ArrayList<KetQuaHocTapChiTietModel> result = new ArrayList<>();
        Elements elements = ketQuaDoc.select("table.tblKhaosat").select("tr.tr-namhoc-hocky");
        Element startElement = null;
        for (Element e :
                elements) {
            Elements hockyName = e.select("b");
            if (hockyName.first().text().replaceAll(" |-|:", "").equalsIgnoreCase(hocKy)) {
                startElement = e;
                break;
            }
        }
        if (startElement == null) {
            return null;
        } else {
            int i = 1;
            Element nextRow = startElement;
            do {
                nextRow = nextRow.nextElementSibling();
                Elements row = nextRow.select("td");
                row.select("span").remove();
                if(row.size() == 1){break;}

                String tenMH = row.get(2).text();
                String maHP = row.get(1).text();
                int soTc = (int)Float.parseFloat(row.get(3).text());
                float diemCC = Float.parseFloat(row.get(4).text().equals("") ? "-1" : row.get(4).text());
                float diemGK = Float.parseFloat(row.get(7).text().equals("") ? "-1" : row.get(7).text());
                float diemCK = Float.parseFloat(row.get(8).text().equals("") ? "-1" : row.get(8).text());
                float diemTK = Float.parseFloat(row.get(19).text().equals("") ? "-1" : row.get(19).text());
                String diemChu = row.get(20).text();

                result.add(new KetQuaHocTapChiTietModel(tenMH, maHP, soTc, diemCC, diemGK, diemCK, diemTK, diemChu));

            } while (true);
        }
        SessionHelper.setListKqChiTiet(result);
        return result;
    }

    @GetMapping("/getselectoption")
    public ArrayList<String> getOption() {
        ArrayList<String> result = new ArrayList<>();
        if (checkSession()) {
            return null;
        }

        Elements selector = ketQuaDoc.select("select.hockynamhoc").select("option");
        for (Element option :
                selector) {
            result.add(option.text());
        }

        return result;
    }

    private String getDob() {
        Elements selected = infoDoc.select("select option[selected]");
        String tmp = selected.get(1).text();
        String month = Integer.parseInt(tmp) < 10 ? "0" + tmp : tmp;
        return selected.get(0).text() + "/" + month + "/" + selected.get(2).text();
    }

    private boolean checkSession() {
        if (SessionHelper.getCookies() != null) {
            cookies = SessionHelper.getCookies();
            try {
                if (checkNull()){
                    scheduleDoc = Jsoup.connect(scheduleURL).cookies(cookies).get();
                    infoDoc = Jsoup.connect(informationURL).cookies(cookies).get();
                    ketQuaDoc = Jsoup.connect(ketQuaURL).cookies(cookies).get();
                }
            } catch (Exception ex) {
                return true;
            }
        }
        return false;
    }

    private boolean checkNull(){
        return scheduleDoc == null || infoDoc == null || ketQuaDoc == null;
    }
}
