package com.bridge.community.controller

import com.bridge.community.service.TopicService
import com.bridge.community.dto.TopicCreateDto
import com.bridge.community.dto.TopicDto
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/topics")
class TopicController(
    private val topicService: TopicService
) {
    @GetMapping
    fun getTopics(): List<TopicDto> {
        return topicService.getTopics()
    }

    @PostMapping
    fun createTopic(
        @RequestBody topicCreateDto: TopicCreateDto
    ) {
        topicService.createTopic(topicCreateDto)
    }

    @DeleteMapping("/{id}")
    fun deleteTopic(
        @PathVariable id: Long
    ) {
        topicService.deleteTopic(id)
    }
}