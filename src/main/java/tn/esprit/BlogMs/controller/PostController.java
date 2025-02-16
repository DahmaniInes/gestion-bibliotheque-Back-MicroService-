// PostController.java
package tn.esprit.BlogMs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.BlogMs.entity.Post;
import tn.esprit.BlogMs.service.NewsService;
import tn.esprit.BlogMs.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final NewsService newsService;

    @GetMapping
    public Page<Post> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return postService.getAllPosts(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PostMapping("/category/{categoryId}")
    public ResponseEntity<Post> createPost(@RequestBody Post post, @PathVariable Long categoryId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(post, categoryId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post post) {
        return ResponseEntity.ok(postService.updatePost(id, post));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Post>> getSuggestedPost(@PathVariable Long categoryId) {
        return ResponseEntity.ok(postService.getPostsByCategory(categoryId));
    }

    @GetMapping("/{id}/news-suggestions")
    public ResponseEntity<List<NewsService.NewsPost>> getNewsSuggestions(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        List<NewsService.NewsPost> suggestions = newsService.getNewsPosts(post.getTitle());
        return ResponseEntity.ok(suggestions);
    }
}