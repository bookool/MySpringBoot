package com.bookool.myboot.elasticsearch.repository;

import com.bookool.myboot.elasticsearch.document.UserDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * 用户ES操作层
 *
 * @author Tommy
 */

@Component
public interface UserDocumentRepository extends ElasticsearchRepository<UserDocument, String> {

}
