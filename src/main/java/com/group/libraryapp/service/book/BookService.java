package com.group.libraryapp.service.book;

import com.group.libraryapp.domain.book.Book;
import com.group.libraryapp.domain.book.BookRepository;
import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserLoanHistory;
import com.group.libraryapp.domain.user.UserLoanHistoryRepository;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.dto.book.request.BookCreateRequest;
import com.group.libraryapp.dto.book.request.BookLoanRequest;
import com.group.libraryapp.dto.book.request.BookReturnRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final UserLoanHistoryRepository userLoanHistoryRepository;
    private final UserRepository userRepository;
    public BookService(BookRepository bookRepository,
                       UserLoanHistoryRepository userLoanHistoryRepository,
                       UserRepository userRepository
    ) {
        this.bookRepository = bookRepository;
        this.userLoanHistoryRepository = userLoanHistoryRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveBook(BookCreateRequest request){
        bookRepository.save(new Book(request.getName()));
    }


    @Transactional
    public void returnBook(BookReturnRequest request) {
        User user = userRepository.findByName(request.getUserName())
                .orElseThrow(IllegalArgumentException::new);
        user.returnBook(request.getBookName());
    }




    @Transactional
    public void loanBook(BookLoanRequest request) {
        Book book = bookRepository.findByName(request.getBookName())
                .orElseThrow(IllegalArgumentException::new); //책이 있는지 책레포지토리 에서 find시그니쳐 함수 보고
        if (userLoanHistoryRepository.existsByBookNameAndIsReturn(book.getName(), false))
        {
            throw new IllegalArgumentException(" 진작 대출되어 있는 책입니다 ");
        } //대출 목록에서 대출 여부를 시그니처 함수로 또 체크하고

        User user = userRepository.findByName(request.getUserName()) //request에서 받아온 사용자이름이 잇는지 확인하고
                .orElseThrow(IllegalArgumentException::new);//잇으면 객체로 가져오고 없으면 예외발생시키고
        user.loanBook(book.getName());
    } // 대출api호출(사용자이름, 책이름) ->  책이름 잇는지 책 레포지 찾아보고 -> 책이 빌려져잇는지 확인하고 -> 책이 빌려져있다면 그 책이름으로
        //대출 되어있는지 여부를 확인하고 -> 대출 안되어있다면 이제는 유저이름이 존재하는지 체크하고 -> 존재하면 id가져와서 책이름이랑 같이 대출 db에 저장
}
