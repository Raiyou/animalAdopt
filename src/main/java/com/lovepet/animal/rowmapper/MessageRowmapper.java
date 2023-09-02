package com.lovepet.animal.rowmapper;

import com.lovepet.animal.model.Message;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageRowmapper implements RowMapper<Message> {

    @Override
    public Message mapRow(ResultSet resultSet, int i) throws SQLException {
        Message message = new Message();
        message.setArticleId(resultSet.getInt("article_id"));
        message.setUserId(resultSet.getInt("user_id"));
        message.setContent(resultSet.getString("content"));
        message.setLikes(resultSet.getString("likes"));
        message.setUnlikes(resultSet.getString("unlikes"));
        message.setPostDate(resultSet.getTimestamp("post_date"));
        message.setModifiedDate(resultSet.getTimestamp("modified_date"));
        message.setMessageId(resultSet.getInt("message_id"));
        message.setFloor(resultSet.getInt("floor"));
        message.setAuthor(resultSet.getString("author"));

        return message;
    }
}
