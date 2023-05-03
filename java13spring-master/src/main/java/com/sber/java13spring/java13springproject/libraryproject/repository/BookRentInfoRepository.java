package com.sber.java13spring.java13springproject.libraryproject.repository;

import com.sber.java13spring.java13springproject.libraryproject.model.BookRentInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRentInfoRepository
      extends GenericRepository<BookRentInfo> {
    
    Page<BookRentInfo> getBookRentInfoByUserId(Long userId,
                                               Pageable pageable);
}
