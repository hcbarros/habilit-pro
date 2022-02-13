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
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Buscador {

    private static List<String> idEstados = new ArrayList<>();


    public static TreeMap<String, String> buscarEstados() {
        String url =
                "https://raw.githubusercontent.com/hcbarros/banco/main/src/br/com/banco/utils/locals/estados.xml";
        return buscarLocais(url, true);
    }


    public static List<String> buscarCidades(String idEstado) {
        if(!idEstados.contains(idEstado)) return null;
        String url =
                "https://raw.githubusercontent.com/hcbarros/banco/main/src/br/com/banco/utils/locals/cidades/"+idEstado+".xml";
        return buscarLocais(url,false).keySet().stream().collect(Collectors.toList());
    }


    private static TreeMap<String, String> buscarLocais(String url, boolean ehEstado) {

        TreeMap<String, String> map = new TreeMap<>();
        System.out.print("\nAguarde...");
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .proxy(ProxySelector.getDefault())
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
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

            NodeList geonames = doc.getElementsByTagName(ehEstado ? "estado" : "cidade");
            for (int i = 0; i < geonames.getLength(); i++) {

                Element geoname = (Element) geonames.item(i);
                Element nome = (Element) geoname.getElementsByTagName("nome").item(0);
                Element id = (Element) geoname.getElementsByTagName("idestado").item(0);
                if(ehEstado) {
                    if(i == 0) idEstados.clear();
                    idEstados.add(id.getTextContent());
                }
                map.put(nome.getTextContent(), id.getTextContent());
            }
        }
        catch (Exception e) {
            System.out.print(" falha na busca!");
            return null;
        }
        System.out.print(" pronto!");
        return map;
    }

}
