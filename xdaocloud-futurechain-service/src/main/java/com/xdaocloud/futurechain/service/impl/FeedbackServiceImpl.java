package com.xdaocloud.futurechain.service.impl;

import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.dto.req.feedback.AddFeedbackRequest;
import com.xdaocloud.futurechain.mapper.FeedbackMapper;
import com.xdaocloud.futurechain.model.Feedback;
import com.xdaocloud.futurechain.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackMapper feedbackMapper;

    /**
     * 意见反馈
     *
     * @param request
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @Override
    @Transactional
    public ResultInfo<?> addFeedback(AddFeedbackRequest request) {
        Feedback feedback = new Feedback();
        feedback.setContact(request.getContact());
        feedback.setContent(request.getContent());
        feedback.setUserId(Long.valueOf(request.getUserid()));
        feedbackMapper.insertSelective(feedback);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }
}
