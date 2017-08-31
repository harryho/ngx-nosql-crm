package com.nnc.app.repository;


import com.nnc.app.domain.OAuth2AuthenticationApproval;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OAuth2ApprovalRepository extends MongoRepository<OAuth2AuthenticationApproval, String> {

    List<OAuth2AuthenticationApproval> findByUserIdAndClientId(String userId, String clientId);

    List<OAuth2AuthenticationApproval> findByUserIdAndClientIdAndScope(String userId, String clientId, String scope);
}
