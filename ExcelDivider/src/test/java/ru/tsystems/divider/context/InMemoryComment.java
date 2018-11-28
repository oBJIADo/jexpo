package ru.tsystems.divider.context;

import ru.tsystems.divider.dao.api.CommentDao;
import ru.tsystems.divider.entity.Comment;

public class InMemoryComment extends InMemoryDaoGeneral<Comment> implements CommentDao, ContextSimmulator<Comment>{

    private static InMemoryComment dao;

    public static InMemoryComment getInMemoryDao(){
        if(dao == null){
            dao = new InMemoryComment();
        }
        return dao;
    }

    private InMemoryComment() {
        super();
        reset();
    }
}
