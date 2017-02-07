package edu.wctc.cbg.bookwebapp.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Chris Gonzalez 2017
 */
public class AuthorService {
    public final List<Author> retrieveAuthorInformation(){
        return Arrays.asList(
                new Author(010,"Dr. Seuss",new Date()),
                new Author(051,"Mark Twain",new Date()),
                new Author(214,"W. Shakespeare",new Date()),
                new Author(003,"G. Orwell",new Date()));
    }
}
