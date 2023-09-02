package com.lovepet.animal.dao;

import com.lovepet.animal.dto.ForumArticleQueryParams;
import com.lovepet.animal.dto.ForumArticleRequest;
import com.lovepet.animal.model.ForumArticle;

import java.util.List;

public interface ForumArticleDao {

    Integer countForumArticle(ForumArticleQueryParams forumArticleQueryParams);

    List<ForumArticle> getForumArticles(ForumArticleQueryParams forumArticleQueryParams);

    List<ForumArticle> getForumArticleById(Integer forumArticleId);

    Integer createForumArticle(ForumArticleRequest forumArticleRequest);

    void updateForumArticle(Integer forumArticleId, ForumArticleRequest forumArticleRequest);

    void deleteForumArticleById(Integer forumArticleUserId, Integer forumArticleId);

    void updateForum(ForumArticle forumArticle, Integer id);
}
