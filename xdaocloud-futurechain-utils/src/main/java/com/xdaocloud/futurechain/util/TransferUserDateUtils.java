package com.xdaocloud.futurechain.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 写入数据到用户中心
 *
 * @author LuoFuMin
 * @data 2018/8/13
 */
public class TransferUserDateUtils {
    public static void main(String[] args) {
        try {

            String datasourceUrl = "jdbc:mysql://192.168.1.64:3306/connection_chain?&characterEncoding=utf-8&useSSL=false&serverTimezone=CTT";
            String datasourceUsername = "root";
            String datasourcePassword = "Xiaodao123!";

            String url = "jdbc:mysql://192.168.1.15:3306/uc?useUnicode=true&characterEncoding=utf-8";
            String username = "xiaodao";
            String password = "Xdaocloud";

            Connection conn = DatabaseUtils.getConnection(datasourceUrl, datasourceUsername, datasourcePassword);
            conn.setAutoCommit(false);

            Connection conn2 = DatabaseUtils.getConnection(url, username, password);
            conn2.setAutoCommit(false);

            String sql = "SELECT * FROM t_user WHERE register_status = 1";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            //执行查询语句并返回结果集
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                //注意：这里要与数据库里的字段对应
                String id_str = resultSet.getString("id");
                String user_name_str = resultSet.getString("username");
                String pass_word_str = resultSet.getString("password_md5");
                String mobile_number_str = resultSet.getString("mobile_number");
                String nickname_str = resultSet.getString("nickname");
                String name_str = resultSet.getString("name");
                String idcard_str = resultSet.getString("idcard");
                String avatar_str = resultSet.getString("avatar");

                id_str = "\'" + id_str + "\'";
                user_name_str = "\'" + user_name_str + "\'";
                pass_word_str = "\'" + pass_word_str + "\'";
                mobile_number_str = "\'" + mobile_number_str + "\'";
                nickname_str = "\'" + nickname_str + "\'";
                name_str = "\'" + name_str + "\'";
                idcard_str = "\'" + idcard_str + "\'";
                avatar_str = "\'" + avatar_str + "\'";

                System.out.println("id==" + id_str + "==nickname==" + nickname_str);
                String sql2 = "INSERT INTO t_user (id,username,password,name,nickname,avatar,phone,idcard) VALUES(" + id_str + "," +
                        "" + user_name_str + "," +
                        "" + pass_word_str + "," +
                        "" + name_str + "," +
                        "" + nickname_str + "," +
                        "" + avatar_str + "," +
                        "" + mobile_number_str + "," +
                        "" + idcard_str + ")";

                System.out.println("==sql2==" + sql2);


                PreparedStatement preparedStatement2 = conn2.prepareStatement(sql2);
                preparedStatement2.executeUpdate();
            }
            conn2.commit();
            conn.commit();

            DatabaseUtils.closeConnection(conn);
            DatabaseUtils.closeConnection(conn2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
