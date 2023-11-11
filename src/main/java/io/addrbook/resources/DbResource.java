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
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * REST Web Service
 *
 * @author usern
 */
@Path("db")
@RequestScoped
public class DbResource {

    private static final Logger LOG = Logger.getLogger(DbResource.class.getName());

    /**
     * Creates a new instance of DbResource
     */
    public DbResource() {
    }

    /**
     * Retrieves representation of an instance of io.addrbook.DbResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON) 
    public String getJson() {
        Connection con;
        PreparedStatement pst;
        ResultSet rs;
        String SQL;
        JSONArray ja = null;
        try {
            con = AppDb.getConnection();
            SQL = "SELECT addressbook.id, trim(concat(addressbook.lastname,' ',addressbook.firstname)) as name,addressbook.company,addressbook.title,addressbook.work,addressbook.mobile,addressbook.email,addressbook.created,addressbook.modified,group_list.group_name as placename, group_list.group_id as placeid "
                    + "FROM addressbook "
                    + "LEFT JOIN address_in_groups ON addressbook.id=address_in_groups.id "
                    + "LEFT JOIN group_list ON address_in_groups.group_id=group_list.group_id "
                    + "WHERE isnull(addressbook.deprecated) "
                    + "ORDER BY name; ";
            pst = con.prepareStatement(SQL);
            rs = pst.executeQuery();
            ja = AppConvert.convertToJSON(rs);
            rs.close();
            pst.close();
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
     * PUT method for updating or creating an instance of DbResource
     *
     * @param formData
     * @param content representation for the resource
     */
    /*
https://stackoverflow.com/questions/4687271/jax-rs-how-to-return-json-and-http-status-code-together    

@POST
@Consumes("application/json")
@Produces("application/json")
public Response authUser(JsonObject authData) {
    String email = authData.getString("email");
    String password = authData.getString("password");
    JSONObject json = new JSONObject();
    if (email.equalsIgnoreCase(user.getEmail()) && password.equalsIgnoreCase(user.getPassword())) {
        json.put("status", "success");
        json.put("code", Response.Status.OK.getStatusCode());
        json.put("message", "User " + authData.getString("email") + " authenticated.");
        return Response.ok(json.toString()).build();
    } else {
        json.put("status", "error");
        json.put("code", Response.Status.NOT_FOUND.getStatusCode());
        json.put("message", "User " + authData.getString("email") + " not found.");
        return Response.status(Response.Status.NOT_FOUND).entity(json.toString()).build();
    }
}
     */
    @PUT
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
//    public Response putJson(@FormParam("code") String code, @FormParam("material") String material) {
    public Response putJson(MultivaluedMap<String, String> formData) {
        JSONObject json = new JSONObject();

        String id = formData.getFirst("txId");
        String name = formData.getFirst("txName");
        String dep = formData.getFirst("txDep");
        String pos = formData.getFirst("txPos");
        String email = formData.getFirst("txEmail");
        String work = formData.getFirst("txWork");
        String mobile = formData.getFirst("txMob");
        String place = formData.getFirst("selPlace");
        if (name.length() > 0) {
            try {
                Connection con;
                PreparedStatement pstmti;
                int inRowCnti = 0;
                String SQLi;
                PreparedStatement pstmtu;
                String SQLu;
                /*
// Copy record into same table
CREATE TEMPORARY TABLE temp_table ENGINE=MEMORY

SELECT * FROM your_table WHERE id=1;
// Update other values at will. 
UPDATE temp_table SET id=0; 

INSERT INTO your_table SELECT * FROM temp_table;
DROP TABLE temp_table;
                 */
                try {
                    con = AppDb.getConnection();
//                    SQLi = "INSERT INTO addressbook.addressbook "
//                            + "(id, domain_id, firstname, middlename, lastname, nickname, company, title, address, addr_long, addr_lat, addr_status, home, mobile, `work`, fax, email, email2, email3, im, im2, im3, homepage, bday, bmonth, byear, aday, amonth, ayear, address2, phone2, notes, photo, x_vcard, created, modified, deprecated, password, login, `role`) "
//                            + "VALUES(NULL, 0, '', NULL, '', NULL, '', NULL, '', NULL, NULL, NULL, '', '', '', '', '', '', NULL, NULL, NULL, NULL, '', 0, '', '', NULL, NULL, NULL, '', '', '', NULL, NULL, now(), now(), '0000-00-00 00:00:00', NULL, NULL, NULL);";
                    SQLi = "INSERT INTO addressbook.addressbook "
                            + "(id, domain_id, firstname, middlename, lastname, nickname, company, title, address, addr_long, addr_lat, addr_status, home, mobile, `work`, fax, email, email2, email3, im, im2, im3, homepage, bday, bmonth, byear, aday, amonth, ayear, address2, phone2, notes, photo, x_vcard, created, modified, deprecated, password, login, `role`) "
                            + "VALUES(?, 0, ?, NULL, '', NULL, ?, ?, '', NULL, NULL, NULL, '', ?, ?, '', ?, '', NULL, NULL, NULL, NULL, '', 0, '', '', NULL, NULL, NULL, '', '', '', NULL, NULL, now(), now(), '0000-00-00 00:00:00', NULL, NULL, NULL);";
                    pstmti = con.prepareStatement(SQLi);
                    if (id.length() > 0) {
                        SQLu = "UPDATE addressbook "
                                + " SET addressbook.deprecated=now(), addressbook.modified=now() "
                                + " WHERE isnull(addressbook.deprecated) AND addressbook.id= ? ;";
                        pstmtu = con.prepareStatement(SQLu);
                        pstmtu.setString(1, id);
                        pstmtu.executeUpdate();
                        pstmtu.close();
                        pstmti.setString(1, id);
                    } else {
//https://stackoverflow.com/questions/1357429/preparedstatement-setnull                        
                        pstmti.setNull(1, java.sql.Types.INTEGER);
                    }
                    pstmti.setString(2, name.trim());
                    pstmti.setString(3, dep.trim());
                    pstmti.setString(4, pos.trim());
                    pstmti.setString(5, mobile.trim());
                    pstmti.setString(6, work.trim());
                    pstmti.setString(7, email.trim());
                    inRowCnti = pstmti.executeUpdate();
                    pstmti.close();
                    if (place.length() > 0) {
                        SQLu = "DELETE FROM address_in_groups "
                                + "WHERE id=? AND isnull(address_in_groups.deprecated);";
                        pstmtu = con.prepareStatement(SQLu);
                        pstmtu.setString(1, id);
                        pstmtu.executeUpdate();
                        pstmtu.close();
                        SQLu = "INSERT INTO address_in_groups "
                                + "(domain_id, id, group_id, created, modified, deprecated) "
                                + " VALUES(0, ?, ?, now(), now(), '0000-00-00 00:00:00');";
                        pstmtu = con.prepareStatement(SQLu);
                        pstmtu.setString(1, id);
                        pstmtu.setString(2, place);
                        pstmtu.executeUpdate();
                        pstmtu.close();
                    }
                    con.close();
                } catch (SQLException ex) {
                    LOG.log(Level.WARNING, ex.getMessage(), ex);
                }
                if (inRowCnti >= 1) {
                    json.put("status", "success");
                    json.put("code", Response.Status.OK.getStatusCode());
                    json.put("message", "Update " + inRowCnti + " records.");
                    return Response.ok(json.toString()).build();
                }
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        json.put("status", "error");
        json.put("code", Response.Status.NOT_FOUND.getStatusCode());
        json.put("message", "Інформація відсутня або Хибний Код.");
        return Response.status(Response.Status.NOT_FOUND).entity(json.toString()).build();
//        throw new UnsupportedOperationException();
    }

    /**
     *
     * DELETE method
     *
     * @param id
     * @return
     */
    @DELETE
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delJson(@FormParam("id") String id) {
        JSONObject json = new JSONObject();
        try {
            Connection con;
            PreparedStatement pstmtu;
            int inRowCntu = 0;
            String SQLu;

            try {
                con = AppDb.getConnection();
                SQLu = "UPDATE addressbook "
                        + " SET addressbook.deprecated=now(), addressbook.modified=now() "
                        + " WHERE isnull(addressbook.deprecated) AND addressbook.id= ? ;";
                pstmtu = con.prepareStatement(SQLu);
                pstmtu.setString(1, id);
                inRowCntu = pstmtu.executeUpdate();
                pstmtu.close();
                con.close();
            } catch (SQLException ex) {
                LOG.log(Level.WARNING, ex.getMessage(), ex);
            }
            if (inRowCntu >= 1) {
                json.put("status", "success");
                json.put("code", Response.Status.OK.getStatusCode());
                json.put("message", "Update " + inRowCntu + " records.");
                return Response.ok(json.toString()).build();
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }
        json.put("status", "error");
        json.put("code", Response.Status.NOT_FOUND.getStatusCode());
        json.put("message", "Інформація відсутня або Хибний Код.");
        return Response.status(Response.Status.NOT_FOUND).entity(json.toString()).build();
//        throw new UnsupportedOperationException();
    }

}
