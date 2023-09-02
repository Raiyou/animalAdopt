package com.lovepet.animal.service.impl;

import com.lovepet.animal.dao.ForumArticleDao;
import com.lovepet.animal.dto.ForumArticleQueryParams;
import com.lovepet.animal.dto.ForumArticleRequest;
import com.lovepet.animal.model.ForumArticle;
import com.lovepet.animal.service.ForumArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ForumArticleImpl implements ForumArticleService {

    @Autowired
    private ForumArticleDao forumArticleDao;

    @Override
    public Integer countForumArticle(ForumArticleQueryParams forumArticleQueryParams) {
        return forumArticleDao.countForumArticle(forumArticleQueryParams);
    }

    @Override
    public List<ForumArticle> getForumArticles(ForumArticleQueryParams forumArticleQueryParams) {
        return forumArticleDao.getForumArticles(forumArticleQueryParams);
    }

    @Override
    public List<ForumArticle> getForumArticleById(Integer forumArticleId) {
        return forumArticleDao.getForumArticleById(forumArticleId);
    }

    @Override
    public Integer createForumArticle(ForumArticleRequest forumArticleRequest) {
        return forumArticleDao.createForumArticle(forumArticleRequest);
    }

    @Override
    public void updateForumArticle(Integer forumArticleId, ForumArticleRequest forumArticleRequest) {
        forumArticleDao.updateForumArticle(forumArticleId, forumArticleRequest);
    }

    @Override
    public void deleteForumArticleById(Integer forumArticleUserId, Integer forumArticleId) {
        forumArticleDao.deleteForumArticleById(forumArticleUserId, forumArticleId);
    }


}

