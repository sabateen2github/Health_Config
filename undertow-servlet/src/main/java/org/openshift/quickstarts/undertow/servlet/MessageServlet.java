/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.openshift.quickstarts.undertow.servlet;

import com.alaa.server.Views;

import com.google.gson.Gson;
import com.health.objects.Strings;
import com.health.objects.Types;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Stuart Douglas
 */
public class MessageServlet extends HttpServlet {

    public static final long Signature = -1999628l;

    public static final Gson gson = new Gson();

    public static volatile boolean isResetting = false;
    public static volatile boolean isInstalling = false;

    public static volatile int num_op = 0;
    
    
    
    public static final String MESSAGE = "message";

    private String message;

    @Override
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
        message = config.getInitParameter(MESSAGE);
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.write(message);
        writer.close();
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        
            InputStream in =req.getInputStream();
            byte[] request_byte = new byte[2048];
            byte[] data = new byte[0];
            int read = 0;
            int size = 0;
            while ((read = in.read(request_byte)) > 0) {
                data = Arrays.copyOf(data, size + read);
                for (int i = 0; i < read; i++) {
                    data[size + i] = request_byte[i];
                }
                size += read;

            }
            String request = new String(data, StandardCharsets.UTF_8);
            Request re = gson.fromJson(request, Request.class);
            while (isResetting) {
            }
            while (isInstalling) {
            }
            num_op++;
            String res = null;
            switch (re.req_type) {

                case Types.Type_Handle_Patient: {
                    //System.out.println("Handle Patient");
                    res = Views.handlePatientHandle(request);
                }
                break;
                case Types.Type_Install_Accounts_Med_Team: {

                    //System.out.println("Installing  Account MEd Team");
                    isInstalling = true;
                    while (num_op > 1) {
                        Thread.yield();
                    }
                    res = Views.handleInstallMeDTeam(request);
                    isInstalling = false;
                }
                break;
                case Types.Type_Install_Accounts_Patients: {
                    //System.out.println("Installing  Account Patient");
                    isInstalling = true;
                    while (num_op > 1) {
                        Thread.yield();
                    }
                    res = Views.handleInstallPatient(request);
                    isInstalling = false;
                }
                break;
                case Types.Type_Install_Insurances: {
                    //System.out.println("Installing  Insurances");
                    isInstalling = true;
                    while (num_op > 1) {
                        Thread.yield();
                    }
                    res = Views.handleInstallInsurances(request);
                    isInstalling = false;
                }
                break;
                case Types.Type_Install_Medications: {

                    isInstalling = true;
                    while (num_op > 1) {
                        Thread.yield();
                    }
                    res = Views.handleInstallMedications(request);
                    isInstalling = false;
                    //System.out.println("Installing  Medications");
                }
                break;
                case Types.Type_Install_Services: {

                    isInstalling = true;
                    while (num_op > 1) {
                        Thread.yield();
                    }
                    res = Views.handleInstallServices(request);
                    isInstalling = false;

                    //System.out.println("Installing  Services");
                }
                break;
                case Types.Type_Install_Rooms: {
                    //System.out.println("Installing  Rooms");
                    isInstalling = true;
                    while (num_op > 1) {
                        Thread.yield();
                    }
                    res = Views.handleInstallRooms(request);
                    isInstalling = false;

                }
                break;
                case Types.Type_Install_Schedules: {
                    //System.out.println("Installing  Schedules");
                    isInstalling = true;
                    while (num_op > 1) {
                        Thread.yield();
                    }
                    res = Views.handleInstallSchedules(request);
                    isInstalling = false;

                }
                break;
                case Types.Type_Install_Condtions: {
                    //System.out.println("Install Condtions");
                    isInstalling = true;
                    while (num_op > 1) {
                        Thread.yield();
                    }
                    res = Views.handleInstallCondtions(request);
                    isInstalling = false;

                }
                break;
                case Types.Type_Install_Procedures: {
                    //System.out.println("Install Procedures");
                    isInstalling = true;
                    while (num_op > 1) {
                        Thread.yield();
                    }
                    res = Views.handleInstallProcedures(request);
                    isInstalling = false;

                }
                break;
                case Types.Type_Book_Appointment: {
                    //System.out.println("Book Appointment");

                    res = Views.handleBook(request);
                }
                break;
                case Types.Type_Check_IDPASS: {

                    res = Views.handleCheckIDPass(request);
                    System.out.print("Checking id And Pass   " + request);

                }
                break;
                case Types.Type_Done_Request: {
                    //System.out.println("Done Request");

                    res = Views.handleDoneRequest(request);
                }
                break;
                case Types.Type_Enter_Line: {
                    //System.out.println("Enter the Line");

                    res = Views.handleEnterLine(request);
                }
                break;
                case Types.Type_Get_Active_Feedback_List: {
                    //System.out.println("GetActive FeedBack");

                    res = Views.handleGetActiveFeedBack(request);
                }
                break;
                case Types.Type_Get_Active_Proc: {
                    //System.out.println("Get Active Procedure");

                    res = Views.handleGetActiveProcedures(request);
                }
                break;
                case Types.Type_Get_Smart_FeedBackList: {
                    //System.out.println("Get Smart FeedBack List");

                    res = Views.handleGetActiveSmartFeeds(request);
                }
                break;
                case Types.Type_Get_Availible_Condtions: {
                    //System.out.println("Get Availible Condtions");

                    res = Views.handleGetAvailibleCondtions(request);
                }
                break;

                case Types.Type_Get_Availible_Medications: {
                    //System.out.println("Get Availible Condtions");
                    res = Views.handleGetAvailibleMedications(request);
                }
                break;
                case Types.Type_Get_Availible_Procedures: {
                    ////System.out.println("Get Availible Procedures");

                    res = Views.handleGetAvailibleProcedures(request);
                }
                break;
                case Types.Type_Get_Availible_Rooms: {
                    //System.out.println("Get Availible Rooms");

                    res = Views.handleGetAvailibleRooms(request);
                }
                break;
                case Types.Type_Get_Availible_Services: {
                    //System.out.println("Get availible Services");

                    res = Views.handleGetAvailibleServices(request);
                }
                break;
                case Types.Type_Get_Condtion: {
                    //System.out.println("Get Condtion");

                    res = Views.handleGetCondtion(request);
                }
                break;
                case Types.Type_Get_Room_Current_Appointment: {
                    //System.out.println("Get Current Appointment");

                    res = Views.handleGetCurrentRoomAppointments(request);
                }
                break;
                case Types.Type_Get_Med_Data: {
                    //System.out.println("Get Med Data");

                    res = Views.handleGetMedData(request);
                }
                break;

                case Types.Type_Get_Notifications: {
                    //System.out.println("Get Notification");

                    res = Views.handleGetNotifications(request);
                }
                break;
                case Types.Type_Get_Personal_Data: {
                    //System.out.println("Get Personal Data");
                    res = Views.handleGetPersonalData(request);
                }
                break;
                case Types.Type_Get_Procedure: {
                    //System.out.println("Get Procedure");

                    res = Views.handleGetProcedure(request);
                }
                break;
                case Types.Type_Get_Rooms_Appointments: {
                    //System.out.println("Get Rooms Appointments");
                    res = Views.handleGetRoomsAppointments(request);
                }
                break;
                case Types.Type_Pay_Service: {
                    //System.out.println("Pay services");
                    res = Views.handlePayService(request);
                }
                break;
                case Types.Type_Skip_Time: {
                    //System.out.println("Skip Time");

                    res = Views.handleSkipTime(request);
                }
                break;
                case Types.Type_StartCondtion: {
                    //System.out.println("Start Condtion");

                    res = Views.handleStartCondtion(request);
                }
                break;
                case Types.Type_StartProcedure: {
                    //System.out.println("Start Procedure");
                    res = Views.handleStartProcedure(request);
                }
                break;
                case Types.Type_StopCondtin: {
                    //System.out.println("Start Condtion");
                    res = Views.handleStopCondtion(request);
                }
                break;
                case Types.Type_StopProcedure: {
                    //System.out.println("Stop Procedure");

                    res = Views.handleStopProcedure(request);
                }
                break;
                case Types.Type_UpdateProcedure: {
                    //System.out.println("Update Procedure");
                    res = Views.handleUpdateProcedure(request);
                }
                break;
                case Types.Type_GetTime: {
                    res = Views.handleGetTime(request);

                }
                break;
                case Types.Type_Get_Availible_Insurances: {
                    //System.out.println("Get Availible Insurances");
                    res = Views.handleGetAvailibleInsurances(request);
                }
                break;
                case Types.Type_Get_Active_Cond: {
                    //System.out.println("Get Active Condtions");
                    res = Views.handleGetActiveCondtions(request);
                }
                break;
                case Types.Type_CancelAppointment: {
                    //System.out.println("Get Active Condtions");
                    res = Views.handleCancelAppointment(request);
                }
                break;
                case Types.Type_AffectedAppointments: {
                    //System.out.println("Handle Affected");
                    res = Views.handleAffectedAppointments(request);
                }
                break;
                case Types.Type_GetRoomFullAppintments: {
                    //System.out.println("Get Active Condtions");
                    res = Views.handleGetFullAppointment(request);
                }
                break;
                case Types.Type_Reset: {
                    isResetting = true;
                    while (num_op > 1) {
                        Thread.yield();
                    }
                    res = Views.handleReset(request) ? Strings.SUCCESS : null;
                    isResetting = false;
                }
                break;
                case Types.Type_setLang: {
                    res = Views.handleSetLang(request);
                }
                break;
            }

            num_op--;

            int length = 0;

            if (res != null) {
                length = res.getBytes(StandardCharsets.UTF_8).length;
            } else {

                res = "error";
                length = res.getBytes(StandardCharsets.UTF_8).length;
            }

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentLength(length);
            OutputStream out = resp.getOutputStream();
            out.write(res.getBytes(StandardCharsets.UTF_8));
            in.close();
            out.close();

    }

    static class Request {

        int req_type;
    }
}
