package com.lovepet.animal.rowmapper;

import com.lovepet.animal.model.ForumArticle;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ForumArticleRowmapper implements RowMapper<ForumArticle> {

    @Override
    public ForumArticle mapRow(ResultSet resultSet, int i) throws SQLException {
        ForumArticle forumArticle = new ForumArticle();

        forumArticle.setArticleId(resultSet.getInt("article_id"));
        forumArticle.setUserId(resultSet.getInt("user_id"));
        forumArticle.setType(resultSet.getString("type"));
        forumArticle.setTitle(resultSet.getString("title"));
        forumArticle.setContent(resultSet.getString("content"));
        forumArticle.setViews(resultSet.getInt("views"));
        forumArticle.setLikes(resultSet.getInt("likes"));
        forumArticle.setUnlikes(resultSet.getString("unlikes"));
        forumArticle.setPostDate(resultSet.getTimestamp("post_date"));
        forumArticle.setModifiedDate(resultSet.getTimestamp("modified_date"));
        // 判斷 sql 語句是否有查詢相應欄位
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columns = resultSetMetaData.getColumnCount();
        for (int j = 1; j <= columns; j++) {
            if (resultSetMetaData.getColumnLabel(j).equals("author")) {
                forumArticle.setAuthor(resultSet.getString("author"));
            }
            if (resultSetMetaData.getColumnLabel(j).equals("feedbacks")) {
                forumArticle.setFeedbacks(resultSet.getInt("feedbacks"));
            }
            if (resultSetMetaData.getColumnLabel(j).equals("message_id")) {
                forumArticle.setMessageId(resultSet.getInt("message_id"));
            }
            if (resultSetMetaData.getColumnLabel(j).equals("commenter_id")) {
                forumArticle.setCommenterId(resultSet.getInt("commenter_id"));
            }
            if (resultSetMetaData.getColumnLabel(j).equals("commenter")) {
                forumArticle.setCommenter(resultSet.getString("commenter"));
            }
            if (resultSetMetaData.getColumnLabel(j).equals("comment")) {
                forumArticle.setComment(resultSet.getString("comment"));
            }
            if (resultSetMetaData.getColumnLabel(j).equals("comment_date")) {
                forumArticle.setCommentDate(resultSet.getTimestamp("comment_date"));
            }
            if (resultSetMetaData.getColumnLabel(j).equals("comment_edited")) {
                forumArticle.setCommentEdited(resultSet.getTimestamp("comment_edited"));
            }
            if (resultSetMetaData.getColumnLabel(j).equals("floor")) {
                forumArticle.setFloor(resultSet.getInt("floor"));
            }
        }

        return forumArticle;
    }
}
