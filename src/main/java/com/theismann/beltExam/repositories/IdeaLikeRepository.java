package com.theismann.beltExam.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.theismann.beltExam.models.IdeaLike;

@Repository
public interface IdeaLikeRepository extends CrudRepository <IdeaLike, Long> {

}
