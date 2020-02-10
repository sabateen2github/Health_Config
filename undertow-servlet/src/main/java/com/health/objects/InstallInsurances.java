package com.health.objects;

import com.google.gson.Gson;
import com.health.requestHandler.RequestHandler;
import java.util.ArrayList;

import org.jetbrains.annotations.NonNls;

import java.util.List;
import org.openshift.quickstarts.undertow.servlet.MessageServlet;

public class InstallInsurances {

    public static class Request {

        public int req_type;
        public List<GetAvailibleInsurances.Insurance> insurances_paths;

    }

    public static List<Long> InstallInsurances(List<GetAvailibleInsurances.Insurance> insurances) {

        Request req = new Request();
        req.req_type = Types.Type_Install_Insurances;
        req.insurances_paths = insurances;
        RequestHandler handler = new RequestHandler((new Gson()).toJson(req));
        @NonNls
        String json = handler.send();
        if (json == null) {
            return null;
        }
        try {
            return MessageServlet.gson.fromJson(json, (new ArrayList<Long>()).getClass());

        } catch (Exception e) {
            return null;
        }

    }

}
