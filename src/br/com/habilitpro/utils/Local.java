package br.com.habilitpro.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Local {

    private static TreeMap<String, String> map = new TreeMap<>();

    public static TreeMap<String, String> buscarEstados() {
        return buscarLocais("3469034");
    }

    public static List<String> buscarCidades(String idEstado) {
        return buscarLocais(idEstado).keySet()
                .stream().collect(Collectors.toList());
    }

    private static TreeMap<String, String> buscarLocais(String geonameId) {

        System.out.print("\nAguarde...");
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .proxy(ProxySelector.getDefault())
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://www.geonames.org/children?geonameId="+geonameId))
                .build();

        try {
            HttpResponse<String> response =  httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());

            String responseBody = response.body();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(responseBody));
            Document doc = db.parse(is);

            NodeList geonames = doc.getElementsByTagName("geoname");
            for (int i = 0; i < geonames.getLength(); i++) {

                Element geoname = (Element) geonames.item(i);
                Element nome = (Element) geoname.getElementsByTagName("name").item(0);
                Element geoId = (Element) geoname.getElementsByTagName("geonameId").item(0);
                map.put(nome.getTextContent(), geoId.getTextContent());
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.print(" falha na busca!");
            return new TreeMap<>();
        }
        System.out.print(" pronto!");
        return map;
    }

}