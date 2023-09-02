package com.lovepet.animal.dao.impl;

import com.lovepet.animal.dao.MessageDao;
import com.lovepet.animal.dto.MessageQueryParams;
import com.lovepet.animal.dto.MessageRequest;
import com.lovepet.animal.model.Message;
import com.lovepet.animal.rowmapper.MessageRowmapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MessageDaoImpl implements MessageDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer countMessage(MessageQueryParams messageQueryParams) {
        String sql = "SELECT count(*) FROM feedback WHERE article_id = :articleId";

        Map<String, Object> map = new HashMap<>();
        map.put("articleId", messageQueryParams.getArticleId());

        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);

        return total;
    }

    @Override
    public List<Message> getMessage(MessageQueryParams messageQueryParams) {
        String sql = "SELECT * FROM (" +
                "SELECT f.message_id, f.article_id, f.user_id, f.content, f.likes, f.unlikes, " +
                "f.post_date, f.modified_date, f.floor, u.name AS author FROM feedback AS f " +
                "LEFT JOIN user AS u ON f.user_id = u.user_id) AS subquery " +
                "WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        if (messageQueryParams.getArticleId()!=null) {
            sql += " AND article_id=:articleId ORDER BY post_date ASC";
            map.put("articleId", messageQueryParams.getArticleId());
        }

        List<Message> messages = namedParameterJdbcTemplate.query(sql, map, new MessageRowmapper());

        return messages;
    }

    @Override
    public Message getMessageById(Integer messageId) {
        String sql = "SELECT * FROM (" +
                "SELECT f.message_id, f.article_id, f.user_id, f.content, f.likes, f.unlikes, " +
                "f.post_date, f.modified_date, f.floor, u.name AS author FROM feedback AS f " +
                "LEFT JOIN user AS u ON f.user_id = u.user_id) AS subquery " +
                "WHERE message_id = :messageId";

        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);

        List<Message> messageList = namedParameterJdbcTemplate.query(sql, map, new MessageRowmapper());

        if (messageList.size() > 0) {
            return messageList.get(0);
        }else {
            return null;
        }
    }

    @Override
    public void createMessage(MessageQueryParams messageQueryParams) {
        String sql = " INSERT INTO feedback (article_id, user_id, content, post_date, modified_date) values(:articleId, :userId, :content, :postDate, :modifiedDate) ";
        Map<String, Object> map = new HashMap<>();
        map.put("articleId", messageQueryParams.getArticleId());
        map.put("userId", messageQueryParams.getUserId());
        map.put("content", messageQueryParams.getContent());
        map.put("postDate", new Date());
        map.put("modifiedDate", new Date());

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void updataMessage(Integer messageId, MessageRequest messageRequest) {
        String sql = "UPDATE feedback SET content = :content, modified_date = :modifiedDate WHERE message_id = :messageId";
        Map<String, Object> map = new HashMap<>();
        map.put("content", messageRequest.getContent());
        map.put("modifiedDate", new Date());
        map.put("messageId", messageId);

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void deleteMessageById(Integer articleId, Integer messageId) {
        String sql = "DELETE FROM feedback WHERE article_id = :articleId AND message_id = :messageId";

        Map<String, Object> map = new HashMap<>();
        map.put("articleId", articleId);
        map.put("messageId", messageId);

        namedParameterJdbcTemplate.update(sql, map);

    }
}
