package com.nijiahao.feign.api.feign;

import com.nijiahao.common.core.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * value = "campus-ai-llm" (LLM服务的应用名称)
 * contextId = "remoteLlmService" (Bean名称)
 */
@FeignClient(contextId = "remoteLlmService" , value = "campus-ai-service-llm")
public interface RemoteLlmService {

}
