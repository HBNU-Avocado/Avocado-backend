package io.wisoft.capstonedesign.repository;

import io.wisoft.capstonedesign.domain.Board;
import io.wisoft.capstonedesign.domain.Member;
import io.wisoft.capstonedesign.exception.nullcheck.NullBoardException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final EntityManager em;

    /**
     * 게시글 작성(저장)
     */
    public void save(Board board) {
        em.persist(board);
    }

    /**
     * 게시글 단건 조회
     */
    public Board findOne(Long boardId) {
        return em.find(Board.class, boardId);
    }

    /**
     * 게시글 전체 조회
     */
    public List<Board> findAll() {
        return em.createQuery("select b from Board b", Board.class)
                .getResultList();
    }

    /**
     * 특정 작성자의 게시글 조회
     */
    public List<Board> findByMemberId(Long memberId) {

        return em.createQuery("select b from Board b join b.member m where m.id = :id", Board.class)
                .setParameter("id", memberId)
                .getResultList();
    }

    /**
     * 게시글 목록 오름차순 조회
     */
    public List<Board> findAllcreateAtASC() {
        return em.createQuery("select b from Board b order by b.createAt", Board.class)
                .getResultList();
    }

    /**
     * 게시글 목록 내림차순 조회
     */
    public List<Board> findAllcreateAtDESC() {
        return em.createQuery("select b from Board b order by b.createAt desc", Board.class)
                .getResultList();
    }
}
