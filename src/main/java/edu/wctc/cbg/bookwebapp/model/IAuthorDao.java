package edu.wctc.cbg.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author cgonz
 */
public interface IAuthorDao {

    @Override
    public abstract boolean equals(Object obj);

    public abstract List<Author> getAuthorList(String tableName, int maxRecords) throws ClassNotFoundException, SQLException;

    public abstract DbAccessor getDb();

    public abstract String getDriverClass();

    public abstract String getPassword();

    public abstract String getUrl();

    public abstract String getUserName();

    @Override
    public abstract int hashCode();

    public abstract void setDb(DbAccessor db);

    public abstract void setDriverClass(String driverClass);

    public abstract void setPassword(String password);

    public abstract void setUrl(String url);

    public abstract void setUserName(String userName);
    
}
