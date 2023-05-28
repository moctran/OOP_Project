package com.jetbrains;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import java.lang.reflect.Array;
import java.text.Normalizer;
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
public class Main {
    public static void main(String[] args) throws IOException {
        String baseUrl = "https://vi.wikipedia.org/wiki/C%C3%A1c_cu%E1%BB%99c_chi%E1%BA%BFn_tranh_Vi%E1%BB%87t_Nam_tham_gia";

        // initializing the list of SuKienLichSu data objects
        // that will contain the scraped data
        List<SuKienLichSu> cacSuKienLichSu = new ArrayList<>();

        // downloading the target website with an HTTP GET request
        Document doc = Jsoup
                .connect(baseUrl)
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36")
                .get();

        Elements tables = doc.select("table.wikitable");
        for (Element table: tables)
        {
            Elements rows = table.select("tr:gt(0)");
            String[] cellValues = new String[4];
            for (Element row : rows) {
                SuKienLichSu skls = new SuKienLichSu();
                Elements cells = row.select("td");

                Element ten = cells.get(0);
                skls.setTen(ten.text());

                ArrayList<String> lucLuong = new ArrayList<>();
                Element lluong = cells.get(1);
                    String cellHtml = lluong.html();
                    String[] strings = cellHtml.split("<b>|</b>|<ul>|</ul>|<li>|</li>");
                    for (String str : strings) {
                        // Add the string to the list if it's not empty
                        if (!str.isEmpty()) {
                            // Remove any remaining HTML tags from the string
                            str = Jsoup.parse(str).text();
                            str = str.trim();
                            if (str.length()>0)
                                lucLuong.add(str);
                        }
                    }
                skls.setLucLuong(lucLuong);

                Element doiPhuong = cells.get(2);
                skls.setDoiPhuong(doiPhuong.text());

                ArrayList<String> ketQua = new ArrayList<>();
                Element kQua = cells.get(3);
                String cellHtml3 = kQua.html();
                String[] strings3 = cellHtml3.split("<b>|</b>|<ul>|</ul>|<li>|</li>");
                for (String str : strings3) {
                    // Add the string to the list if it's not empty
                    if (!str.isEmpty()) {
                        // Remove any remaining HTML tags from the string
                        str = Jsoup.parse(str).text();
                        str = str.trim();
                        if (str.length()>0)
                            ketQua.add(str);
                    }
                }
                skls.setKetQua(ketQua);

                skls.display();
                cacSuKienLichSu.add(skls);
            }
        }

        // Convert the list of objects to a JSON array
        JSONArray jsonArray = new JSONArray();
        for (SuKienLichSu suKienLichSu : cacSuKienLichSu) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Tên", suKienLichSu.getTen());
            jsonObject.put("Lực lượng", new JSONArray(suKienLichSu.getLucLuong()));
            jsonObject.put("Đối phương", suKienLichSu.getDoiPhuong());
            jsonObject.put("Kết quả", new JSONArray(suKienLichSu.getKetQua()));
            jsonArray.put(jsonObject);
        }
        // Write the JSON array to a file
        try (FileWriter fileWriter = new FileWriter("objects.json")) {
            fileWriter.write(jsonArray.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}