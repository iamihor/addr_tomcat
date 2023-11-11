/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package io.addrbook.resources;

import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;

import javax.ws.rs.Path;

import javax.ws.rs.core.Context;

import javax.ws.rs.core.Response;
import org.json.JSONObject;

/**
 * REST Web Service
 *
 * @author usern
 */
//https://www.czetsuyatech.com/2015/01/javaee-rest-get-client-ip.html

@Path("getip")
@RequestScoped
public class GetIP {

//inject
    @Context
    private HttpServletRequest request;
    
    @GET
    public Response getIP() {
        JSONObject json = new JSONObject();
        String ipAddr = request.getRemoteAddr();
        System.out.println("ipAddress:" + ipAddr);

        json.put("status", "success");
        json.put("code", Response.Status.OK.getStatusCode());
        json.put("message", ipAddr);
        return Response.ok(json.toString()).build();
    }
}
