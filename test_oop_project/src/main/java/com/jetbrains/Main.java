package com.jetbrains;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import java.util.*;
import java.io.IOException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static java.lang.constant.ConstantDescs.NULL;

public class Main {public static void main(String[] args) throws IOException {
    // the URL of the target website's home page
    String baseUrl = "https://nguoikesu.com/tu-lieu/quan-su/tran-ngoc-hoi-dong-da-nam-1789";

    // initializing the list of SuKienLichSu data objects
    // that will contain the scraped data
    List<SuKienLichSu> cacSuKienLichSu = new ArrayList<>();

    // downloading the target website with an HTTP GET request
    Document doc = Jsoup
            .connect(baseUrl)
            .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36")
            .get();
    Elements nextElements = doc.select("a.btn.btn-sm.btn-secondary.next");
    String completeUrl = "";
    // looking for the "Next →" HTML element
    while (baseUrl.compareTo(completeUrl)!=0 && (!nextElements.isEmpty())) {
            // getting the "Next →" HTML element
            Element nextElement = nextElements.first();
            // extracting the relative URL of the next page
            String relativeUrl = nextElement.attr("href");
            // building the complete URL of the next page
            completeUrl =  "https://nguoikesu.com" + relativeUrl;
            System.out.println(completeUrl);

            doc = Jsoup
                    .connect(completeUrl)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36")
                    .get();

        // retrieving the list of product HTML elements
        // selecting all quote HTML elements
        SuKienLichSu skls = new SuKienLichSu();
        //Title
        String tenSuKien = doc.selectXpath("//*[@id=\"content\"]/div[2]/div[1]").text();
        skls.setTen(tenSuKien);
        System.out.println(tenSuKien);

        //Time, Place, Outcomes
        Elements elements = doc.select("table[cellpadding='0'] tr");
        String[] attributeValue = new String[elements.size()];
        int i = 0;
        for (Element element: elements) {
            attributeValue[i] = element.select("td:nth-child(2)").text();
            i++;
        }
        String thoiGian = "";
        String diaDiem = "";
        String ketQua = "";
        if (attributeValue.length!=0){
            thoiGian = attributeValue[0];
            diaDiem = attributeValue[1];
            ketQua = attributeValue[2];
            skls.setThoiGian(thoiGian);
            skls.setDiaDiem(diaDiem);
            skls.setKetQua(ketQua);
        }
        skls.setThoiGian(thoiGian);
        skls.setDiaDiem(diaDiem);
        skls.setKetQua(ketQua);

        //Historical Figures
        ArrayList<String> nhanVatLichSu = new ArrayList<>();
        Elements table = doc.select("table[cellpadding='2'] ");
        Elements rows = table.select("tr:nth-child(8)");
        for (Element row : rows)
        {
            Elements cells = rows.select("td");
            for (Element cell : cells)
            {
                String cellHtml = cell.html();
                String[] strings = cellHtml.split("<br>");
                for (String str : strings) {
                    // Add the string to the list if it's not empty
                    if (!str.isEmpty()) {
                        // Remove any remaining HTML tags from the string
                        str = Jsoup.parse(str).text();
                        str = str.trim();
                        if ((str.length()>0) && ((str.charAt(str.length()-1) == '†') || (str.charAt(str.length()-1) == ',')))
                            str = str.substring(0,str.length()-2);
                        nhanVatLichSu.add(str);
                    }
                }
            }
        }
        skls.setNhanVatLichSu(nhanVatLichSu);
        skls.display();
        cacSuKienLichSu.add(skls);
        // looking for the "Next →" HTML element in the new page
        nextElements = doc.select("a.btn.btn-sm.btn-secondary.next");
    }

    // Convert the list of objects to a JSON array
    JSONArray jsonArray = new JSONArray();
    for (SuKienLichSu suKienLichSu : cacSuKienLichSu) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Tên", suKienLichSu.getTen());
        jsonObject.put("Thời gian", suKienLichSu.getThoiGian());
        jsonObject.put("Địa điểm", suKienLichSu.getDiaDiem());
        jsonObject.put("Kết quả", suKienLichSu.getKetQua());
        jsonObject.put("Các nhân vật lịch sử", new JSONArray(suKienLichSu.getNhanVatLichSu()));
        jsonArray.put(jsonObject);
    }
    // Write the JSON array to a file
    try (FileWriter fileWriter = new FileWriter("objects.json")) {
        fileWriter.write(jsonArray.toString(4)); // Use 4 spaces for indentation
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}
