package org.springblade.vlstream.pojo.dto;

import lombok.Data;

/**
 * Unified user center user informationDTO
 */
@Data
public class UserCenterUser {

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
     * last request time
     */
    private String lastRequestTime;

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
