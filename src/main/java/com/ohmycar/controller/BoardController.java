package com.ohmycar.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ohmycar.domain.BoardVO;
import com.ohmycar.domain.CommentVO;
import com.ohmycar.domain.Criteria;
import com.ohmycar.domain.UserVO;
import com.ohmycar.service.BoardService;
import com.ohmycar.service.CommentService;
import com.ohmycar.service.UserService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/board/*")
@Log4j
public class BoardController {

	private final BoardService boardService;
	private final UserService userService;
	private final CommentService commentService;

	private static final String REDIRECT_BEFORE_PAGE = "redirect:/user/mypage";// TODO 게시판 버튼 있는 곳으로 바꾸기

	public BoardController(BoardService boardService, UserService userService, CommentService commentService) {
		this.boardService = boardService;
		this.userService = userService;
		this.commentService = commentService;
	}

	@GetMapping("/write")
	public String writeBoardPage(Model model) {
		log.info("Writing");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			UserVO userVO = userService.getUserByUserId(userDetails.getUsername());
			model.addAttribute("userVO", userVO);
			return "/board/write";
		} else {
			return "/board/list";
		}
	}

	@PostMapping("/write")
	public String writePost(BoardVO boardVO) {
		boardService.write(boardVO);
		return "redirect:/board/list";
	}

	@GetMapping("/list")
	public void getList(Model model) {
		List<BoardVO> boardList = boardService.getAllPosts();
		model.addAttribute("boardList", boardList);
	}

	@PostMapping("/list")
	public void postList(Model model, BoardVO boardVO) {
		boardService.write(boardVO); // 게시글 작성 서비스 호출
		List<BoardVO> boardList = boardService.getAllPosts(); // 갱신된 게시글 목록을 다시 가져옴
		model.addAttribute("boardList", boardList); // 모델에 추가
	}

	// 게시글 읽기
	@GetMapping("/read")
	public String read(@RequestParam("bno") Long bno, Model model) {
		BoardVO board = boardService.read(bno);
		model.addAttribute("board", board);

		// 게시글에 해당하는 댓글 목록을 가져옵니다.
		List<CommentVO> commentList = commentService.getCommentsByBoardId(bno);
		model.addAttribute("commentList", commentList);

		return "board/read";
	}

	@PostMapping("/comment")
	public String writeComment(@RequestParam("bno") Long bno, @RequestParam("content") String content) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String writer = authentication.getName();

		CommentVO comment = new CommentVO();
		comment.setBno(bno);
		comment.setContent(content);
		comment.setWriter(writer);

		commentService.writeComment(comment);

		return "redirect:/board/read?bno=" + bno;
	}

	@PostMapping("/comment/register")
	public String registerComment(CommentVO comment) {
		commentService.register(comment);
		return "redirect:/board/read?bno=" + comment.getBno();
	}

	@PostMapping("/comment/modify")
	public String modifyComment(CommentVO comment) {
		commentService.modify(comment);
		return "redirect:/board/read?bno=" + comment.getBno();
	}

	@PostMapping("/comment/remove")
	public String removeComment(@RequestParam("id") Long id, @RequestParam("bno") Long bno) {
		commentService.remove(id);
		return "redirect:/board/read?bno=" + bno;
	}

	@GetMapping("/modify")
	public String modify(@RequestParam("bno") Long bno, Model model) {
		BoardVO board = boardService.read(bno);
		model.addAttribute("board", board);
		log.info("move to modify.jsp");
		return "board/modify";
	}

	@PostMapping("/modify")
	public String postModify(BoardVO boardVO, Model model) {
		boardService.modify(boardVO);
		model.addAttribute("bno", boardVO.getBno());
		return "redirect:/board/read?bno=" + boardVO.getBno(); // 수정된 게시글 읽기 페이지로 리다이렉트
	}

	@GetMapping("/delete")
	public String getDelete(@RequestParam("bno") int bno) {
		boardService.delete(bno);
		return "redirect:/board/list";
	}

}