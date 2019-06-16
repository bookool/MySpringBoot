package com.bookool.myboot.elasticsearch.document;

import com.bookool.myboot.elasticsearch.config.ElasticsearchConfig;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;

import java.util.Date;

/**
 * 用户doc
 *
 * @author Tommy
 */
@Document(indexName = ElasticsearchConfig.INDEX_NAME, type = UserDocument.TYPE_NAME)
@Mapping(mappingPath = "com/bookool/myboot/elasticsearch/UserMapping.json")
@Data
public class UserDocument {

    /**
     * type名称
     */
    public static final String TYPE_NAME = "user";

    /**
     * id
     */
    @Id
    private String id;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 用户名(至少3位,必须包含非数字非符合的字符,不能包含@符)
     */
    private String userName;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 微信OpenID
     */
    private String wxOpenid;

    /**
     * 微信UnionID
     */
    private String wxUnionid;

    /**
     * 用户修改密码的时间
     */
    private Date passwordModified;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 用户状态
     */
    private Short userState;

    /**
     * 用户等级
     */
    private Short userStatus;

    /**
     * 用户备注
     */
    private String remark;

}
