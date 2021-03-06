/*
 * Copyright (C) 2014 Frank Steiler <frank.steiler@steilerdev.de>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package activeRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class represents a row of the FfollowsP table providing functions to find, insert, update and delete these rows.
 * @author Frank Steiler <frank.steiler@steilerdev.de>
 */
public class FfollowsPActiveRecord extends DatabaseUtility {
    
    /**
     * This String contains the SQL command to insert data into the database.
     */
    private final String INSERT_INTO =
            "Insert into FfollowsP(FollowingFanpage, FollowedPost, PostRead)" +
            " Values (?, ?, ?)";
    
    /**
     * This String contains the part of the SQL command deleting the row.
     */
    private final String DELETE =
            " Delete" +
            " from FfollowsP";
    
    /**
     * Reduces the selection to the pageID and postID.
     */
    private final String BY_POSTID_PAGEID =
            " Where FollowedPost = ?" +
            " And FollowingFanpage = ?";
    
    private int followingFanpage;
    private int followedPost;
    private boolean postRead;
        
    /**
     * This function inserts the object into the database.
     * @return True if insert was successfull, false otherwise.
     */
    public boolean insert()
    {
        boolean success;
        try
        {
            PreparedStatement stmt = getDatabaseConnection().prepareStatement(INSERT_INTO);
            stmt.setInt(1, followingFanpage);
            stmt.setInt(2, followedPost);
            stmt.setBoolean(3, postRead);
             
            success = executeUpdate(stmt);
        }
        catch (Exception e)
        {
            success = false;
        }
        return success;
    }

    /**
     * Removes the row, the object is representing, from the database.
     * @return True if remove was successful, false otherwise.
     */
    public boolean remove()
    {
        boolean success;
        try
        {
            PreparedStatement stmt = getDatabaseConnection().prepareStatement(DELETE + BY_POSTID_PAGEID);
            stmt.setInt(1, followedPost);
            stmt.setInt(2, followingFanpage);

            success = executeUpdate(stmt);
        }
        catch (Exception e)
        {
            success = false;
        }
        return success;
    } 
    
    /**
     * This function executes the query for a given prepared statement.
     * @param stmt The prepared statement which needs to be executed.
     * @return True if successful, false otherwise.
     */
    private boolean executeUpdate(PreparedStatement stmt)
    {
        boolean success = false;
        try
        {
            Connection con = stmt.getConnection();
            try
            {               
                if(stmt.executeUpdate()>0)
                {
                    success = true;
                }
                
                stmt.close();
            }
            catch (SQLException sqle)
            {
                success = false;
            }
            finally
            {
                closeDatabaseConnection(con);
            }
        }
        catch (Exception e)
        {
            success = false;
        }
        return success;
    }

    /**
     * @return The followingFanpage.
     */
    public int getFollowingFanpage() {
        return followingFanpage;
    }

    /**
     * @param followingFanpage The followingFanpage to set.
     */
    public void setFollowingFanpage(int followingFanpage) {
        this.followingFanpage = followingFanpage;
    }

    /**
     * @return The followedPost.
     */
    public int getFollowedPost() {
        return followedPost;
    }

    /**
     * @param followedPost The followedPost to set.
     */
    public void setFollowedPost(int followedPost) {
        this.followedPost = followedPost;
    }

    /**
     * @return The postRead flag.
     */
    public boolean isPostRead() {
        return postRead;
    }

    /**
     * @param postRead The postRead to set.
     */
    public void setPostRead(boolean postRead) {
        this.postRead = postRead;
    }
}
