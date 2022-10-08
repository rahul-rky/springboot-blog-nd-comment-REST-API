package com.blog.api.service.impl;

import com.blog.api.entity.Comment;
import com.blog.api.entity.Post;
import com.blog.api.exception.BlogAPIException;
import com.blog.api.exception.ResourceNotFoundException;
import com.blog.api.payload.CommentDto;
import com.blog.api.repository.CommentRepository;
import com.blog.api.repository.PostRepository;
import com.blog.api.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
    Comment comment = mapToEntity(commentDto);

    //retreive post by id
    Post post =postRepository
            .findById(postId)
            .orElseThrow(()->new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post"));
    //set post to comment entity
    comment.setPost(post);

    //save comment to database
        Comment newComment = commentRepository.save(comment);
        return mapToDTO(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
       //retrieve comment by postId - create custom method in repository
        List<Comment> comments = commentRepository.findByPostId(postId);
        //converting list of comment to list of commnetDto
        return comments.stream().map(comment->mapToDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        //retrieve post by id
        Post post = postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
        //retreive comment by id
        Comment comment=commentRepository.findById(commentId)
                .orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
        if(!comment.getPost().getId().equals(post.getId()))
            throw new  BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");

        return mapToDTO(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentUpdate) {
        //retreive post entity by id
        Post post=postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
        //retreive comment entity by id
        Comment comment=commentRepository.findById(commentId)
                .orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));

        if(!comment.getPost().getId().equals(post.getId()))
            throw new  BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");

        comment.setName(commentUpdate.getName());
        comment.setEmail(commentUpdate.getEmail());
        comment.setBody(commentUpdate.getBody());
        Comment commentUpdated=commentRepository.save(comment);
        return mapToDTO(commentUpdated);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {

        Post post =postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
        Comment comment =commentRepository.findById(commentId)
                .orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));

        if(!comment.getPost().getId().equals(post.getId()))
            throw new  BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");

        commentRepository.delete(comment);
    }

    //convert Entity to DTO
    private CommentDto mapToDTO(Comment comment){
        CommentDto commentDto=new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        return commentDto;
    }

    //convert DTO to Entity
    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return comment;
    }
}
