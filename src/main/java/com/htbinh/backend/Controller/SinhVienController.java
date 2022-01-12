//package com.htbinh.backend.Controller;
//
//import com.htbinh.backend.Model.*;
//import com.htbinh.backend.MultiSession;
//import com.htbinh.backend.SessionHelper;
//import org.json.JSONObject;
//import org.jsoup.Connection;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//public class SinhVienController {
//
//    private static final String informationURL = "https://daotao1.ute.udn.vn/thong-tin-ca-nhan.html";
//    private static final String scheduleURL = "https://daotao1.ute.udn.vn/thoikhoabieu/index";
//    private static final String ketQuaURL = "https://daotao1.ute.udn.vn/ket-qua-hoc-tap.html";
//    private static final String notiURL = "https://daotao1.ute.udn.vn/sinhvien/thongbao/thongbaotulophocphan/";
//    private static final String tuitionURL = "http://daotao.ute.udn.vn/hpstatus.asp";
//
//    Map<String, String> cookies;
//
//    Document infoDoc, scheduleDoc, ketQuaDoc, tuitionDoc;
//
//    @GetMapping("/sinhvien/getfee")
//    public ArrayList<TuitionModel> getTuition(@RequestParam String SessionID) {
//
//        SessionHelper sh = MultiSession.getSessionByID(SessionID);
//
//        if (sh.getListTuition() != null) {
//            return sh.getListTuition();
//        }
//
//        getSupport("tuition", sh);
//
//        ArrayList<TuitionModel> result = new ArrayList<>();
////        Elements items = tuitionDoc.select("div.hocphi_item");
//        Elements items = tuitionDoc.select("#pagemain > div:eq(1) > div:eq(1) > table");
//        for (Element item :
//                items) {
//            ArrayList<String> buffer = new ArrayList<>();
//            String hocKy = item.select("h3").get(0).text();
//            Elements cols = item.select("tr");
//            for (Element r :
//                    cols) {
//                Element text = r.select("td").get(1);
//                buffer.add(text.text());
//            }
//
//            int soTinChi = (int) Float.parseFloat(buffer.get(0));
//            String hocPhi = buffer.get(2);
//            String noKyTruoc = buffer.get(7);
//            String duKyTruoc = buffer.get(8);
//            String tong = buffer.get(9);
//
//            result.add(new TuitionModel(hocKy, soTinChi, hocPhi, noKyTruoc, duKyTruoc, tong));
//        }
//        sh.setListTuition(result);
//        return result;
//    }
//
//    @GetMapping("/sinhvien/getnoti")
//    public ArrayList<NotificationModel> getNoti() {
//        ArrayList<NotificationModel> result = new ArrayList<>();
//        try {
//            Document doc = getNotiDocument();
//            for (Element row :
//                    doc.select("tbody").select("tr")) {
//                ArrayList<String> tmp = new ArrayList<>();
//                for (Element item :
//                        row.select("td")) {
//                    tmp.add(item.text());
//                }
//                String link = row.select("a").attr("href");
//                String from = tmp.get(2);
//                String toClass = tmp.get(1).split(":")[1].replace(" ", "");
//                String date = tmp.get(3);
//
//                Document tmpDoc = Jsoup.connect(link).cookies(cookies).get();
//
//                String details = tmpDoc.select("tbody").select("tr").get(2).select("td").get(1).text();
//
//                result.add(new NotificationModel(from, toClass, date, details));
//            }
//        } catch (Exception ex) {
//            return null;
//        }
//        return result;
//    }
//
//    private Document getNotiDocument() {
//        StringBuffer response = new StringBuffer();
//        JSONObject json = new JSONObject();
//        String raw = "";
//        try {
//            URL url = new URL(notiURL);
//            HttpURLConnection http = (HttpURLConnection) url.openConnection();
//            http.setRequestProperty("cookie", "PHPSESSID=" + cookies.get("PHPSESSID") + ";token=" + cookies.get("token") + ";");
//            int responseCode = http.getResponseCode();
//            BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
//            String inputLine;
//            while ((inputLine = in.readLine()) != null) {
//                response.append(inputLine);
//            }
//            in.close();
//            json = new JSONObject(response.toString());
//            byte[] utf8 = json.get("html").toString().getBytes("UTF-8");
//            raw = new String(utf8, "UTF-8");
//        } catch (Exception ex) {
//        }
//        return Jsoup.parse(raw);
//    }
//
//    @GetMapping("/getNews")
//    public ArrayList<NewsModel> getNews(@RequestParam String SessionID) {
//        String baseURL = "https://ute.udn.vn/";
//        String newsURL = "default.aspx";
//
//        SessionHelper sh = MultiSession.getSessionByID(SessionID);
//
//        if (sh.getListNews() != null) {
//            return sh.getListNews();
//        }
//
//        ArrayList<NewsModel> result = new ArrayList<>();
//        try {
//            Connection.Response res = Jsoup.connect(baseURL + newsURL).method(Connection.Method.GET).execute();
//            Document raw = res.parse();
//
//            Elements magazine_items = raw.select("div.row-articles");
//            for (Element articles :
//                    magazine_items) {
//                for (Element item :
//                        articles.select("div.magazine-item")) {
//                    String title = item.select("h3").text();
//                    String description = item.select("div.magazine-item-ct").text();
//                    String published_date = item.select("dd.published").text();
//                    String imageLink = baseURL + item.select("div.item-image").select("img").first().attr("src");
//                    String detailsLink = baseURL + item.select("a").first().attr("href");
//
//                    result.add(new NewsModel(title, description, published_date, imageLink, detailsLink));
//                }
//            }
//            sh.setListNews(result);
//        } catch (Exception ex) {
//            System.out.println(ex.toString());
//        }
//
//        return result;
//    }
//
//    @GetMapping("/getNewsDetails")
//    public String getNewsDetails(@RequestParam String URL) {
//        String baseURL = "https://ute.udn.vn";
//        String result = "";
//        try {
//            Connection.Response res = Jsoup.connect(URL).method(Connection.Method.GET).execute();
//            Document raw = res.parse();
//            for (Element img :
//                    raw.select("img")) {
//                img.attr("src", baseURL + img.attr("src"));
//            }
//            result = raw.select("section.article-full").html();
//        } catch (Exception ex) {
//            System.out.println(ex.toString());
//        }
//
//        return result;
//    }
//
//    @GetMapping("/sinhvien/getinfo")
//    public StudentModel getStudentInfo(@RequestParam String SessionID) {
//
//        SessionHelper sh = MultiSession.getSessionByID(SessionID);
//
//        StudentModel svModel;
//        ArrayList<String> raw = new ArrayList<>();
//
//        getSupport("info", sh);
//
//        Elements list = infoDoc.select("input[readonly=readonly]");
//        for (Element e :
//                list) {
//            raw.add(e.val());
//        }
//
//        String soCMND = infoDoc.select("input[name=vdata[cmnd]]").val().equals("") ? "Chưa cập nhật" : infoDoc.select("input[name=vdata[cmnd]]").val();
//        String noiSinh = infoDoc.select("input[name=vdata[noisinh]]").val().equals("") ? "Chưa cập nhật" : infoDoc.select("input[name=vdata[noisinh]]").val();
//        String soDienThoai = infoDoc.select("input[name=vdata[didong]]").val().equals("") ? "Chưa cập nhật" : infoDoc.select("input[name=vdata[didong]]").val();
//        String Email = infoDoc.select("input[name=vdata[email]]").val().equals("") ? "Chưa cập nhật" : infoDoc.select("input[name=vdata[email]]").val();
//        String avtLink = infoDoc.select("img.image-avatar").get(0).attr("src");
//        svModel = new StudentModel(sh.getUser().getMsv(), raw.get(0),
//                raw.get(1), raw.get(4), raw.get(3), getDob(), soCMND, noiSinh, soDienThoai, Email, avtLink);
//
//        return svModel;
//    }
//
//    @GetMapping("/sinhvien/lichthi")
//    public ArrayList<ExamScheduleModel> getExamSchedules() {
//        //TODO
//        //CheckThat: Học kỳ hiện tại chưa có lịch thi.
//        return null;
//    }
//
//    @GetMapping("/sinhvien/gettkb")
//    public ArrayList<ScheduleModel> getSchedule(@RequestParam String SessionID) {
//        /*
////        if (!checkSession()) {
////            return null;
////        }
////        Elements body = scheduleDoc.select("tbody");
////        Elements rows = body.get(0).select("tr");
////
////        for (Element r :
////                rows) {
////            ArrayList<String> buffer = new ArrayList<>();
////            Elements cols = r.select("td");
////            for (Element c :
////                    cols) {
////                buffer.add(c.text());
////            }
////            result.add(new TkbModel(buffer.get(0), buffer.get(1), buffer.get(2),
////                    buffer.get(3), buffer.get(4), buffer.get(6), buffer.get(7)));
////        }
////        return result;*/ //for old code get Tkb
//
//        SessionHelper sh = MultiSession.getSessionByID(SessionID);
//
//        if (sh.getListTkb() != null) {
//            return sh.getListTkb();
//        }
//
//        ArrayList<ScheduleModel> result = new ArrayList<>();
//        Map<String, String> data = new HashMap<>();
//        data.put("loai", "sinhvien");
//        data.put("maKhoa", "1");
//        data.put("maGiangvien", "1");
//        data.put("maSinhvien", sh.getUser().getMsv());
//
//        try {
//            Connection.Response res = Jsoup.connect(scheduleURL).data(data).method(Connection.Method.POST).execute();
//            Document raw = res.parse();
//            for (Element row :
//                    raw.select("table.tblCalendar").select("tbody").first().select("tr")) {
//                result.add(getEachDay(row));
//            }
//        } catch (IOException ioException) {
//            return null;
//        }
//        sh.setListTkb(result);
//        return result;
//    }
//
//    private ScheduleModel getEachDay(Element element) {
//        ScheduleModel result;
//        ArrayList<String> tmp = new ArrayList<>();
//        for (Element item :
//                element.select("td")) {
//            tmp.add(item.text());
//        }
//        result = new ScheduleModel(tmp.get(1), tmp.get(0), tmp.get(6), tmp.get(3), tmp.get(4) + "-" + tmp.get(5), tmp.get(2));
//        return result;
//    }
//
//    @GetMapping("/sinhvien/kqhoctap")
//    public ArrayList<KetQuaHocTapModel> getResult(@RequestParam String SessionID) {
//        SessionHelper sh = MultiSession.getSessionByID(SessionID);
//        getSupport("result", sh);
//        if (sh.getListKq() != null) {
//            return sh.getListKq();
//        }
//
//        ArrayList<KetQuaHocTapModel> result = new ArrayList<>();
//        Elements table = ketQuaDoc.select("table.tblKhaosat");
//        Elements body = table.select("tbody");
//        Elements rows = body.get(0).select("tr");
//        for (Element r :
//                rows) {
//            ArrayList<String> buffer = new ArrayList<>();
//            Elements cols = r.select("td");
//            for (Element c :
//                    cols) {
//                buffer.add(c.text());
//            }
//
//            float dtbHocKy = Float.parseFloat(buffer.get(3));
//            float dtbHocBong = Float.parseFloat(buffer.get(5));
//
//            result.add(new KetQuaHocTapModel(buffer.get(0), buffer.get(2), buffer.get(6), dtbHocKy, dtbHocBong));
//        }
//        sh.setListKq(result);
//        return result;
//    }
//
//    @GetMapping("/sinhvien/kqhoctap/chitiet")
//    public ArrayList<KetQuaHocTapChiTietModel> getResultDetails(@RequestParam String hocKy, @RequestParam String SessionID) {
//        SessionHelper sh = MultiSession.getSessionByID(SessionID);
//
//        if (sh.getListKqChiTiet() != null) {
//            return sh.getListKqChiTiet();
//        }
//
//        ArrayList<KetQuaHocTapChiTietModel> result = new ArrayList<>();
//        Elements elements = ketQuaDoc.select("table.tblKhaosat").select("tr.tr-namhoc-hocky");
//        Element startElement = null;
//        for (Element e :
//                elements) {
//            Elements hockyName = e.select("b");
//            if (hockyName.first().text().replaceAll("\\D", "").equalsIgnoreCase(hocKy)) {
//                startElement = e;
//                break;
//            }
//        }
//        if (startElement == null) {
//            return null;
//        } else {
//            int i = 1;
//            Element nextRow = startElement;
//            do {
//                nextRow = nextRow.nextElementSibling();
//                Elements row = nextRow.select("td");
//                row.select("span").remove();
//                if (row.size() == 1) {
//                    break;
//                }
//
//                String tenMH = row.get(2).text();
//                String maHP = row.get(1).text();
//                int soTc = (int) Float.parseFloat(row.get(3).text());
//                float diemCC = Float.parseFloat(row.get(4).text().equals("") ? "-1" : row.get(4).text());
//                float diemGK = Float.parseFloat(row.get(7).text().equals("") ? "-1" : row.get(7).text());
//                float diemCK = Float.parseFloat(row.get(8).text().equals("") ? "-1" : row.get(8).text());
//                float diemTK = Float.parseFloat(row.get(19).text().equals("") ? "-1" : row.get(19).text());
//                String diemChu = row.get(20).text();
//
//                result.add(new KetQuaHocTapChiTietModel(tenMH, maHP, soTc, diemCC, diemGK, diemCK, diemTK, diemChu));
//
//            } while (true);
//        }
//        sh.setListKqChiTiet(result);
//        return result;
//    }
//
//    @GetMapping("/getselectoption")
//    public ArrayList<String> getOption() {
//        ArrayList<String> result = new ArrayList<>();
//
//        Elements selector = ketQuaDoc.select("select.hockynamhoc").select("option");
//        for (Element option :
//                selector) {
//            result.add(option.text());
//        }
//
//        return result;
//    }
//
//    private String getDob() {
//        Elements selected = infoDoc.select("select option[selected]");
//        String tmp = selected.get(1).text();
//        String month = Integer.parseInt(tmp) < 10 ? "0" + tmp : tmp;
//        return selected.get(0).text() + "/" + month + "/" + selected.get(2).text();
//    }
//
//    private void getSupport(String s, SessionHelper sh) {
//        cookies = sh.getCookies();
//        try {
//            switch (s) {
//                case "info":
//                    infoDoc = Jsoup.connect(informationURL).cookies(cookies).get();
//                    break;
//                case "schedule":
//                    scheduleDoc = Jsoup.connect(scheduleURL).cookies(cookies).get();
//                    break;
//                case "result":
//                    ketQuaDoc = Jsoup.connect(ketQuaURL).cookies(cookies).get();
//                    break;
//                case "tuition":
//                    tuitionDoc = Jsoup.connect(tuitionURL).cookies(cookies).get();
//                    break;
//            }
//        } catch (Exception e) {
//        }
//    }
//}
