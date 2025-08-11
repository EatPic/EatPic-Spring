package EatPic.spring.domain.card.repository;

import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.card.mapping.CardHashtag;
import java.util.List;
import java.util.Optional;

import EatPic.spring.domain.hashtag.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardHashtagRepository extends JpaRepository<CardHashtag, Long> {

  List<CardHashtag> findAllByCardId(Long cardId);
  List<CardHashtag> findByCard(Card card);

  List<CardHashtag> findByHashtag(Hashtag hashtag);      // Hashtag 객체
}
