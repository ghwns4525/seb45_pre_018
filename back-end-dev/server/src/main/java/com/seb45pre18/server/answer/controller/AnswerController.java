package com.seb45pre18.server.answer.controller;

import com.seb45pre18.server.answer.dto.AnswerDto;
import com.seb45pre18.server.answer.entity.Answer;
import com.seb45pre18.server.answer.mapper.AnswerMapper;
import com.seb45pre18.server.answer.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answers")
@RequiredArgsConstructor
@CrossOrigin
public class AnswerController {
    private final AnswerService answerService;
    private final AnswerMapper mapper;

    //질문글에 답변을 쓸때 답변 생성
    @PostMapping
    public ResponseEntity postAnswer(@RequestBody AnswerDto.post requestBody,
                                     @LoginAccountId Long memberId){
        requestBody.setMemberId(memberId);

        Answer answer = mapper.answerPostToAnswer(requestBody);
        Answer response = answerService.createAnswer(requestBody.getMemberId(),requestBody.getQuestionId(),answer);

        return new ResponseEntity<>(mapper.answerToAnswerResponse(response), HttpStatus.CREATED);

    }

    //질문글에 작성한 답변 내용 수정
    @PatchMapping("/{answer-id}/edit")
    public ResponseEntity patchAnswer(@PathVariable("answer-id") Long answerId,
                                      @RequestBody AnswerDto.Patch requestBody,
                                      @LoginAccountId Long memberId) {
        requestBody.setAnswerId(answerId);

        Answer response = answerService.updateAnswer(mapper.answerPatchToAnswer(requestBody), memberId);

        return new ResponseEntity<>(mapper.answerToAnswerResponse(response), HttpStatus.OK);
    }

//    // 답변 하나 조회
//    @GetMapping("/{answer-id}")
//    public ResponseEntity getAnswer(@PathVariable("answer-id") Long answerId) {
//        Answer response = answerService.findAnswer(answerId);
//
//        return new ResponseEntity<>(mapper.answerToAnswerResponse(response), HttpStatus.OK);
//    }
//
//    // 여러 답변 조회
//    @GetMapping
//    public ResponseEntity getAnswers() {
//        List<Answer> answers = answerService.findAnswers();
//        List<AnswerDto.response> response =
//                answers.stream()
//                        .map(answer -> mapper.answerToAnswerResponse(answer))
//                        .collect(Collectors.toList());
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    // 답변 삭제
    @DeleteMapping("/{answer-id}")
    public ResponseEntity deleteAnswer(@PathVariable("answer-id") Long answerId,
                                       @LoginAccountId Long memberId) {
        answerService.deleteAnswer(answerId, memberId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }



}
