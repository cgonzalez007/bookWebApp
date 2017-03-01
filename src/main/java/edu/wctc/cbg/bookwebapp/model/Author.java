package edu.wctc.cbg.bookwebapp.model;

import java.util.Date;
import java.util.Objects;

/**
 * 
 * @author Chris Gonzalez 2017
 */
public class Author {
    private final String NULL_MSG = " cannot be null";
    
    private Integer authorId;
    private String authorName;
    private Date dateAdded;
    /**
     * 
     */
    public Author() {
    }
    /**
     * 
     * @param authorId 
     */
    public Author(Integer authorId) {
        this.authorId = authorId;
    }
    /**
     * 
     * @param authorId
     * @param authorName
     * @param dateAdded 
     */
    public Author(Integer authorId, String authorName, Date dateAdded) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.dateAdded = dateAdded;
    }
    /**
     * 
     * @return 
     */
    public final Integer getAuthorId() {
        return authorId;
    }
    /**
     * 
     * @param authorId
     * @throws IllegalArgumentException 
     */
    public final void setAuthorId(Integer authorId) throws IllegalArgumentException {
        if(authorId == null) {
            throw new IllegalArgumentException("author id" + NULL_MSG);
        }
        this.authorId = authorId;
    }
    /**
     * 
     * @return 
     */
    public final String getAuthorName() {
        return authorName;
    }
    /**
     * 
     * @param authorName
     * @throws IllegalArgumentException 
     */
    public final void setAuthorName(String authorName) throws IllegalArgumentException {
        if(authorName == null || authorName.isEmpty()) {
            throw new IllegalArgumentException("author name is required");
        }
        this.authorName = authorName;
    }
    /**
     * 
     * @return 
     */
    public final Date getDateAdded() {
        return dateAdded;
    }
    /**
     * 
     * @param dateAdded
     * @throws IllegalArgumentException 
     */
    public final void setDateAdded(Date dateAdded) throws IllegalArgumentException {
        if(dateAdded == null) {
            throw new IllegalArgumentException("date addedl" + NULL_MSG);
        }
         this.dateAdded = dateAdded;
    }
    /**
     * 
     * @return 
     */
    @Override
    public final int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.authorId);
        return hash;
    }
    /**
     * 
     * @param obj
     * @return 
     */
    @Override
    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Author other = (Author) obj;
        if (!Objects.equals(this.authorId, other.authorId)) {
            return false;
        }
        return true;
    }
    /**
     * 
     * @return 
     */
    @Override
    public final String toString() {
        return "Author{" + "authorId=" + authorId + ", authorName=" + authorName + ", dateAdded=" + dateAdded + '}';
    }
    
    
}
