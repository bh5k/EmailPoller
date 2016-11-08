package com.stg.emailpoller.repository;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

/**
 * Photo Data Access Object (DAO).
 *
 * Created by dqromney on 11/7/16.
 */
public interface PhotoDao {

    @SqlUpdate("insert into photo (subject, text, imageUrl, userid) values (:subject, :text, :imageUrl, :userid)")
    void insert(@Bind("subject") String subject, @Bind("text") String text, @Bind("imageUrl") String imageUrl, @Bind("userid") Long userid);

    @SqlQuery("select subject from user where id = :id")
    String findSubjectById(@Bind("id") int id);

    /**
     * close with no args is used to close the connection
     */
    void close();

}
