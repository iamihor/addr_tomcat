/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package io.addrbook.resources;

import common.AppConvert;
import common.AppDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.MediaType;
import org.json.JSONArray;

/**
 * REST Web Service
 *
 * @author usern
 */
@Path("group")
@RequestScoped
public class GroupResource {

    private static final Logger LOG = Logger.getLogger(GroupResource.class.getName());
    
    /**
     * Creates a new instance of GroupResource
     */
    public GroupResource() {
    }

    /**
     * Retrieves representation of an instance of io.addrbook.resources.GroupResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
     Connection con;
        PreparedStatement pstmt;
        ResultSet rs;
        String SQL;
        JSONArray ja = null;
        try {
            con = AppDb.getConnection();
            SQL = "SELECT group_id as id, created, modified, deprecated, group_name as placename, group_header " +
                    "FROM group_list "+
                    "WHERE isnull(group_list.deprecated) " +
                    "ORDER BY group_name; ";
            pstmt = con.prepareStatement(SQL);
            rs = pstmt.executeQuery();
            ja = AppConvert.convertToJSON(rs);
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            LOG.log(Level.WARNING, ex.getMessage(), ex);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        if (ja != null && ja.length() > 0) {
            return ja.toString();
        } else {
            return "Інформація відсутня.";
        }
//        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of GroupResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
