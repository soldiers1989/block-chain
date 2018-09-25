package com.xdaocloud.futurechain.controller;

import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.dto.req.feedback.AddFeedbackRequest;
import com.xdaocloud.futurechain.service.FeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 意见反馈
 *
 * @author LuoFuMin
 */
@RestController
@Api(value = "FeedbackController", description="意见反馈")
@RequestMapping("/api/app/")
public class FeedbackController{

    @Autowired
    private FeedbackService feedbackService;

    /**
     * 意见反馈
     *
     * @param request
     * @param bindingResult
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "添加意见反馈")
    @PostMapping("v1/feedback/add")
    public ResultInfo<?> addFeedbackV1(@Valid @RequestBody AddFeedbackRequest request, BindingResult bindingResult)
            throws Exception {

        return feedbackService.addFeedback(request);
    }


}
