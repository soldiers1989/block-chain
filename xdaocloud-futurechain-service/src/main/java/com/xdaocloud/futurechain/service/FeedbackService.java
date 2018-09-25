package com.xdaocloud.futurechain.service;

import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.dto.req.feedback.AddFeedbackRequest;

public interface FeedbackService {

    ResultInfo<?> addFeedback(AddFeedbackRequest request);
}
