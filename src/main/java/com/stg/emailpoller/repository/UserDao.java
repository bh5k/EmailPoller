package com.stg.emailpoller.repository;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

/**
 * User Data Access Object (DAO)
 *
 * Created by dqromney on 11/7/16.
 */
public interface UserDao {

//    @SqlUpdate("create table something (id int primary key, name varchar(100))")
//    void createSomethingTable();

    @SqlUpdate("insert into user (email, name) values (:email, :name)")
    void insert(@Bind("email") String email, @Bind("name") String name);

    @SqlQuery("select name from user where id = :id")
    String findNameById(@Bind("id") int id);

    @SqlQuery("select id from user where email = :email")
    Long findNameByEmail(@Bind("email") String email);

    /**
     * close with no args is used to close the connection
     */
    void close();
}
