package es.app2u.demo;

import org.json.JSONException;
import org.json.JSONObject;

/*import es.app2u.beat.handler.ConnectionHandler;
import es.app2u.beat.handler.Credentials;
import es.app2u.beat.handler.JsonHandler;
import es.app2u.beat.handler.Parameters;*/
import io.reactivex.Completable;

public class ExampleHandler {

    public Completable execute(String pickingCode, String warehouse) {
        /*Parameters parameters = new Parameters();
        parameters.setMethod(ConnectionHandler.HttpMethod.POST);
        parameters.setAuthorization(() -> Credentials.basic("1", "969696"));

        String url = "http://192.168.1.42:8000/api/casas-finalize-picking/";
        parameters.setUrl(url);

        JSONObject data = new JSONObject();
        try {
            data.put("code", pickingCode);
            data.put("warehouse_code", warehouse);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        parameters.setData(data);

        JsonHandler handler = new JsonHandler();
        return handler.execute(parameters).ignoreElement();*/
        return Completable.complete();
    }
}
