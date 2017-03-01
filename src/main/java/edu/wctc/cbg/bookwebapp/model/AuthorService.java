package edu.wctc.cbg.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Chris Gonzalez 2017
 */
public class AuthorService {
    private static final int MIN_MAX_RECORDS_PARAMETER = 1;

    private IAuthorDao authorDao;
    /**
     * 
     * @param authorDao 
     */
    public AuthorService(IAuthorDao authorDao) {
        setAuthorDao(authorDao);
    }
    /**
     * 
     * @param authorTableName
     * @param maxRecords
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public final List<Author> retrieveAuthors(String authorTableName, int maxRecords) 
            throws ClassNotFoundException, SQLException, 
            IllegalArgumentException{
        if (authorTableName == null || authorTableName.isEmpty() || maxRecords < 
                MIN_MAX_RECORDS_PARAMETER) {
            throw new InvalidInputException();
        }
        return authorDao.getAuthorList(authorTableName,maxRecords);
    }
    /**
     * 
     * @param authorTableName
     * @param authorIdColName
     * @param authorId
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public final Author retrieveAuthor(String authorTableName, String
            authorIdColName, String authorId) throws ClassNotFoundException, 
            SQLException,IllegalArgumentException{
        if (authorTableName == null || authorIdColName == null || 
                authorId == null || authorTableName.isEmpty() ||
                authorIdColName.isEmpty() || authorId.isEmpty()) {
            throw new InvalidInputException();
        }
        return authorDao.retrieveAuthor(authorTableName, authorIdColName, authorId);
    }
    /**
     * 
     * @param authorTableName
     * @param authorIdColName
     * @param authorId
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public final int deleteAuthorById(String authorTableName, String authorIdColName,
            String authorId) throws ClassNotFoundException, SQLException,
            IllegalArgumentException{
        if (authorTableName == null || authorIdColName == null || 
                authorId == null || authorTableName.isEmpty() ||
                authorIdColName.isEmpty() || authorId.isEmpty()) {
            throw new InvalidInputException();
        }
        return authorDao.deleteAuthorById(authorTableName, authorIdColName, authorId);
    }
    /**
     * 
     * @param authorTableName
     * @param colNames
     * @param colValues
     * @param authorIdColName
     * @param authorId
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public final int updateAuthorById(String authorTableName, List<String> colNames, 
            List<Object> colValues, String authorIdColName, Object authorId) 
            throws ClassNotFoundException, SQLException, 
            IllegalArgumentException{
        if (authorTableName == null || authorIdColName == null || 
                colNames == null || colValues == null ||
                authorId == null || authorTableName.isEmpty() ||
                authorIdColName.isEmpty() || colNames.isEmpty() || 
                colValues.isEmpty()) {
            throw new InvalidInputException();
        }
        return authorDao.updateAuthorById(authorTableName, colNames, colValues, 
                authorIdColName, authorId);
    }
    /**
     * 
     * @param authorTableName
     * @param authorTableColNames
     * @param authorTableColValues
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public final int addNewAuthor(String authorTableName, List<String> authorTableColNames,
            List<Object> authorTableColValues) throws ClassNotFoundException, 
            SQLException, IllegalArgumentException {
        if (authorTableName == null || authorTableColNames == null || 
                authorTableColValues == null || authorTableName.isEmpty() ||
                authorTableColNames.isEmpty() || authorTableColValues.isEmpty()) {
            throw new InvalidInputException();
        }
        return authorDao.addNewAuthor(authorTableName, authorTableColNames, 
                authorTableColValues);
    }
    /**
     * 
     * @return 
     */
    public final IAuthorDao getAuthorDao() {
        return authorDao;
    }
    /**
     * 
     * @param authorDao 
     */
    public final void setAuthorDao(IAuthorDao authorDao) throws 
            IllegalArgumentException{
       if(authorDao == null){
           throw new InvalidInputException();
       }
        this.authorDao = authorDao;
    }

    @Override
    public final int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.authorDao);
        return hash;
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AuthorService other = (AuthorService) obj;
        if (!Objects.equals(this.authorDao, other.authorDao)) {
            return false;
        }
        return true;
    }

    @Override
    public final String toString() {
        return "AuthorService";
    }
    
}
