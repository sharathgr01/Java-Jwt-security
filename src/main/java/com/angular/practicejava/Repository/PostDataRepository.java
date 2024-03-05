package com.angular.practicejava.Repository;

import com.angular.practicejava.entity.PostData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostDataRepository extends JpaRepository<PostData,Integer> {


}
