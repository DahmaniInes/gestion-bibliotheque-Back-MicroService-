package tn.esprit.BlogMs.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tn.esprit.BlogMs.entity.Category;
import tn.esprit.BlogMs.entity.Post;
import tn.esprit.BlogMs.repository.PostRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CategoryService categoryService;

    public Page<Post> getAllPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findAll(pageable);
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));
    }

    public Post createPost(Post post, Long categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        post.setCategory(category);
        return postRepository.save(post);
    }

    public Post updatePost(Long id, Post postDetails) {
        Post post = getPostById(id);
        if(postDetails.getTitle() != null) {
            post.setTitle(postDetails.getTitle());
        }
        if(postDetails.getContent() != null) {
            post.setContent(postDetails.getContent());
        }
        if(postDetails.getAuthor() != null) {
            post.setAuthor(postDetails.getAuthor());
        }
        post.setImageUrl(postDetails.getImageUrl());
        return postRepository.save(post);
    }


    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public List<Post> getPostsByCategory(Long categoryId) {
        return postRepository.findByCategoryId(categoryId);
    }
}