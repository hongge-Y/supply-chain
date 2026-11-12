package cn.dsscm.service.impl;

import cn.dsscm.dao.NewsMapper;
import cn.dsscm.pojo.News;
import cn.dsscm.service.NewsService;
import cn.dsscm.tools.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 新闻业务逻辑实现类
 */
@Service
@Transactional
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsMapper newsMapper;

    @Override
    public List<News> getNewsList(String title, int pageIndex, int pageSize) {
        PageUtil pageUtil = new PageUtil(pageIndex, pageSize, 0);
        return newsMapper.selectByPage(title, pageUtil.getStart(), pageSize);
    }

    @Override
    public int getNewsCount(String title) {
        return newsMapper.selectCount(title);
    }

    @Override
    public boolean addNews(News news) {
        news.setCreationDate(new Date());
        if (news.getApprovalStatus() == null) {
            news.setApprovalStatus(2);
        }
        return newsMapper.insertNews(news) > 0;
    }

    @Override
    public boolean modifyNews(News news) {
        news.setModifyDate(new Date());
        return newsMapper.updateNews(news) > 0;
    }

    @Override
    public boolean deleteNews(Long id) {
        return newsMapper.deleteNews(id) > 0;
    }

    @Override
    public News getNewsById(Long id) {
        return newsMapper.selectById(id);
    }

    @Override
    public boolean submitForApproval(Long id) {
        News news = newsMapper.selectById(id);
        if (news != null) {
            news.setApprovalStatus(1);
            return newsMapper.updateNews(news) > 0;
        }
        return false;
    }
}