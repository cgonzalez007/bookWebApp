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
                new Author(0010,"Dr. Seuss",new Date()),
                new Author(0451,"Mark Twain",new Date()),
                new Author(4521,"William Shakespeare",new Date()),
                new Author(0563,"George Orwell",new Date()));
    }
}
