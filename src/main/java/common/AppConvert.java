
package common;

import org.json.JSONArray;
import org.json.JSONObject;
import java.sql.ResultSet;

public class AppConvert {

    /**
     * Convert a result set into a JSON Array
     *
     * @param rs
     * @return a JSONArray
     * @throws Exception
     */
    public static JSONArray convertToJSON(ResultSet rs)
            throws Exception {
        JSONArray jsonArray = new JSONArray();
//io
//        rs.beforeFirst();
//io
        while (rs.next()) {
            int total_cols = rs.getMetaData().getColumnCount();
            JSONObject jsonObj = new JSONObject();
            for (int i = 0; i < total_cols; i++) {
                jsonObj.put(rs.getMetaData().getColumnLabel(i + 1)
                        .toLowerCase(), rs.getObject(i + 1));
            }
            jsonArray.put(jsonObj);
        }
        return jsonArray;
    }

    /**
     * Convert a result set into a XML List
     *
     * @param resultSet
     * @return a XML String with list elements
     * @throws Exception if something happens
     */
    public static String convertToXML(ResultSet resultSet)
            throws Exception {
        StringBuilder xmlArray = new StringBuilder("<results>");
        while (resultSet.next()) {
            int total_cols = resultSet.getMetaData().getColumnCount();
            xmlArray.append("<result ");
            for (int i = 0; i < total_cols; i++) {
                xmlArray.append(" ").append(resultSet.getMetaData().getColumnLabel(i + 1)
                        .toLowerCase()).append("='").append(resultSet.getObject(i + 1)).append("'");
            }
            xmlArray.append(" />");
        }
        xmlArray.append("</results>");
        return xmlArray.toString();
    }
}
