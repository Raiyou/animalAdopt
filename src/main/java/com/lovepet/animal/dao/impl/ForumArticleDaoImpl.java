package com.lovepet.animal.dao.impl;

import com.lovepet.animal.dao.ForumArticleDao;
import com.lovepet.animal.dto.ForumArticleQueryParams;
import com.lovepet.animal.dto.ForumArticleRequest;
import com.lovepet.animal.dto.PersonalAnimalQueryParams;
import com.lovepet.animal.model.ForumArticle;
import com.lovepet.animal.rowmapper.ForumArticleRowmapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ForumArticleDaoImpl implements ForumArticleDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer countForumArticle(ForumArticleQueryParams forumArticleQueryParams) {
        String sql = "SELECT count(*) FROM article WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        //查詢條件
        sql = addFilteringSql(sql, map, forumArticleQueryParams);

        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);

        return total;
    }

    @Override
    public List<ForumArticle> getForumArticles(ForumArticleQueryParams forumArticleQueryParams) {
        String sql = "SELECT * FROM (" +
                "SELECT a.article_id, a.user_id, a.type, a.title, a.content, a.views, a.likes, a.unlikes, " +
                "a.post_date, a.modified_date, u.name AS author, count(*) AS feedbacks FROM article AS a " +
                "LEFT JOIN user AS u ON a.user_id = u.user_id " +
                "LEFT JOIN feedback AS f ON a.article_id = f.article_id " +
                "GROUP BY a.article_id) AS subquery " +
                "WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        //查詢條件
        sql = addFilteringSql(sql, map, forumArticleQueryParams);

        //排序
        sql = sql + " ORDER BY " + forumArticleQueryParams.getOrderBy() + " " + forumArticleQueryParams.getSort();

        //分頁
        sql = sql + " LIMIT :limit OFFSET :offset";
        map.put("limit", forumArticleQueryParams.getLimit());
        map.put("offset", forumArticleQueryParams.getOffset());

        List<ForumArticle> forumArticleList = namedParameterJdbcTemplate.query(sql, map, new ForumArticleRowmapper());

        return forumArticleList;
    }

    @Override
    public ForumArticle getForumArticleById(Integer forumArticleId) {
        String sql = "SELECT * FROM article WHERE article_id = :articleId";

        Map<String, Object> map = new HashMap<>();
        map.put("articleId", forumArticleId);

        List<ForumArticle> forumArticleList = namedParameterJdbcTemplate.query(sql, map, new ForumArticleRowmapper());

        if (forumArticleList.size() > 0) {
            return forumArticleList.get(0);
        } else {
            return null;
        }
    }

    private String addFilteringSql(String sql, Map<String, Object> map, ForumArticleQueryParams forumArticleQueryParams) {
        if (forumArticleQueryParams.getArticleId() != null) {
            sql = sql + " AND article_id = :articleId";
            map.put("articleId", forumArticleQueryParams.getArticleId());
        }

        if (forumArticleQueryParams.getUserId() != null) {
            sql = sql + " AND user_id = :userId";
            map.put("userId", forumArticleQueryParams.getUserId());
        }

        if (forumArticleQueryParams.getType() != null) {
            sql = sql + " AND type = :type";
            map.put("type", forumArticleQueryParams.getType());
        }

        if (forumArticleQueryParams.getSearch() != null) {
            sql = sql + " AND (title LIKE :search1 OR content LIKE :search2)";
            map.put("search1", "%" + forumArticleQueryParams.getSearch() + "%");
            map.put("search2", "%" + forumArticleQueryParams.getSearch() + "%");
        }
        return sql;
    }

    @Override
    public Integer createForumArticle(ForumArticleRequest forumArticleRequest) {
        String sql = "INSERT INTO article(user_id, type, title, content, views, likes, unlikes, post_date, modified_date) " +
                "VALUES (:userId, :type, :title, :content, 0, 0, 0, :postDate, :modifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", forumArticleRequest.getUserId());
        map.put("type", forumArticleRequest.getType());
        map.put("title", forumArticleRequest.getTitle());
        map.put("content", forumArticleRequest.getContent());

        Date now = new Date();
        map.put("postDate", now);
        map.put("modifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int forumArticleId = keyHolder.getKey().intValue();

        return forumArticleId;
    }

    @Override
    public void updateForumArticle(Integer forumArticleId, ForumArticleRequest forumArticleRequest) {
        String sql = "UPDATE article SET type = :type, title = :title, content = :content, modified_date = :modifiedDate " +
                "WHERE article_id = :articleId";

        Map<String, Object> map = new HashMap<>();
        map.put("type", forumArticleRequest.getType());
        map.put("title", forumArticleRequest.getTitle());
        map.put("content", forumArticleRequest.getContent());
        map.put("modifiedDate", new Date());

        map.put("articleId", forumArticleId);

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void deleteForumArticleById(Integer forumArticleUserId, Integer forumArticleId) {
        String sql = "DELETE FROM article WHERE user_id = :userId AND article_id = :articleId";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", forumArticleUserId);
        map.put("articleId", forumArticleId);

        namedParameterJdbcTemplate.update(sql, map);

        String sql1 = "DELETE FROM feedback WHERE article_id = :articleId";

        Map<String, Object> map1 = new HashMap<>();
        map1.put("articleId", forumArticleId);

        namedParameterJdbcTemplate.update(sql1, map1);

    }

    @Override
    public void updateForum(ForumArticle forumArticle, Integer id) {
        String sql = "";
        Map<String,Object> map = new HashMap<>();
        System.out.println(forumArticle.getLikes());
        System.out.println(forumArticle.getViews());

        int likes = getForumArticleById(id).getLikes();
        System.out.println(likes);

        int views = getForumArticleById(id).getViews();
        System.out.println(views);


        if (forumArticle.getLikes() > likes) {
            sql = "UPDATE article SET likes = :likes WHERE article_id = :articleId";


            map.put("likes", forumArticle.getLikes());
            map.put("articleId", id);
        }

        if (forumArticle.getViews() > views) {
            sql = "UPDATE article SET views = :views WHERE article_id = :articleId";

            map.put("views", forumArticle.getViews());
            map.put("articleId", id);
        }

        namedParameterJdbcTemplate.update(sql, map);
    }
}
