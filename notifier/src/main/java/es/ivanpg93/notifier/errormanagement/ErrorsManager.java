package es.ivanpg93.notifier.errormanagement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.ivanpg93.notifier.R;
import es.ivanpg93.notifier.messages.DialogMessage;
import es.ivanpg93.notifier.messages.Msg;

public class ErrorsManager {

    protected Parameters parameters;

    //==============================================================================================
    //region public

    public ErrorsManager() {
        this.parameters = new Parameters();
    }

    public ErrorsManager(Parameters parameters) {
        this.parameters = parameters;
    }

    public Message manageServerErrors(Throwable e) {
        /*if (e instanceof ResponseError) {
            return manageResponseError((ResponseError) e);
        } else if (e instanceof UnknownHostError) {
            return manageUnknownHostError((UnknownHostError) e);
        } else if (e instanceof ConnectError) {
            return manageConnectError((ConnectError) e);
        } else if (e instanceof TimeoutError) {
            return manageTimeoutError((TimeoutError) e);
        } else if (e instanceof ConnectionError) {
            return manageConnectionError((ConnectionError) e);
        } else {
            return manageDefaultError(e);
        }*/
        return manageDefaultError(e);
    }

    public void addKeyErrors(Map<String, Msg> map) {
        Map<String, Msg> errMap = this.parameters.getErrorKeyMapper();
        errMap.putAll(map);
    }

    public void addKeyError(String key, Msg msg) {
        Map<String, Msg> errMap = this.parameters.getErrorKeyMapper();
        errMap.put(key, msg);
    }

    public void removeKeyErrors(Map<String, Msg> map) {
        Map<String, Msg> errMap = this.parameters.getErrorKeyMapper();
        for (Map.Entry<String, Msg> entries: map.entrySet()) {
            errMap.remove(entries.getKey());
        }
    }

    public void removeKeyError(String key) {
        Map<String, Msg> errMap = this.parameters.getErrorKeyMapper();
        errMap.remove(key);
    }

    //endregion
    //==============================================================================================
    //region protected

    /*protected Message manageResponseError(ResponseError error) {

        boolean isUnauthorized = error.getErrorCode() == ResponseError.CODE_UNAUTHORIZED;

        if (isUnauthorized) {
            Msg messageId = getUnauthorizedMessage();
            Msg titleId = getUnauthorizedTitle();
            return new DialogMessage(titleId, messageId).addAction(Message.Action.LOGOUT);
        }

        String msg = error.getMessage();
        if (!TextUtils.isEmpty(msg)) {
            return new DialogMessage(getDefaultTitle(), new Msg(msg));
        }

        JSONObject response = error.getJsonObject();
        if (response == null) {
            return manageDefaultError(error);
        }

        try {
            return manageErrorFromJsonData(response);
        } catch (JSONException e) {
            return manageDefaultError(error);
        }
    }

    // May be a child class need to override and need the error
    @SuppressWarnings("squid:S1172")
    protected Message manageConnectionError(ConnectionError error) {
        int messageId = R.string.error_connection;
        return new DialogMessage(getDefaultTitle(), new Msg(messageId));
    }

    // May be a child class need to override and need the error
    @SuppressWarnings("squid:S1172")
    protected Message manageUnknownHostError(UnknownHostError error) {
        int messageId = R.string.error_unknown_host;
        return new DialogMessage(getDefaultTitle(), new Msg(messageId));
    }

    // May be a child class need to override and need the error
    @SuppressWarnings("squid:S1172")
    protected Message manageConnectError(ConnectError error) {
        int messageId = R.string.error_connection_failed;
        return new DialogMessage(getDefaultTitle(), new Msg(messageId));
    }

    // May be a child class need to override and need the error
    @SuppressWarnings("squid:S1172")
    protected Message manageTimeoutError(TimeoutError error) {
        int messageId = R.string.error_connection_timeout;
        return new DialogMessage(getDefaultTitle(), new Msg(messageId));
    }*/


    // May be a child class need to override and need the error
    @SuppressWarnings("squid:S1172")
    protected Message manageDefaultError(Throwable error) {
        return new DialogMessage(getDefaultTitle(), getDefaultMessage());
    }

    // May be a child class need to override and need the response
    @SuppressWarnings("squid:S1172")
    protected Message manageErrorFromJsonData(JSONObject response) throws JSONException {

        String errorCode = response.getString(getErrorDataKey());
        Map<String, Msg> errorKeyMapper = getErrorKeyMapper();

        for (Map.Entry<String, Msg> entries: errorKeyMapper.entrySet()) {
            if (errorCode.equals(entries.getKey())) {
                return new DialogMessage(getDefaultTitle(), entries.getValue());
            }
        }
        return new DialogMessage(getDefaultTitle(), getDefaultMessage());
    }

    protected Msg getDefaultMessage(){ return parameters.getDefaultMessage();}
    protected Msg getDefaultTitle(){ return parameters.getDefaultTitle();}
    protected Msg getUnauthorizedMessage() { return parameters.getUnauthorizedMessage();}
    protected Msg getUnauthorizedTitle(){ return parameters.getUnauthorizedTitle();}
    protected String getErrorDataKey() {return parameters.getErrorDataKey();}
    private Map<String, Msg> getErrorKeyMapper() {
        return parameters.getErrorKeyMapper();
    }

    //endregion

    //==============================================================================================
    //region Parameters

    public static class Parameters {
        private String errorDataKey = "error_code";
        private Msg unauthorizedMessage = new Msg(R.string.error_unauthorized);
        private Msg defaultMessage = new Msg(R.string.error_internal);
        private Msg unauthorizedTitle = new Msg(R.string.dialog_error);
        private Msg defaultTitle = new Msg(R.string.dialog_error);
        private Map<String, Msg> errorKeyMapper = new HashMap<>();

        public Msg getUnauthorizedMessage() {
            return unauthorizedMessage;
        }

        public void setUnauthorizedMessage(Msg unauthorizedMessage) {
            this.unauthorizedMessage = unauthorizedMessage;
        }

        public Msg getDefaultMessage() {
            return defaultMessage;
        }

        public void setDefaultMessage(Msg defaultMessage) {
            this.defaultMessage = defaultMessage;
        }

        public Msg getUnauthorizedTitle() {
            return unauthorizedTitle;
        }

        public void setUnauthorizedTitle(Msg unauthorizedTitle) {
            this.unauthorizedTitle = unauthorizedTitle;
        }

        public Msg getDefaultTitle() {
            return defaultTitle;
        }

        public void setDefaultTitle(Msg defaultTitle) {
            this.defaultTitle = defaultTitle;
        }

        protected String getErrorDataKey() {
            return errorDataKey;
        }

        public void setErrorDataKey(String errorDataKey) {
            this.errorDataKey = errorDataKey;
        }

        public void setErrorKeyMapper(Map<String, Msg> errorKeyMapper) {
            this.errorKeyMapper = errorKeyMapper;
        }

        public Map<String, Msg> getErrorKeyMapper() {
            return errorKeyMapper;
        }
    }

}
