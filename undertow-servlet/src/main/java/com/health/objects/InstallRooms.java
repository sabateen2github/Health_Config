package com.health.objects;

import com.google.gson.Gson;
import com.health.requestHandler.RequestHandler;
import java.util.ArrayList;

import java.util.List;
import org.openshift.quickstarts.undertow.servlet.MessageServlet;

public class InstallRooms {

    public static class Request {

        public int req_type;
        public List<GetAvailibleRooms.Room> rooms;
    }

    public static List<Long> InstallProceduresTemplates(List<GetAvailibleRooms.Room> rooms) {

        Request req = new Request();
        req.req_type = Types.Type_Install_Rooms;
        req.rooms = rooms;
        RequestHandler handler = new RequestHandler((new Gson()).toJson(req));
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
