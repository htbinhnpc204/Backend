package com.htbinh.backend.Controller;

import com.htbinh.backend.Model.*;
import com.htbinh.backend.SessionHelper;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

@RestController
public class DaoTaoController {

    private static final String tuitionURL = "http://daotao.ute.udn.vn/hpstatus.asp";
    private static final String scheduleURL = "http://daotao.ute.udn.vn/svtkb.asp?masv=";
    private static final String hkResult = "http://daotao.ute.udn.vn/svtranscript.asp";
    private static final String informationURL = "http://daotao.ute.udn.vn/studentInfo.asp";
    private static final String examScheduleURL = "http://daotao.ute.udn.vn/examTimeSv.asp";

    private Document tuitionDoc, scheduleDoc, infoDoc, hkResultDoc;
    private Document examDoc;

    @GetMapping("/sinhvien/getfee/{msv}")
    public ArrayList<TuitionModel> getTuition(@PathVariable String msv) {
        DecimalFormat df = new DecimalFormat("###,###,###");
        getSupport("tuition", msv);
        ArrayList<TuitionModel> result = new ArrayList<>();
        if (tuitionDoc != null) {
            Element parent = tuitionDoc.select("td#pagemain").select("div").get(1);
            parent.select("br").remove();
            for (Element table : parent.select("table")) {
                ArrayList<String> temp = new ArrayList<>();
                String name = Jsoup.parse(table.previousSibling().toString()).text().split(":")[1].trim();
                temp.add(name);
                for (Element row : table.select("tr")) {
                    temp.add(row.text());
                }

                int stc = Integer.parseInt(temp.get(1).split(":")[1].trim());
                String hocPhi = temp.get(2).split(":")[1] + " VNĐ";
                String tongHocPhi = temp.get(4).split(":")[1] + " VNĐ";

                int hocPhiInt = Integer.parseInt(hocPhi.replaceAll("\\D", ""));
                int tongHocPhiInt = Integer.parseInt(tongHocPhi.replaceAll("\\D", ""));

                int noKyTruoc = tongHocPhiInt - hocPhiInt;

                String noKyTruocString = df.format(noKyTruoc) + " VNĐ";


                result.add(new TuitionModel(temp.get(0), stc, hocPhi, noKyTruocString, tongHocPhi));
            }
        }
        return result;
    }

    @GetMapping("/sinhvien/schedule/{msv}")
    public ArrayList<ScheduleModel> getSchedule(@PathVariable String msv) {
        try {
            scheduleDoc = Jsoup.connect(scheduleURL + msv).get();
        } catch (Exception ex) {
            scheduleDoc = null;
        }
        ArrayList<ScheduleModel> result = new ArrayList<>();
        Element table = scheduleDoc.select("table").get(1);
        int i = -1;
        for (Element row : table.select("tr")) {
            if (i == -1) {
                i += 1;
                continue;
            }
            ArrayList<String> temp = new ArrayList<>();
            for (Element col : row.select("td")) {
                temp.add(col.text().trim());
            }
            String tenHp = temp.get(1);
            String tenLopHp = temp.get(0);
            String giangVien = temp.get(5);
            String Thu = Integer.parseInt(temp.get(2)) == 8 ? "Chủ nhật" : "Thứ " + temp.get(2);
            String Tiet = temp.get(3) + " - " + temp.get(4);
            String Phong = temp.get(6);

            result.add(new ScheduleModel(tenHp, tenLopHp, giangVien, Thu, Tiet, Phong));
        }

        return result;
    }

    @GetMapping("/sinhvien/examSchedule/{msv}")
    public ArrayList<ExamScheduleModel> getExamSchedule(@PathVariable String msv) {
        getSupport("exam", msv);
        ArrayList<ExamScheduleModel> result = new ArrayList<>();
        Element table = examDoc.select("table").get(2);
        int i = -1;
        for (Element row : table.select("tr")) {
            if (i == -1) {
                i += 1;
                continue;
            }
            ArrayList<String> temp = new ArrayList<>();
            for (Element col : row.select("td")) {
                temp.add(col.text().trim());
            }
            String tenHp = temp.get(1);
            String tenLopHp = temp.get(0);
            String giangVien = temp.get(2);
            String ngayThi = temp.get(3);
            String gioThi = temp.get(4);
            String Phong = temp.get(5);

            result.add(new ExamScheduleModel(ngayThi, tenLopHp, tenHp, giangVien, gioThi, Phong));
        }
        return result;
    }

    @GetMapping("/getNews")
    public ArrayList<NewsModel> getNews() {
        String baseURL = "https://ute.udn.vn/";
        String newsURL = "default.aspx";

        ArrayList<NewsModel> result = new ArrayList<>();
        try {
            Connection.Response res = Jsoup.connect(baseURL + newsURL).method(Connection.Method.GET).execute();
            Document raw = res.parse();

            Elements magazine_items = raw.select("div.row-articles");
            for (Element articles :
                    magazine_items) {
                for (Element item :
                        articles.select("div.magazine-item")) {
                    String title = item.select("h3").text();
                    String description = item.select("div.magazine-item-ct").text();
                    String published_date = item.select("dd.published").text();
                    String imageLink = baseURL + item.select("div.item-image").select("img").first().attr("src");
                    String detailsLink = baseURL + item.select("a").first().attr("href");

                    result.add(new NewsModel(title, description, published_date, imageLink, detailsLink));
                }
            }
        } catch (Exception ex) {
        }
        return result;
    }

    @GetMapping("/getNewsDetails")
    public String getNewsDetails(@RequestParam String URL) {
        String baseURL = "https://ute.udn.vn";
        String result = "";
        try {
            Connection.Response res = Jsoup.connect(URL).method(Connection.Method.GET).execute();
            Document raw = res.parse();
            for (Element img :
                    raw.select("img")) {
                img.attr("src", baseURL + img.attr("src"));
            }
            result = raw.select("section.article-full").html();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        return result;
    }

    @GetMapping("/sinhvien/hkresult/{msv}")
    public ArrayList<KetQuaHocTapModel> getHkResult(@PathVariable String msv) {
        getSupport("result", msv);
        ArrayList<KetQuaHocTapModel> result = new ArrayList<>();
        if(hkResultDoc != null){
            Element Intro = hkResultDoc.select("table").get(6);
            for(int i = 1; i < Intro.select("tr").size(); i++){
                Element row = Intro.select("tr").get(i);
                ArrayList<String> temp = new ArrayList<>();
                for(Element e : row.select("td")){
                    temp.add(e.text());
                }
                float dtbHocKy = Float.parseFloat(temp.get(2));
                float dtbHocBong = (float)(Math.round((dtbHocKy * 10 / 4.0) * 100)/100.0);
                result.add(new KetQuaHocTapModel(temp.get(0), temp.get(1), temp.get(4), dtbHocKy, dtbHocBong));
            }
            return result;
        }
        return null;
    }

    @GetMapping("/sinhvien/hkresult/details/{msv}")
    public ArrayList<KetQuaHocTapChiTietModel> getResultDetails(@PathVariable String msv, @RequestParam String maHk){
        getSupport("result", msv);
        ArrayList<KetQuaHocTapChiTietModel> result = new ArrayList<>();
        if(hkResultDoc != null) {
            Element Details = hkResultDoc.select("table").get(4);
            int cRow = 1;
            for (Element row:
                 Details.select("tr")) {
                if(cRow == 1){ cRow += 1; continue;}
                Elements cell = row.select("td");
                if(cell.get(6).text().equals(maHk)){
                    float dtk = Float.parseFloat(cell.get(4).text());
                    int tinchi = Integer.parseInt(cell.get(3).text());
                    result.add(new KetQuaHocTapChiTietModel(cell.get(2).text(), cell.get(0).text(), tinchi, dtk, cell.get(5).text()));
                }
            }
            return result;
        }
        return null;
    }

    @GetMapping("/sinhvien/info/{msv}")
    public StudentModel getInfo(@PathVariable String msv) {
        getSupport("info", msv);
        ArrayList<String> basicInfo = new ArrayList<>();
        if (infoDoc != null) {
            Elements infoRows = infoDoc.select("#pagemain").select("form").get(0).select("tr");
            Element basic = infoRows.get(1);
            String[] temp = basic.html().split("<br>");
            for (String s : temp) {
                s = Jsoup.parse(s.trim()).text().split(":")[1].trim();
                basicInfo.add(s);
            }
            for (int i = 2; i < infoRows.size() - 1; i++) {
                String s = infoRows.get(i).text();
                Elements input = infoRows.get(i).select("input");
                if (input.size() > 0) {
                    s += input.get(0).val();
                }
                if(s.split(":").length >= 2){
                    basicInfo.add(s.split(":")[1].trim());
                }
                else{
                    basicInfo.add("Chưa cập nhật");
                }
            }
            String nganh = getNganh(msv);
            basicInfo.add(nganh);
        } else {
            return null;
        }
        return new StudentModel(msv, basicInfo.get(1), getLop(msv), getNganh(msv), basicInfo.get(2),
                basicInfo.get(8), basicInfo.get(3), basicInfo.get(5), basicInfo.get(6));
    }

    private String getNganh(String msv) {
        String result = "";
        getSupport("result", msv);
        if(hkResultDoc == null){
            return "-1";
        }
        Document tempDoc = hkResultDoc;
        result = tempDoc.select("#pagemain").select("div").get(0).select("b").get(1).text().trim();

        return result;
    }

    private String getLop(String msv){
        return msv.substring(0,2) + "T" + msv.substring(10,11);
    }

    private void getSupport(String s, String msv) {
        try {
            Map<String, String> cookie = LoginController.freshLogin(SessionHelper.getUserByID(msv));
            if (cookie != null) {
                switch (s) {
                    case "exam":
                        examDoc = Jsoup.connect(examScheduleURL).cookies(cookie).data("masv", msv).post();
                        break;
                    case "info":
                        infoDoc = Jsoup.connect(informationURL).cookies(cookie).get();
                        break;
                    case "result":
                        hkResultDoc = Jsoup.connect(hkResult).cookies(cookie).get();
                        break;
                    case "tuition":
                        tuitionDoc = Jsoup.connect(tuitionURL).cookies(cookie).get();
                        break;
                }
            }

        } catch (Exception e) {
        }
    }
}
