package com.bridge.community.controller

import com.bridge.community.dto.*
import com.bridge.community.service.BoardService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/boards")
class BoardController(
    private val boardService: BoardService
) {
    @GetMapping
    fun getBoards(): List<BoardDto> {
        return boardService.getBoards()
    }

    @GetMapping("/topics")
    fun getBoardsFromTopic(
        @RequestParam topicId: Long
    ): List<BoardDto> {
        return boardService.getBoardsFromTopic(topicId)
    }

    @GetMapping("/{id}")
    fun getBoardById(
        @PathVariable id: Long
    ): BoardContentsDto {
        return boardService.getBoardById(id)
    }

    @PostMapping
    fun createBoard(@RequestBody boardCreateDto: BoardCreateDto) {
        boardService.createBoard(boardCreateDto)
    }

    @PutMapping("/{id}")
    fun updateBoardById(
        @PathVariable id: Long,
        @RequestBody boardUpdateDto: BoardUpdateDto
    ) {
        boardService.updateBoardById(id, boardUpdateDto)
    }

    @DeleteMapping("/{id}")
    fun deleteBoardById(
        @PathVariable id: Long
    ) {
        boardService.deleteBoardById(id)
    }

    @PutMapping("{id}/recommend")
    fun createBoardRecommend(
        @PathVariable id: Long
    ) {
        boardService.createBoardRecommend(id)
    }

    @DeleteMapping("/{id}/recommend")
    fun deleteBoardRecommend(
        @PathVariable id: Long
    ) {
        boardService.deleteBoardRecommend(id)
    }

    @GetMapping("/{id}/replies")
    fun getBoardReply(
        @PathVariable id: Long
    ): List<BoardReplyDto> {
        return boardService.getBoardReply(id)
    }

    @PostMapping("/replies")
    fun createBoardReply(
        @RequestBody boardReplyCreateDto: BoardReplyCreateDto,
    ) {
        boardService.createBoardReply(boardReplyCreateDto)
    }

    @DeleteMapping("/replies/{id}")
    fun deleteBoardReplyById(
        @PathVariable id: Long
    ) {
        boardService.deleteBoardReplyById(id)
    }
}