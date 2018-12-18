package tk.jasoryeh;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;

import java.util.Map;

public class AvicusNetworkAPI {
    public static String use(HttpExchange httpExchange) {
        JsonObject obj = new JsonObject();

        try {
            Map<String, String> qtm = RequestsDisplayer.queryToMap(httpExchange.getRequestURI().getQuery());

            if(isAuthorized(qtm.getOrDefault("key", "nope."))) {
                String gqlc = qtm.getOrDefault("query", "nope.");
                Pair<String, JsonObject> pair = gqlc.equals("nope.") ? null : process(gqlc);

                if(pair != null) {
                    JsonObject data = new JsonObject();
                    data.add(pair.getKey(), pair.getValue());

                    obj.add("data", data);
                }
            } else {
                throw new Exception("Not authorized");
            }
        } catch(Exception e) {
            JsonArray error = new JsonArray();
            error.add("Failed. :( | API -> " + e.getMessage());

            obj.add("errors", error);
            System.out.println("Failed. :( | API");
        }
        return obj.toString();
    }

    private static boolean isAuthorized(String key) {
        return true; // for now :/
    }

    private static Pair<String, JsonObject> process(String graphqlCrap) {
        // example as: {allAfterAlert(created_at:"2018-12-17T18:28:02.132-08:00"){url,created_at,user_id,seen,id,message}}

        // Strip brackets, now allAfterAlert(created_at:"2018-12-17T18:28:02.132-08:00"){url,created_at,user_id,seen,id,message}
        String strip = graphqlCrap.replaceFirst("\\{", "").substring(0, graphqlCrap.lastIndexOf("}") - 1);

        return null;
    }
}
