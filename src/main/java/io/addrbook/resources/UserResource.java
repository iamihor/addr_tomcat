/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package io.addrbook.resources;

import common.AppUtility;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

/**
 * REST Web Service
 *
 * @author usern
 */
@Path("user")
@RequestScoped
public class UserResource {

    @Context
    private HttpServletRequest request;

    /**
     * Creates a new instance of UserResource
     */
    public UserResource() {
    }

//https://www.educative.io/answers/how-to-get-the-ip-address-of-a-localhost-in-java
    /**
     *
     * @return
     */
    @GET
    public Response getJson() {
        JSONObject json = new JSONObject();
        String ipAddr = request.getRemoteAddr();
        AppUtility Util = new AppUtility();
        String userRole = Util.getProp(ipAddr);
        if (userRole != null) {
            json.put("status", "success");
            json.put("code", Response.Status.OK.getStatusCode());
            json.put("message", userRole);
            return Response.ok(json.toString()).build();
        }
        json.put("status", "error");
        json.put("code", Response.Status.NOT_FOUND.getStatusCode());
        json.put("message", ipAddr);
        return Response.status(Response.Status.NOT_FOUND).entity(json.toString()).build();
    }

    /**
     * PUT method for updating or creating an instance of UserResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

}
