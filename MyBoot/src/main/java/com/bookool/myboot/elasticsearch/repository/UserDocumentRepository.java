package com.bookool.myboot.elasticsearch.repository;

import com.bookool.myboot.elasticsearch.document.UserDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户ES操作层
 *
 * @author Tommy
 */

@Component
public interface UserDocumentRepository extends ElasticsearchRepository<UserDocument, String> {

    /**
     * 通过用户名查找用户
     * @param userName 用户名
     * @return 用户
     */
    List<UserDocument> findByUserNameLike(String userName);

    /**
     * 通过手机号查找用户
     * @param mobile 手机号
     * @return 用户
     */
    List<UserDocument> findByMobile(String mobile);

}
