package com.bookool.myboot.elasticsearch.service.impl;

import com.bookool.myboot.elasticsearch.document.UserDocument;
import com.bookool.myboot.elasticsearch.repository.UserDocumentRepository;
import com.bookool.myboot.elasticsearch.service.UserEsService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户搜索实现
 *
 * @author Tommy
 * @date 190927
 */
@Service("UserEsService")
public class UserEsServiceImpl implements UserEsService {

    /**
     * 仅在建立Type(表)时使用
     */
    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 数据操作
     */
    @Resource
    private UserDocumentRepository userDocumentRepository;

    /**
     * 建立Type(表)
     *
     * @param clazz doc
     * @return 是否成功
     */
    @Override
    public <T> boolean putMapping(Class<T> clazz) {
        return elasticsearchTemplate.putMapping(clazz);
    }

    /**
     * 添加一个用户
     *
     * @param userDocument 用户实体
     * @return 是否成功
     */
    @Override
    public boolean save(UserDocument userDocument) {
        try {
            userDocumentRepository.save(userDocument);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 添加多个用户
     *
     * @param userDocuments 用户实体列表
     * @return 是否成功
     */
    @Override
    public boolean saveAll(List<UserDocument> userDocuments) {
        try {
            userDocumentRepository.saveAll(userDocuments);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除一个用户
     *
     * @param userId 用户id
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String userId) {
        try {
            userDocumentRepository.deleteById(userId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取一个用户
     *
     * @param userId 用户id
     * @return 获取的用户
     */
    @Override
    public UserDocument getById(String userId) {
        try {
            return userDocumentRepository.findById(userId).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取用户通过用户名
     *
     * @param userName 用户名
     * @return 获取的用户
     */
    @Override
    public List<UserDocument> getByUserName(String userName) {
        return userDocumentRepository.findByUserNameLike(userName);
    }

    /**
     * 获取用户通过手机号
     *
     * @param mobile 手机号
     * @return 获取的用户
     */
    @Override
    public List<UserDocument> getByMobile(String mobile) {
        return userDocumentRepository.findByMobile(mobile);
    }

    /**
     * 获取用户通过用户名（分页）
     *
     * @param userName 用户名
     * @param page 页数
     * @param size 每页数量
     * @return 获取的用户
     */
    @Override
    public List<UserDocument> getByUserNamePage(String userName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.should(QueryBuilders.matchQuery("userName", userName))
                .should(QueryBuilders.matchQuery("nickName", userName));
        FunctionScoreQueryBuilder functionScoreQueryBuilder =
                QueryBuilders.functionScoreQuery(queryBuilder);
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withPageable(pageable)
                .withQuery(functionScoreQueryBuilder).build();
        Page<UserDocument> searchPageResults = userDocumentRepository.search(searchQuery);
        return searchPageResults.getContent();
    }

}
