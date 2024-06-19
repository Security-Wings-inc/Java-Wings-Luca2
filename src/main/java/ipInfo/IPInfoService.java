package ipInfo;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.example.Console;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import static Log.LogMonitoramento.generateLog;

public class IPInfoService {
    public static void coletaEndereco(Console console){


        DateTimeFormatter formatadorDeData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        try {
            // URL da API para obter informações sobre o IP
            String apiUrl = "http://ip-api.com/xml/";
            String query = ""; // Deixe vazio para obter o IP atual


            // Cria a URL completa e abre a conexão HTTP
            URL url = new URL(apiUrl + query);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Cria um DocumentBuilder para analisar a resposta XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(connection.getInputStream());

            // Obtém o elemento raiz do documento XML
            Element root = doc.getDocumentElement();

            // Extrai elementos específicos do XML
            String country = root.getElementsByTagName("country").item(0).getTextContent();
            String region = root.getElementsByTagName("region").item(0).getTextContent();
            String city = root.getElementsByTagName("city").item(0).getTextContent();
            String ipQuery = root.getElementsByTagName("query").item(0).getTextContent();

            // Exibindo informações no console
            System.out.println("País: " + country);
            System.out.println("Região: " + region);
            System.out.println("Cidade: " + city);
            System.out.println("IP: " + ipQuery);


            // Cria um objeto IPInfo
            IPInfo ipInfo = new IPInfo();

            // Define suas propriedades com os valores extraídos
            ipInfo.setCountry(country);
            ipInfo.setRegion(region);
            ipInfo.setCity(city);
            ipInfo.setIpQuery(ipQuery);

            new IPInfoDAO().cadastrarDados(ipInfo, console);

            LocalDateTime localDateTime1 = LocalDateTime.now();
            LocalDateTime localDateTime2 = LocalDateTime.now();

            generateLog("{" + formatadorDeData.format(localDateTime1) + "} " +"[INFO] " + " Endereço coletado..." + "\n");

            generateLog("""
                    {%s} [LOCALIZAÇÃO] Pais: %s, Região: %s, Cidade: %s, IP: %s \n
                    """.formatted(formatadorDeData.format(localDateTime2), country, region, city, ipQuery));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
