package com.jaspreet.foodsharing;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RetroRepository extends MongoRepository<ElementModel, String>{

	@Query("{'username' : ?0 }")
	List<ElementModel> getByUsername(String username);

	@Query("{'username' : { $ne : ?0 } }")
	List<ElementModel> getOtherUserData(String username);
}
