package io.goorm.board.controller;

import io.goorm.board.entity.Post;
import io.goorm.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 메인 페이지
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // 게시글 목록 (기본)
    @GetMapping("/posts")
    public String list(Model model) {
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);
        return "post/list";
    }

    // 게시글 상세 조회
    @GetMapping("/posts/{seq}")
    public String show(@PathVariable Long seq, Model model) {
        Post post = postService.findBySeq(seq);
        model.addAttribute("post", post);
        return "post/show";
    }

    // 게시글 작성 폼
    @GetMapping("/posts/new")
    public String createForm(Model model) {
        model.addAttribute("post", new Post());
        return "post/form";
    }

    // 게시글 저장 → 목록으로 (RedirectAttributes 파라미터 추가)
    @PostMapping("/posts")
    public String create(@ModelAttribute Post post, RedirectAttributes redirectAttributes) {
        postService.save(post);
        redirectAttributes.addFlashAttribute("message", "✅ 게시글이 등록되었습니다!");
        return "redirect:/posts";
    }

    // 게시글 수정 폼
    @GetMapping("/posts/{seq}/edit")
    public String editForm(@PathVariable Long seq, Model model) {
        Post post = postService.findBySeq(seq);
        model.addAttribute("post", post);
        return "post/form";
    }

    // 게시글 수정 → 상세보기로 (RedirectAttributes 파라미터 추가)
    @PostMapping("/posts/{seq}")
    public String update(@PathVariable Long seq, @ModelAttribute Post post, RedirectAttributes redirectAttributes) {
        postService.update(seq, post);
        redirectAttributes.addFlashAttribute("message", "✅ 게시글이 수정되었습니다!");
        return "redirect:/posts/" + seq;
    }

    // 게시글 삭제 → 목록으로 (RedirectAttributes 파라미터 추가)
    @PostMapping("/posts/{seq}/delete")
    public String delete(@PathVariable Long seq, RedirectAttributes redirectAttributes) {
        postService.delete(seq);
        redirectAttributes.addFlashAttribute("message", "✅ 게시글이 삭제되었습니다!");
        return "redirect:/posts";
    } }
