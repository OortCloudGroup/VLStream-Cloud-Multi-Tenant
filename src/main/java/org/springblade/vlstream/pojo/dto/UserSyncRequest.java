package org.springblade.vlstream.pojo.dto;

import lombok.Data;

/**
 * User synchronization requestDTO
 */
@Data
public class UserSyncRequest {

    /**
     * userID
     */
    private String userId;

    /**
     * tenantID
     */
    private String tenantId;

    /**
     * Login account
     */
    private String loginId;

    /**
     * Username
     */
    private String userName;

    /**
     * Login time
     */
    private String loginTime;

    /**
     * Log inIP
     */
    private String loginIP;

    /**
     * Login type
     */
    private Integer loginType;

    /**
     * client type
     */
    private String client;

    /**
     * access token
     */
    private String accessToken;
}
