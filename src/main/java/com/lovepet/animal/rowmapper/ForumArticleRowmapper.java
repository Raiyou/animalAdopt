package com.lovepet.animal.rowmapper;

import com.lovepet.animal.model.ForumArticle;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
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
        forumArticle.setAuthor(resultSet.getString("author"));
        forumArticle.setFeedbacks(resultSet.getInt("feedbacks"));

        return forumArticle;
    }
}
