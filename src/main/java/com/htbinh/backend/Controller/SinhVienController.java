package com.htbinh.backend.Controller;

import com.htbinh.backend.Model.KetQuaHocTapChiTietModel;
import com.htbinh.backend.Model.KetQuaHocTapModel;
import com.htbinh.backend.Model.SinhVienModel;
import com.htbinh.backend.Model.TkbModel;
import com.htbinh.backend.SessionHelper;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.print.Doc;
import java.io.IOException;
import java.util.*;

@RestController
public class SinhVienController {

    private static final String informationURL = "https://daotao1.ute.udn.vn/thong-tin-ca-nhan.html";
    private static final String scheduleURL = "https://daotao1.ute.udn.vn/thoikhoabieu/index";
    private static final String ketQuaURL = "https://daotao1.ute.udn.vn/ket-qua-hoc-tap.html";

    Document infoDoc = null, scheduleDoc = null, ketQuaDoc = null;


    @GetMapping("/sinhvien/getinfo")
    public SinhVienModel getSinhVien() {
        SinhVienModel svModel = null;
        ArrayList<String> raw = new ArrayList<>();
        if (!checkSession()) {
            return null;
        }
        Elements list = infoDoc.select("input[readonly=readonly]");
        for (Element e :
                list) {
            raw.add(e.val());
        }
        svModel = new SinhVienModel(SessionHelper.getSv().getMsv(), raw.get(0),
                raw.get(1), raw.get(4), raw.get(3), getDob());
        return svModel;
    }

    @GetMapping("/sinhvien/gettkb")
    public ArrayList<TkbModel> getTkb() {
        ArrayList<TkbModel> result = new ArrayList<>();
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
//        return result;

        if(!checkSession()){return null;}

        Map<String, String> data = new HashMap<>();
        data.put("loai","sinhvien");
        data.put("maKhoa","1");
        data.put("maGiangvien","1");
        data.put("maSinhvien",SessionHelper.getSv().getMsv());

        try {
            Connection.Response res = Jsoup.connect(scheduleURL).data(data).method(Connection.Method.POST).execute();
            Document raw = res.parse();
            for (Element row:
                 raw.select("tblCalendar").select("tbody").first().select("tr")) {
                result.add(getEachDay(row));
            }
        } catch (IOException ioException) {
            return null;
        }
        return result;
    }

    private TkbModel getEachDay(Element element){
        TkbModel result;
        ArrayList<String> tmp = new ArrayList<>();
        for (Element item:
                element.select("td")) {
            tmp.add(item.text());
        }
        result = new TkbModel(tmp.get(2), tmp.get(0), tmp.get(1), tmp.get(4), tmp.get(5) + " - " tmp.get(6), tmp.get(3));

        return result;
    }

    @GetMapping("/sinhvien/kqhoctap")
    public ArrayList<KetQuaHocTapModel> getKq() {
        ArrayList<KetQuaHocTapModel> result = new ArrayList<>();
        if (!checkSession()) {
            return null;
        }

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

        return result;
    }

    @GetMapping("/sinhvien/kqhoctap/chitiet")
    public ArrayList<KetQuaHocTapChiTietModel> getChiTietKq(@RequestParam String hocKy) {
        ArrayList<KetQuaHocTapChiTietModel> result = new ArrayList<>();
        if (!checkSession()) {
            return null;
        }
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
                float diemCC = Float.parseFloat(row.get(4).text() == "" ? "-1" : row.get(4).text());
                float diemGK = Float.parseFloat(row.get(7).text() == "" ? "-1" : row.get(7).text());
                float diemCK = Float.parseFloat(row.get(8).text() == "" ? "-1" : row.get(8).text());
                float diemTK = Float.parseFloat(row.get(19).text() == "" ? "-1" : row.get(19).text());
                String diemChu = row.get(20).text();

                result.add(new KetQuaHocTapChiTietModel(tenMH, maHP, soTc, diemCC, diemGK, diemCK, diemTK, diemChu));

            } while (true);
        }
        return result;
    }

    @GetMapping("/getselectoption")
    public ArrayList<String> getOption() {
        ArrayList<String> result = new ArrayList<>();
        if (!checkSession()) {
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
            Map<String, String> cookies = SessionHelper.getCookies();
            try {
                if (scheduleDoc == null || infoDoc == null || ketQuaDoc == null) {
                    scheduleDoc = Jsoup.connect(scheduleURL).cookies(cookies).get();
                    infoDoc = Jsoup.connect(informationURL).cookies(cookies).get();
                    ketQuaDoc = Jsoup.connect(ketQuaURL).cookies(cookies).get();
                }
            } catch (Exception ex) {
                return false;
            }
        }
        return true;
    }
}
