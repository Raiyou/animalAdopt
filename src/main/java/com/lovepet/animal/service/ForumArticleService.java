package com.lovepet.animal.service;

import com.lovepet.animal.dto.ForumArticleQueryParams;
import com.lovepet.animal.dto.ForumArticleRequest;
import com.lovepet.animal.model.ForumArticle;

import java.util.List;

public interface ForumArticleService {

    Integer countForumArticle(ForumArticleQueryParams forumArticleQueryParams);

    List<ForumArticle> getForumArticles(ForumArticleQueryParams forumArticleQueryParams);

    List<ForumArticle> getForumArticleById(Integer forumArticleId);

    Integer createForumArticle(ForumArticleRequest forumArticleRequest);

    void updateForumArticle(Integer forumArticleId, ForumArticleRequest forumArticleRequest);

    void deleteForumArticleById(Integer forumArticleUserId, Integer forumArticleId);

}
