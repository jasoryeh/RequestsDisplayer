package tk.jasoryeh;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class RequestsDisplayer {
    public static void main(String[] args) throws IOException {
        System.out.println("API SERVER STARTING >>>");

        HttpServer server = HttpServer.create(new InetSocketAddress(1000), 1);
        server.createContext("/", httpExchange -> {
            System.out.println("<-----| NEW REQUEST | ----->");
            System.out.println("REQUEST: " + httpExchange.getRemoteAddress());
            System.out.println("REQUEST: " + httpExchange.getRequestMethod());
            System.out.println("REQUEST: " + httpExchange.getProtocol());

            try {
                queryToMap(httpExchange.getRequestURI().getQuery()).forEach((s, s2) -> System.out.println("REQEUST QUERY: " + s + " | " + s2));
            } catch(Exception e) {
                System.out.println("REQUEST QUERY: " + "no addt'l info.");
            }

            System.out.println("REQUEST URI: " + httpExchange.getRequestURI().toString());
            System.out.println("REQUEST BODY: " + IOUtils.toString(httpExchange.getRequestBody()));

            httpExchange.getRequestHeaders().forEach((s, strings) -> {
                System.out.println("HEADER: " + s + " | " + strings);
            });

            String queryResponse = "";
            int responseCode = 200;

            // Some Avicus API FUN :)
            String apiResponse = AvicusNetworkAPI.use(httpExchange);
            queryResponse = apiResponse.equals("") ? queryResponse : apiResponse;
            responseCode = apiResponse.startsWith("unauth") ? 403 : responseCode;
            // FUN END :(

            httpExchange.sendResponseHeaders(responseCode, queryResponse.getBytes().length);
            OutputStream os = httpExchange.getResponseBody();
            os.write(queryResponse.getBytes());
            os.close();
        });
        server.start();
        System.out.println("<<< STARTED, WAITING...");
    }

    public static Map<String, String> queryToMap(String q) {
        try {
            Map<String, String> query_pairs = new LinkedHashMap<String, String>();
            String[] pairs = q.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
            }
            return query_pairs;
        } catch(Exception e) {
            return new HashMap<String, String>();
        }
    }
}
