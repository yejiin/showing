package com.showing.backend.api.controller;

import com.showing.backend.api.service.SeasonService;
import com.showing.backend.common.exception.handler.ErrorResponse;
import com.showing.backend.common.model.BaseResponseBody;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.showing.backend.common.model.ResponseMessage.GET_SEASON;
import static com.showing.backend.common.model.ResponseMessage.GET_SEASON_LIST;

@Api(value = "공연 API", tags = {"performance"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/performances")
public class PerformanceController {

    private final SeasonService seasonService;

    @ApiOperation(value = "퍼포먼스 별 시즌 목록", notes = "시즌의 목록을 보여준다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = GET_SEASON_LIST, response = ErrorResponse.class),
            @ApiResponse(code = 400, message = "실패", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "인증 실패", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "서버 오류", response = ErrorResponse.class)
    })
    @GetMapping("/seasons/{performanceId}")
    public ResponseEntity<BaseResponseBody> getSeasonList(
            @PathVariable("performanceId") @ApiParam(value = "목록을 얻을 퍼포먼스의 id", required = true) Long performanceId){

        return ResponseEntity.ok(BaseResponseBody.of(HttpStatus.OK, GET_SEASON_LIST, seasonService.getSeasonList(performanceId)));
    }


    @ApiOperation(value = "시즌별 공연 정보", notes = "시즌별 공연의 정보를 보여준다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = GET_SEASON),
            @ApiResponse(code = 400, message = "실패", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "인증 실패", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "서버 오류", response = ErrorResponse.class)
    })
    @GetMapping("/seasons/list/{seasonId}")
    public ResponseEntity<BaseResponseBody> getSeasonDetail(
            @PathVariable("seasonId") @ApiParam(value = "확인할 공연의 id", required = true) Long seasonId){

        return ResponseEntity.ok(BaseResponseBody.of(HttpStatus.OK, GET_SEASON, seasonService.getSeasonInfo(seasonId)));
    }

}
